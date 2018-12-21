package cn.gribe.modules.business.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.modules.business.dao.CdActivityDao;
import org.springframework.stereotype.Service;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.gribe.entity.ActivityEntity;
import cn.gribe.modules.business.service.CdActivityService;


@Service("cdActivityService")
public class CdActivityServiceImpl extends ServiceImpl<CdActivityDao, ActivityEntity> implements CdActivityService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
//        Page<ActivityEntity> page = this.selectPage(
//                new Query<ActivityEntity>(params).getPage(),
//                new EntityWrapper<ActivityEntity>()
//        );
//
//        return new PageUtils(page);
        Page<ActivityEntity> page = new Page<>();// 当前页，总条数 构造 page 对象
        page.setRecords(this.baseMapper.selectPage());
        return new PageUtils(page);
    }

}
