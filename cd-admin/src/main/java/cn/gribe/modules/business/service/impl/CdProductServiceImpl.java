package cn.gribe.modules.business.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;
import cn.gribe.entity.ProductTagEntity;
import cn.gribe.modules.business.dao.CdProductDao;
import cn.gribe.entity.ProductEntity;
import cn.gribe.modules.business.dao.ProductTagDao;
import cn.gribe.modules.business.service.CdProductService;
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

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ProductEntity> page = this.selectPage(
                new Query<ProductEntity>(params).getPage(),
                new EntityWrapper<ProductEntity>()
        );
        List<ProductEntity> res = page.getRecords();
        List<ProductEntity> result = new ArrayList<>();
        for(ProductEntity entity : res){
            List<ProductTagEntity> tags = queryTags(entity.getId());
            if(tags != null && tags.size() > 0){
                entity.setTag(tags.get(0).getTag());
            }
            result.add(entity);
        }
        page.setRecords(result);
        return new PageUtils(page);
    }

    @Override
    public List<ProductTagEntity> queryTags(Integer productId) {
        EntityWrapper<ProductTagEntity> wrapper = new EntityWrapper<ProductTagEntity>();
        wrapper.eq("product_id",productId);
        List<ProductTagEntity> tags = tagDao.selectList(wrapper);
        return tags;
    }

}
