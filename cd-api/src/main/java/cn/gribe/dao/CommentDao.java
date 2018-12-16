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

    List<CommentEntity> selectPage(@Param("postId") String postId , @Param("storeId")String storeId,@Param("name") String name,@Param("userId") String userId);

    List<CommentEntity> selectPageByUserId(@Param("userId") Integer userId);

}
