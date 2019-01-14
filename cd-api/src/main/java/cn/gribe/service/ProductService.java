package cn.gribe.service;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.entity.ProductEntity;
import cn.gribe.entity.ProductSpecialPrice;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 */
public interface ProductService extends IService<ProductEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<ProductSpecialPrice> selectSpecialPriceList(Date startTime,Integer productId);
}

