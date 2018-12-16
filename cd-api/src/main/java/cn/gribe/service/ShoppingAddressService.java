package cn.gribe.service;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.entity.ShoppingAddressEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-15 17:53:36
 */
public interface ShoppingAddressService extends IService<ShoppingAddressEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

