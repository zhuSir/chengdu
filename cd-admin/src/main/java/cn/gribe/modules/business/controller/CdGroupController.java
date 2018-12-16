package cn.gribe.modules.business.controller;

import java.util.Arrays;
import java.util.Map;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;
import cn.gribe.modules.business.entity.CdGroupEntity;
import cn.gribe.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.gribe.modules.business.service.CdGroupService;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-24 15:34:15
 */
@RestController
@RequestMapping("business/cdgroup")
public class CdGroupController {
    @Autowired
    private CdGroupService cdGroupService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:cdgroup:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cdGroupService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("business:cdgroup:info")
    public R info(@PathVariable("id") Integer id){
        CdGroupEntity cdGroup = cdGroupService.selectById(id);

        return R.ok().put("cdGroup", cdGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:cdgroup:save")
    public R save(@RequestBody CdGroupEntity cdGroup){
        cdGroupService.insert(cdGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:cdgroup:update")
    public R update(@RequestBody CdGroupEntity cdGroup){
        ValidatorUtils.validateEntity(cdGroup);
        cdGroupService.updateAllColumnById(cdGroup);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:cdgroup:delete")
    public R delete(@RequestBody Integer[] ids){
        cdGroupService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
