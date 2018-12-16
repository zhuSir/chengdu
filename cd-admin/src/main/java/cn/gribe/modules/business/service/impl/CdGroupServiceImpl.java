package cn.gribe.modules.business.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;
import cn.gribe.modules.business.dao.CdGroupDao;
import cn.gribe.modules.business.entity.CdGroupEntity;
import cn.gribe.modules.business.service.CdGroupService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("cdGroupService")
public class CdGroupServiceImpl extends ServiceImpl<CdGroupDao, CdGroupEntity> implements CdGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CdGroupEntity> page = this.selectPage(
                new Query<CdGroupEntity>(params).getPage(),
                new EntityWrapper<CdGroupEntity>()
        );

        return new PageUtils(page);
    }

}
