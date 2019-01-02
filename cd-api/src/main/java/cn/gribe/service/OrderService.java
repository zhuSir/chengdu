package cn.gribe.service;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.entity.OrderEntity;
import cn.gribe.entity.ProductEntity;
import cn.gribe.entity.UserEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-06 16:49:57
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    String saveAndPay(OrderEntity order, ProductEntity productEntity,UserEntity user);

    OrderEntity queryByCode(String orderCode);

    int selectSales(Integer productId);
}

