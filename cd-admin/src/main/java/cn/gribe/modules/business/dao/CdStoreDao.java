package cn.gribe.modules.business.dao;

import cn.gribe.entity.StoreEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 *
 */
public interface CdStoreDao extends BaseMapper<StoreEntity> {

    List<StoreEntity> queryList(RowBounds rowBounds, @Param("storeId") Integer storeId,@Param("name")Object name);
	
}
