package cn.gribe.modules.business.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;
import cn.gribe.modules.business.dao.CdGroupDao;
import cn.gribe.entity.GroupEntity;
import cn.gribe.modules.business.service.CdGroupService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("cdGroupService")
public class CdGroupServiceImpl extends ServiceImpl<CdGroupDao, GroupEntity> implements CdGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<GroupEntity> page = this.selectPage(
                new Query<GroupEntity>(params).getPage(),
                new EntityWrapper<GroupEntity>()
        );

        return new PageUtils(page);
    }

}
