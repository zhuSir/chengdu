package cn.gribe.modules.business.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;
import cn.gribe.entity.PostEntity;
import cn.gribe.modules.business.dao.CdActivityDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.gribe.entity.ActivityEntity;
import cn.gribe.modules.business.service.CdActivityService;


@Service("cdActivityService")
public class CdActivityServiceImpl extends ServiceImpl<CdActivityDao, ActivityEntity> implements CdActivityService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ActivityEntity> page = new Query<ActivityEntity>(params).getPage();// 当前页，总条数 构造 page 对象
        List<ActivityEntity> res = this.baseMapper.selectPage(page);
        page.setRecords(res);
        return new PageUtils(page);
    }

}

