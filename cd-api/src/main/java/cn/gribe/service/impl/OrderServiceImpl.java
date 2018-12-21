package cn.gribe.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;
import cn.gribe.common.utils.alipay.AlipayUtils;
import cn.gribe.common.validator.Assert;
import cn.gribe.dao.OrderDao;
import cn.gribe.entity.AliPayOrder;
import cn.gribe.entity.OrderEntity;
import cn.gribe.entity.ProductEntity;
import cn.gribe.entity.UserEntity;
import cn.gribe.service.OrderService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import cn.gribe.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cdOrderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<OrderEntity> page = new Query<OrderEntity>(params).getPage();// 当前页，总条数 构造 page 对象
        String state = params.get("state") == null ? null : (String) params.get("state");
        page.setRecords(this.baseMapper.selectPageByState(state));
        return new PageUtils(page);
    }

    @Override
    public String saveAndPay(OrderEntity order, ProductEntity product,UserEntity user) {
        //验证是否有没有支付的订单
        Assert.state(isExistAwaitPay(user.getId()),"您当前还有未支付订单，请先支付后再下单");
        if(OrderEntity.PAY_TYPE_ALIPAY.equals(order.getPayType())){
            //支付宝支付
            Assert.state(order.getSum() > 0,"金额错误，必须大于零元");
            String remark = product.getName();//TODO 判断字数是否超出
            String subject = product.getName();//TODO 判断字数是否超出
            AliPayOrder payOrder = new AliPayOrder(String.valueOf(order.getSum()),remark,subject,order.getCode());
            String result = AlipayUtils.getAliPayOrder(payOrder);
            Assert.isNull(result,"支付下单错误,订单号为："+payOrder.toString());
            Assert.state(!this.insert(order),"保存订单错误，请联系管理员","==>:保存订单错误 orderInfo:"+order.toString());
            return result;
        }else if(OrderEntity.PAY_TYPE_WECHATPAY.equals(order.getPayType())){
            return null;
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
