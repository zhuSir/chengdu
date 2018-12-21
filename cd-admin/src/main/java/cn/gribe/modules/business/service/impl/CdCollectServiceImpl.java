package cn.gribe.modules.business.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;
import cn.gribe.modules.business.dao.CdCollectDao;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.gribe.entity.CollectEntity;
import cn.gribe.modules.business.service.CdCollectService;


@Service("cdCollectService")
public class CdCollectServiceImpl extends ServiceImpl<CdCollectDao, CollectEntity> implements CdCollectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CollectEntity> page = this.selectPage(
                new Query<CollectEntity>(params).getPage(),
                new EntityWrapper<CollectEntity>()
        );

        return new PageUtils(page);
    }

}
