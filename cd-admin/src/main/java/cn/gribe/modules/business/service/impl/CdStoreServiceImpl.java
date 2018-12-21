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
import cn.gribe.entity.StoreEntity;


@Service("cdStoreService")
public class CdStoreServiceImpl extends ServiceImpl<CdStoreDao, StoreEntity> implements CdStoreService {

    @Override
    public List<StoreEntity> queryAllStore() {
        return this.selectList(new EntityWrapper<StoreEntity>());
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<StoreEntity> page = this.selectPage(
                new Query<StoreEntity>(params).getPage(),
                new EntityWrapper<StoreEntity>()
        );

        return new PageUtils(page);
    }

}
