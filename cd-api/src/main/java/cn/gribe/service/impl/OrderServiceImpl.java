package cn.gribe.service.impl;

import cn.gribe.common.utils.DateUtils;
import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.wxpay.WxpayUtils;
import cn.gribe.common.validator.Assert;
import cn.gribe.dao.OrderDao;
import cn.gribe.entity.OrderEntity;
import cn.gribe.entity.ProductEntity;
import cn.gribe.entity.UserEntity;
import cn.gribe.service.OrderService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import cn.gribe.common.utils.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;


@Service("cdOrderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private WxpayUtils wxpayUtils;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<OrderEntity> page = new Query<OrderEntity>(params).getPage();// 当前页，总条数 构造 page 对象
        String state = params.get("state") == null ? null : (String) params.get("state");
        state = "0".equals(state) ? null : state;
        Integer userId = (Integer) params.get("userId");
        page.setRecords(this.baseMapper.selectPageByState(page,state,userId));
        return new PageUtils(page);
    }

    @Override
    public synchronized Object saveAndPay(OrderEntity order, ProductEntity product,UserEntity user) {
        //验证是否有没有支付的订单
//        Assert.state(isExistAwaitPay(user.getId()),"您当前还有未支付订单，请先支付后再下单");
        Assert.state(order.getSum() < 0,"金额错误，必须大于零元");
        double sumPrice = product.getPrice()*order.getCount();
        Assert.state(sumPrice != order.getSum(),"金额错误，请重新下单");
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.like("create_time",DateUtils.format(new Date()));
        int count = this.baseMapper.selectCount(wrapper);
        String orderCode = DateUtils.format(new Date(),"yyyyMMddHHmmssSS")+count;
        order.setCode(orderCode);//设置订单号
        if(OrderEntity.PAY_TYPE_ALIPAY.equals(order.getPayType())){
            //支付宝支付
//            String remark = product.getName();//TODO 判断字数是否超出
//            String subject = product.getName();//TODO 判断字数是否超出
//            AliPayOrder payOrder = new AliPayOrder(String.valueOf(order.getSum()),remark,subject,order.getCode());
//            String result = alipayUtils.getAliPayOrder(payOrder);
//            Assert.isNull(result,"支付下单错误,订单号为："+payOrder.toString());
            Assert.state(!this.insert(order),"保存订单错误，请联系管理员","==>:保存订单错误 orderInfo:"+order.toString());
            return "success";
        }else if(OrderEntity.PAY_TYPE_WECHATPAY.equals(order.getPayType())){
            Map signString = wxpayUtils.unifiedOrder(product.getName(),order.getCode(),String.valueOf((int)(order.getSum()*100)));
            Assert.isNull(signString,"下单错误，请联系管理员");
            Assert.state(!this.insert(order),"保存订单错误，请联系管理员","==>:保存订单错误 orderInfo:"+order.toString());
            return signString;
            //微信支付
        }
        Assert.isNull(null,"支付类型错误");
        return null;
    }

    @Override
    public OrderEntity queryByCode(String orderCode) {
        EntityWrapper wrapper = new EntityWrapper<>();
        wrapper.eq("code",orderCode);
        return this.selectOne(wrapper);
    }

    @Override
    public int selectSales(Integer productId) {
        EntityWrapper orderWrapper = new EntityWrapper();
        orderWrapper.eq("product_id",productId);
        int count = this.baseMapper.selectCount(orderWrapper);
        return count;
    }

    /**
     * 判断用户是否有待付订单
     * @param userId
     * @return
     */
    public boolean isExistAwaitPay(int userId){
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("state",OrderEntity.STATE_AWAIT_PAY);
        wrapper.eq("user_id",userId);
        int count = this.selectCount(wrapper);
        if(count > 0){
            return true;
        }
        return false;
    }

}
