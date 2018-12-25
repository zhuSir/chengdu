package cn.gribe.modules.business.dao;

import cn.gribe.entity.CommentEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-24 15:34:15
 */
public interface CdCommentDao extends BaseMapper<CommentEntity> {

    List<CommentEntity> selectPage(@Param("content") String content,@Param("storeId") Integer storeId);

}
