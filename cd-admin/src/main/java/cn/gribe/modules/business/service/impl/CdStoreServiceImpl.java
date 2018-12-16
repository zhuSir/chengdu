package cn.gribe.modules.business.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;
import cn.gribe.modules.business.service.CdStoreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.gribe.modules.business.dao.CdStoreDao;
import cn.gribe.modules.business.entity.CdStoreEntity;


@Service("cdStoreService")
public class CdStoreServiceImpl extends ServiceImpl<CdStoreDao, CdStoreEntity> implements CdStoreService {

    @Override
    public List<CdStoreEntity> queryAllStore() {
        return this.selectList(new EntityWrapper<CdStoreEntity>());
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CdStoreEntity> page = this.selectPage(
                new Query<CdStoreEntity>(params).getPage(),
                new EntityWrapper<CdStoreEntity>()
        );

        return new PageUtils(page);
    }

}
