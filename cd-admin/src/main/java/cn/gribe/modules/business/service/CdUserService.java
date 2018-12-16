package cn.gribe.modules.business.service;

import cn.gribe.common.utils.PageUtils;
import com.baomidou.mybatisplus.service.IService;
import cn.gribe.modules.business.entity.CdUserEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-24 15:34:14
 */
public interface CdUserService extends IService<CdUserEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

