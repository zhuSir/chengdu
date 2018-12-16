package cn.gribe.service.impl;

import cn.gribe.common.utils.PageUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import cn.gribe.common.utils.Query;
import cn.gribe.dao.ActivityDao;
import cn.gribe.entity.ActivityEntity;
import cn.gribe.service.ActivityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("cdActivityService")
public class ActivityServiceImpl extends ServiceImpl<ActivityDao, ActivityEntity> implements ActivityService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ActivityEntity> page = this.selectPage(
                new Query<ActivityEntity>(params).getPage(),
                new EntityWrapper<ActivityEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public List<ActivityEntity> queryAllActivity(){
        List<ActivityEntity> res = this.selectList(new EntityWrapper<>());
        return res;
    }

}
