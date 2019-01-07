package cn.gribe.modules.business.dao;

import cn.gribe.entity.ActivityEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-24 15:34:23
 */
public interface CdActivityDao extends BaseMapper<ActivityEntity> {

    List<ActivityEntity> selectPage(RowBounds rowBounds);

}
