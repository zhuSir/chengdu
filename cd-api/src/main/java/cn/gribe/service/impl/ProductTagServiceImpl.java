package cn.gribe.service.impl;

import cn.gribe.dao.ProductTagDao;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import cn.gribe.entity.ProductTagEntity;
import cn.gribe.service.ProductTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Zhugw on 2018/12/12 0012.
 */
@Service
public class ProductTagServiceImpl implements ProductTagService {

    @Autowired
    private ProductTagDao dao;

    @Override
    public List<ProductTagEntity> selectList(Integer productId) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("product_id",productId);
        return dao.selectList(wrapper);
    }
}
