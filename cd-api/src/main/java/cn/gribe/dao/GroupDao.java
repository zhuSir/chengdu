package cn.gribe.dao;

import cn.gribe.entity.GroupEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-15 17:53:36
 */
public interface GroupDao extends BaseMapper<GroupEntity> {

    List<GroupEntity> selectPageByUserId(RowBounds rowBounds, @Param("userId") Integer userId);
}
