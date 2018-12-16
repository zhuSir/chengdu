package cn.gribe.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.dao.ShoppingAddressDao;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import cn.gribe.common.utils.Query;

import cn.gribe.entity.ShoppingAddressEntity;
import cn.gribe.service.ShoppingAddressService;


@Service("cdShoppingAddressService")
public class ShoppingAddressServiceImpl extends ServiceImpl<ShoppingAddressDao, ShoppingAddressEntity> implements ShoppingAddressService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper wrapper = new EntityWrapper<ShoppingAddressEntity>();
        wrapper.eq("user_id",params.get("userId"));
        Page<ShoppingAddressEntity> page = this.selectPage(
                new Query<ShoppingAddressEntity>(params).getPage(),
                wrapper
        );
        return new PageUtils(page);
    }

}
