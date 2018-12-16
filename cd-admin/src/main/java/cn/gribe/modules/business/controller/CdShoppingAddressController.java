package cn.gribe.modules.business.controller;

import java.util.Arrays;
import java.util.Map;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;
import cn.gribe.modules.business.entity.CdShoppingAddressEntity;
import cn.gribe.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.gribe.modules.business.service.CdShoppingAddressService;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-24 15:34:15
 */
@RestController
@RequestMapping("business/cdshoppingaddress")
public class CdShoppingAddressController {
    @Autowired
    private CdShoppingAddressService cdShoppingAddressService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:cdshoppingaddress:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cdShoppingAddressService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("business:cdshoppingaddress:info")
    public R info(@PathVariable("id") Integer id){
        CdShoppingAddressEntity cdShoppingAddress = cdShoppingAddressService.selectById(id);

        return R.ok().put("cdShoppingAddress", cdShoppingAddress);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:cdshoppingaddress:save")
    public R save(@RequestBody CdShoppingAddressEntity cdShoppingAddress){
        cdShoppingAddressService.insert(cdShoppingAddress);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:cdshoppingaddress:update")
    public R update(@RequestBody CdShoppingAddressEntity cdShoppingAddress){
        ValidatorUtils.validateEntity(cdShoppingAddress);
        cdShoppingAddressService.updateAllColumnById(cdShoppingAddress);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:cdshoppingaddress:delete")
    public R delete(@RequestBody Integer[] ids){
        cdShoppingAddressService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
