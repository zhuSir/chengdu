package cn.gribe.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.dao.ProductDao;
import cn.gribe.dao.ProductTagDao;
import cn.gribe.entity.OrderEntity;
import cn.gribe.entity.ProductEntity;
import cn.gribe.service.OrderService;
import cn.gribe.service.ProductService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import cn.gribe.common.utils.Query;
import cn.gribe.entity.ProductTagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("cdProductService")
public class ProductServiceImpl extends ServiceImpl<ProductDao, ProductEntity> implements ProductService {

    @Autowired
    private ProductTagDao productTagDao;

    @Autowired
    private OrderService orderService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper wrapper = new EntityWrapper<ProductEntity>();
        Object storeId = params.get("storeId");
        if(storeId != null){
            wrapper.eq("store_id",storeId);
        }
        Page<ProductEntity> page = this.selectPage(
                new Query<ProductEntity>(params).getPage(),
                wrapper
        );
        List<ProductEntity> products = page.getRecords();
        for(ProductEntity entity : products){
            List<ProductTagEntity> tags = productTagDao.selectList(new EntityWrapper().eq("product_id",entity.getId()));
            entity.setTags(tags);
            //entity 销量统计
            int count = orderService.selectSales(entity.getId());
            entity.setSales(count);
        }
        return new PageUtils(page);
    }

}
