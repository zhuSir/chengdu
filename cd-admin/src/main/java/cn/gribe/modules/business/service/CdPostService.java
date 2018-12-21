package cn.gribe.modules.business.service;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.entity.PostEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-24 15:34:15
 */
public interface CdPostService extends IService<PostEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

