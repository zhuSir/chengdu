package cn.gribe.service;

import cn.gribe.common.utils.PageUtils;
import com.baomidou.mybatisplus.service.IService;
import cn.gribe.entity.ActivityEntity;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface ActivityService extends IService<ActivityEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<ActivityEntity> queryAllActivity();
}

