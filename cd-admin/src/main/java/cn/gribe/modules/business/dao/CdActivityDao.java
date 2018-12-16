package cn.gribe.modules.business.dao;

import cn.gribe.modules.business.entity.CdActivityEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-24 15:34:23
 */
public interface CdActivityDao extends BaseMapper<CdActivityEntity> {

    List<CdActivityEntity> selectPage();

}
