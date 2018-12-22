package cn.gribe.modules.business.controller;

import java.util.Arrays;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.gribe.entity.OrderEntity;
import cn.gribe.modules.business.service.CdOrderService;
import cn.gribe.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-24 15:34:15
 */
@RestController
@RequestMapping("business/cdorder")
public class CdOrderController {
    @Autowired
    private CdOrderService cdOrderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:cdorder:list")
    public R list(Integer page,Integer limit,String phone,String storeName,String startTime,String endTime){
        PageUtils res = cdOrderService.queryPage(page,limit,phone,storeName,startTime,endTime);
        return R.ok().put("page", res);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("business:cdorder:info")
    public R info(@PathVariable("id") Integer id){
        OrderEntity cdOrder = cdOrderService.selectById(id);

        return R.ok().put("cdOrder", cdOrder);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:cdorder:save")
    public R save(@RequestBody OrderEntity cdOrder){
        cdOrderService.insert(cdOrder);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:cdorder:update")
    public R update(@RequestBody OrderEntity cdOrder){
        ValidatorUtils.validateEntity(cdOrder);
        cdOrderService.updateAllColumnById(cdOrder);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:cdorder:delete")
    public R delete(@RequestBody Integer[] ids){
        cdOrderService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
