package cn.gribe.service;

import cn.gribe.common.utils.PageUtils;
import com.baomidou.mybatisplus.service.IService;
import cn.gribe.entity.CommentEntity;

import java.util.Map;

/**
 * 评论
 */
public interface CommentService extends IService<CommentEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByUserId(Integer userId);
}

