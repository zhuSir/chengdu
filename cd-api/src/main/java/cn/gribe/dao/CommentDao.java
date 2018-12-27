package cn.gribe.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import cn.gribe.entity.CommentEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public interface CommentDao extends BaseMapper<CommentEntity> {

    List<CommentEntity> selectPage(@Param("postId") Object postId , @Param("storeId")Object storeId,@Param("name") Object name,@Param("userId") Object userId);

    List<CommentEntity> selectPageByCommentId(@Param("commentId") Object commentId );

    List<CommentEntity> selectPageByUserId(@Param("userId") Integer userId);

}
