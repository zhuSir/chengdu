package cn.gribe.modules.business.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.modules.business.dao.CdActivityDao;
import org.springframework.stereotype.Service;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.gribe.modules.business.entity.CdActivityEntity;
import cn.gribe.modules.business.service.CdActivityService;


@Service("cdActivityService")
public class CdActivityServiceImpl extends ServiceImpl<CdActivityDao, CdActivityEntity> implements CdActivityService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
//        Page<CdActivityEntity> page = this.selectPage(
//                new Query<CdActivityEntity>(params).getPage(),
//                new EntityWrapper<CdActivityEntity>()
//        );
//
//        return new PageUtils(page);
        Page<CdActivityEntity> page = new Page<>();// 当前页，总条数 构造 page 对象
        page.setRecords(this.baseMapper.selectPage());
        return new PageUtils(page);
    }

}
