package cn.gribe.modules.sys.service;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.modules.sys.entity.DcAgencyUserEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-07 17:38:54
 */
public interface DcAgencyUserService extends IService<DcAgencyUserEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

