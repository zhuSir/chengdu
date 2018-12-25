package cn.gribe.modules.business.service;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.entity.OrderEntity;
import com.baomidou.mybatisplus.service.IService;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-24 15:34:15
 */
public interface CdOrderService extends IService<OrderEntity> {

    PageUtils queryPage(Integer page,Integer limit,String phone, String storeName,Integer storeId, String startTime, String endTime);
}

