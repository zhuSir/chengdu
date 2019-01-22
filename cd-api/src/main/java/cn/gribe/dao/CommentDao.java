package cn.gribe.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import cn.gribe.entity.CommentEntity;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public interface CommentDao extends BaseMapper<CommentEntity> {

    List<CommentEntity> selectPage(RowBounds rowBounds, @Param("postId") Object postId , @Param("storeId")Object storeId, @Param("name") Object name, @Param("userId") Object userId);

    List<CommentEntity> selectPageByCommentId(@Param("commentId") Object commentId );

    List<CommentEntity> selectPageByUserId(RowBounds rowBounds, @Param("userId") Integer userId);

    List<CommentEntity> selectPageByPostComment(Page<CommentEntity> page,@Param("userId")  Object userId);

    CommentEntity selectByStoreId(@Param("storeId") Integer storeId);
}
