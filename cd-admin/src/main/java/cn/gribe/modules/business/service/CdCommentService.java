package cn.gribe.modules.business.service;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.entity.CommentEntity;
import com.baomidou.mybatisplus.service.IService;
import cn.gribe.entity.CommentEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-24 15:34:15
 */
public interface CdCommentService extends IService<CommentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

