package cn.gribe.modules.business.dao;

import cn.gribe.entity.PostEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-24 15:34:15
 */
public interface CdPostDao extends BaseMapper<PostEntity> {

    List<PostEntity> selectPage(RowBounds rowBounds,@Param("name") Object name,@Param("groupId") Object groupId);

}
