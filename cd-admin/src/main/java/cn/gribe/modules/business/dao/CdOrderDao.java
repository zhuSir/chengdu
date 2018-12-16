package cn.gribe.modules.business.dao;

import cn.gribe.modules.business.entity.CdOrderEntity;
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
public interface CdOrderDao extends BaseMapper<CdOrderEntity> {

    List<CdOrderEntity> selectPage(@Param("phone") String phone, @Param("storeName")String storeName, @Param("startTime") String startTime, @Param("endTime") String endTime);

}
