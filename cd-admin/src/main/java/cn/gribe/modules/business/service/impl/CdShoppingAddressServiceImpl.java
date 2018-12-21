package cn.gribe.modules.business.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;
import cn.gribe.modules.business.dao.CdShoppingAddressDao;
import cn.gribe.entity.ShoppingAddressEntity;
import cn.gribe.modules.business.service.CdShoppingAddressService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("cdShoppingAddressService")
public class CdShoppingAddressServiceImpl extends ServiceImpl<CdShoppingAddressDao, ShoppingAddressEntity> implements CdShoppingAddressService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ShoppingAddressEntity> page = this.selectPage(
                new Query<ShoppingAddressEntity>(params).getPage(),
                new EntityWrapper<ShoppingAddressEntity>()
        );

        return new PageUtils(page);
    }

}
