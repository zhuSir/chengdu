package cn.gribe.modules.business.dao;

import cn.gribe.entity.StoreEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 */
public interface CdStoreDao extends BaseMapper<StoreEntity> {

    List<StoreEntity> queryList(@Param("storeId") Integer storeId);
	
}
