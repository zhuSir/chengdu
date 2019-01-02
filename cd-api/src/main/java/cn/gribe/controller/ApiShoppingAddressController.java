package cn.gribe.controller;

import java.util.Date;
import java.util.Map;

import cn.gribe.annotation.Login;
import cn.gribe.annotation.LoginUser;
import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;
import cn.gribe.entity.ShoppingAddressEntity;
import cn.gribe.service.ShoppingAddressService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import cn.gribe.common.validator.ValidatorUtils;
import cn.gribe.entity.UserEntity;
import com.sun.org.apache.xerces.internal.impl.dv.dtd.ENTITYDatatypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 收货地址
 */
@RestController
@RequestMapping("/api/shopping/address")
public class ApiShoppingAddressController {
    @Autowired
    private ShoppingAddressService shoppingAddressService;

    /**
     * 列表
     */
    @Login
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, @LoginUser UserEntity user){
        params.put("userId",user.getId());
        PageUtils page = shoppingAddressService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @Login
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id, @LoginUser UserEntity user){
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("id",id);
        wrapper.eq("user_id",user.getId());
        ShoppingAddressEntity cdShoppingAddress = shoppingAddressService.selectOne(wrapper);
        return R.ok().put("cdShoppingAddress", cdShoppingAddress);
    }

    /**
     * 保存
     */
    @Login
    @RequestMapping("/save")
    public R save(ShoppingAddressEntity shoppingAddress,@LoginUser UserEntity user){
        //参数校验
        ValidatorUtils.validateEntity(shoppingAddress);
        shoppingAddress.setUserId(user.getId());
        if(shoppingAddress.getId() == 0){
            shoppingAddress.setCreateTime(new Date());
            shoppingAddress.setIsDelete(0);
        }
        if(1 == shoppingAddress.getIsDefault()){
            EntityWrapper wrapper = new EntityWrapper();
            wrapper.eq("user_id",user.getId());
            wrapper.eq("is_default",1);
            ShoppingAddressEntity shoppingAddressEntity = shoppingAddressService.selectOne(wrapper);
            shoppingAddressEntity.setIsDefault(0);
            shoppingAddressService.updateById(shoppingAddressEntity);
        }
        shoppingAddress.setUpdateTime(new Date());
        shoppingAddressService.insertOrUpdate(shoppingAddress);
        return R.ok();
    }

    /**
     * 删除
     */
    @Login
    @RequestMapping("/delete")
    public R delete(Integer id,@LoginUser UserEntity user){
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("id",id);
        wrapper.eq("user_id",user.getId());
        shoppingAddressService.delete(wrapper);
        return R.ok();
    }

}
