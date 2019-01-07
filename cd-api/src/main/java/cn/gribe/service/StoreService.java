package cn.gribe.service;

import cn.gribe.common.utils.PageUtils;
import com.baomidou.mybatisplus.service.IService;
import cn.gribe.entity.StoreEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-06 16:49:57
 */
public interface StoreService extends IService<StoreEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<StoreEntity> queryByLocation(String lat,String lon,Integer page,Integer limit);

    PageUtils queryPageByUserId(Map<String, Object> params, Integer userId);
}

