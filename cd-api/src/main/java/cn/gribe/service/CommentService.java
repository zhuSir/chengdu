package cn.gribe.service;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.entity.UserEntity;
import com.baomidou.mybatisplus.service.IService;
import cn.gribe.entity.CommentEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * 评论
 */
public interface CommentService extends IService<CommentEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPostCommentPage(Map<String, Object> params);

    PageUtils queryPageByUserId(Map<String, Object> params,Integer userId);

    void save(MultipartFile[] files, CommentEntity comment, UserEntity user) throws IOException;

    CommentEntity selectByStoreId(Integer storeId);
}

