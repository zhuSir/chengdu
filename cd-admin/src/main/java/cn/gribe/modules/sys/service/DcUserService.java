package cn.gribe.modules.sys.service;

import cn.gribe.common.utils.PageUtils;
import com.baomidou.mybatisplus.service.IService;
import cn.gribe.modules.sys.entity.DcUserEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-07 17:38:54
 */
public interface DcUserService extends IService<DcUserEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

