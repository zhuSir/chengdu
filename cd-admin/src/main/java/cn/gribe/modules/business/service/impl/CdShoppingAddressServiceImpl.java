package cn.gribe.modules.business.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;
import cn.gribe.modules.business.dao.CdShoppingAddressDao;
import cn.gribe.modules.business.entity.CdShoppingAddressEntity;
import cn.gribe.modules.business.service.CdShoppingAddressService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("cdShoppingAddressService")
public class CdShoppingAddressServiceImpl extends ServiceImpl<CdShoppingAddressDao, CdShoppingAddressEntity> implements CdShoppingAddressService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CdShoppingAddressEntity> page = this.selectPage(
                new Query<CdShoppingAddressEntity>(params).getPage(),
                new EntityWrapper<CdShoppingAddressEntity>()
        );

        return new PageUtils(page);
    }

}
