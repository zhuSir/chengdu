package cn.gribe.modules.business.dao;

import cn.gribe.entity.OrderEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-24 15:34:15
 */
@Repository
public interface CdOrderDao extends BaseMapper<OrderEntity> {

    List<OrderEntity> selectPage(@Param("phone") Object phone, @Param("storeName")Object storeName,@Param("storeId")Object storeId, @Param("status") Object status, @Param("startTime") String startTime, @Param("endTime") String endTime);

}