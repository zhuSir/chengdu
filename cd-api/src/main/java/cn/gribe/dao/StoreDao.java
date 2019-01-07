package cn.gribe.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import cn.gribe.entity.StoreEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-06 16:49:57
 */
public interface StoreDao extends BaseMapper<StoreEntity> {

    List<StoreEntity> selectPageByUserId(RowBounds rowBounds, @Param("userId") Integer userId);
}
