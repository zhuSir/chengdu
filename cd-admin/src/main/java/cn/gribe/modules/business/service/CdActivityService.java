package cn.gribe.modules.business.service;

import cn.gribe.common.utils.PageUtils;
import com.baomidou.mybatisplus.service.IService;
import cn.gribe.entity.ActivityEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-24 15:34:23
 */
public interface CdActivityService extends IService<ActivityEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

