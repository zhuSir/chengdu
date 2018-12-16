package cn.gribe.service;

import cn.gribe.entity.ProductTagEntity;

import java.util.List;

/**
 * Created by Zhugw on 2018/12/12 0012.
 */
public interface ProductTagService {

    List<ProductTagEntity> selectList(Integer productId);

}
