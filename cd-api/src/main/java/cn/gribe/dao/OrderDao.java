package cn.gribe.dao;

import cn.gribe.entity.OrderEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
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
public interface OrderDao extends BaseMapper<OrderEntity> {

    List<OrderEntity> selectPageByState(RowBounds rowBounds, @Param("state")String state, @Param("userId")Integer userId);

}
