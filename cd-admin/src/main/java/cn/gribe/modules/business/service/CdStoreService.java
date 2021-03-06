package cn.gribe.modules.business.service;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.entity.StoreEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-24 15:34:15
 */
public interface CdStoreService extends IService<StoreEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<StoreEntity> queryAllStore(Integer storeId);

    StoreEntity queryByUserId(Long userId);
}

