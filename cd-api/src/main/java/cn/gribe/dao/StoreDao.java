package cn.gribe.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import cn.gribe.entity.StoreEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 店铺
 */
public interface StoreDao extends BaseMapper<StoreEntity> {

    List<StoreEntity> selectPageByUserId(RowBounds rowBounds, @Param("userId") Integer userId);

    List<StoreEntity> selectPageOrderByDistance(RowBounds rowBounds, @Param("lat") String lat, @Param("lng") String lng,@Param("storeType") Object storeType,@Param("name") Object name);
}
