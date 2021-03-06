package cn.gribe.modules.business.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;
import cn.gribe.entity.ProductSpecialPrice;
import cn.gribe.entity.ProductTagEntity;
import cn.gribe.entity.StoreEntity;
import cn.gribe.modules.business.dao.CdProductDao;
import cn.gribe.entity.ProductEntity;
import cn.gribe.modules.business.dao.CdStoreDao;
import cn.gribe.modules.business.dao.ProductSpecialPriceDao;
import cn.gribe.modules.business.dao.ProductTagDao;
import cn.gribe.modules.business.service.CdProductService;
import cn.gribe.modules.business.service.CdStoreService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("cdProductService")
public class CdProductServiceImpl extends ServiceImpl<CdProductDao, ProductEntity> implements CdProductService {

    @Autowired
    private ProductTagDao tagDao;

    @Autowired
    private CdStoreDao storeDao;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper wrapper = new EntityWrapper<ProductEntity>();
        Object storeId = params.get("storeId") != null ? params.get("storeId") : null;
        if(storeId != null && StringUtils.isNotEmpty(String.valueOf(storeId)) && !"0".equals(String.valueOf(storeId))){
            wrapper.eq("store_id",storeId);
        }
        String name = params.get("name") != null ? (String) params.get("name") : null;
        if(name != null){
            wrapper.like("name",name);
        }
        Page<ProductEntity> page = this.selectPage(
                new Query<ProductEntity>(params).getPage(),
                wrapper
        );
        List<ProductEntity> res = page.getRecords();
        List<ProductEntity> result = new ArrayList<>();
        for(ProductEntity entity : res){
            List<ProductTagEntity> tags = queryTags(entity.getId());
            if(tags != null && tags.size() > 0){
                entity.setTag(tags.get(0).getTag());
            }
            StoreEntity storeEntity = storeDao.selectById(entity.getStoreId());
            if(storeEntity != null){
                entity.setStoreName(storeEntity.getName());
            }
            result.add(entity);
        }
        page.setRecords(result);
        logger.info("==>: page: "+JSONObject.toJSONString(page));
        return new PageUtils(page);
    }

    @Override
    public List<ProductTagEntity> queryTags(Integer productId) {
        EntityWrapper<ProductTagEntity> wrapper = new EntityWrapper<ProductTagEntity>();
        wrapper.eq("product_id",productId);
        List<ProductTagEntity> tags = tagDao.selectList(wrapper);
        return tags;
    }

    @Override
    public void saveOrUpdateBySpecialPrice(List<ProductSpecialPrice> prices) {
//        for(ProductSpecialPrice price : prices){
//            productSpecialPriceDao
//        }
    }

}
