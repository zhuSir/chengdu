package cn.gribe.modules.business.service.impl;

import cn.gribe.entity.ProductSpecialPrice;
import cn.gribe.modules.business.dao.ProductSpecialPriceDao;
import cn.gribe.modules.business.service.ProductSpecialPriceService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ProductSpecialPriceServiceImpl
 * @Description special price service
 * @Author Zhugw
 * @Date 2019/1/11 14:51
 * @Version 1.0
 **/
@Service
public class ProductSpecialPriceServiceImpl  extends ServiceImpl<ProductSpecialPriceDao, ProductSpecialPrice> implements ProductSpecialPriceService{

    @Override
    public List<ProductSpecialPrice> selectListByProductId(Integer id) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("product_id",id);
        List<ProductSpecialPrice> prices = this.baseMapper.selectList(wrapper);
        Map res = new HashMap();
        for(ProductSpecialPrice price : prices){
            res.putAll(price.getMap());
        }
        return prices;
    }
}
