package cn.gribe.modules.business.service;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.entity.ProductEntity;
import cn.gribe.entity.ProductSpecialPrice;
import cn.gribe.entity.ProductTagEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 商品服务
 */
public interface CdProductService extends IService<ProductEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<ProductTagEntity> queryTags(Integer productId);

    void saveOrUpdateBySpecialPrice(List<ProductSpecialPrice> prices);
}

