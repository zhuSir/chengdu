package cn.gribe.modules.business.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;
import cn.gribe.entity.OrderEntity;
import cn.gribe.entity.PostEntity;
import cn.gribe.modules.business.service.CdStoreService;
import org.apache.commons.lang.StringUtils;
import org.reflections.Store;
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
    public StoreEntity queryByUserId(Long userId) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("user_id",userId);
        List<StoreEntity> stores = this.baseMapper.selectList(wrapper);
        if(stores != null && stores.size() >= 1){
            return stores.get(0);
        }
        return null;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<StoreEntity> page = new Query<StoreEntity>(params).getPage();// 当前页，总条数 构造 page 对象
        Integer storeId = params.get("storeId") == null ? null : (Integer) params.get("storeId");
        page.setRecords(this.baseMapper.queryList(storeId));
        return new PageUtils(page);
    }

}
