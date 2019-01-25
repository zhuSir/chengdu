package cn.gribe.modules.business.service;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.entity.OrderEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 订单
 */
public interface CdOrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void refundOrder(Integer orderId);

    List<OrderEntity> selectByParams(Map<String, Object> params);
}

