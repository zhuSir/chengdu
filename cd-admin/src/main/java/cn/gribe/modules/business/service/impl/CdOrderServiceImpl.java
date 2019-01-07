package cn.gribe.modules.business.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;
import cn.gribe.common.utils.alipay.AlipayUtils;
import cn.gribe.common.validator.Assert;
import cn.gribe.entity.OrderEntity;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.gribe.modules.business.dao.CdOrderDao;
import cn.gribe.modules.business.service.CdOrderService;

import java.util.List;
import java.util.Map;


@Service("cdOrderService")
public class CdOrderServiceImpl extends ServiceImpl<CdOrderDao, OrderEntity> implements CdOrderService {

    @Autowired
    private AlipayUtils alipayUtils;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<OrderEntity> resPage = new Query<OrderEntity>(params).getPage();// 当前页，总条数 构造 page 对象
        //String phone, String storeName,Integer storeId,Integer status, String startTime, String endTime
        Object phone = params.get("phone");
        String startTime = params.get("startTime") != null && StringUtils.isNotEmpty((String) params.get("startTime"))? params.get("startTime")+" 00:00:00" : null;
        String endTime = params.get("endTime") != null && StringUtils.isNotEmpty((String) params.get("endTime")) ? params.get("endTime")+" 23:59:59" : null;
        Object storeName = params.get("storeName");
        Object storeId = params.get("storeId");
        Object status = params.get("status");
        Object payResults = params.get("payResults");
        List<OrderEntity> records = this.baseMapper.selectPage(resPage,phone,storeName,storeId,status,startTime,endTime,payResults);
        resPage.setRecords(records);
        System.out.println("==>:json : "+ JSONObject.toJSONString(resPage));
        return new PageUtils(resPage);
    }

    @Override
    public void refundOrder(Integer orderId) {
        Assert.isNull(orderId,"参数错误，请刷新重试");
        OrderEntity orderEntity = this.baseMapper.selectById(orderId);
        Assert.isNull(orderEntity,"订单错误，请刷新重试");
        Assert.state(orderEntity.getState().intValue() != OrderEntity.STATE_AWAIT_USE.intValue(),
                "该订单不是待使用状态，不能进行退单操作");
        //退款
        alipayUtils.orderRefund(orderEntity.getCode(),orderEntity.getTradeNo(),orderEntity.getSum());
        orderEntity.setState(OrderEntity.STATE_CHARGE_BACK);
        this.baseMapper.updateById(orderEntity);
    }

    public List<OrderEntity> selectByParams(Integer storeId){
        return this.baseMapper.selectByParams(storeId);
    }

}
