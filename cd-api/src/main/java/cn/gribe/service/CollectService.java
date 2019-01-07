package cn.gribe.service;

import cn.gribe.common.utils.PageUtils;
import com.baomidou.mybatisplus.service.IService;
import cn.gribe.entity.CollectEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-15 17:53:36
 */
public interface CollectService extends IService<CollectEntity> {

    PageUtils queryPage(Integer userId, Map<String, Object> type);

    CollectEntity selectByParams(CollectEntity collect);
}

