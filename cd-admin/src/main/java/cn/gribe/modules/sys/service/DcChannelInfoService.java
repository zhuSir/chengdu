package cn.gribe.modules.sys.service;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.modules.sys.entity.DcChannelInfoEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-07 17:38:54
 */
public interface DcChannelInfoService extends IService<DcChannelInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

