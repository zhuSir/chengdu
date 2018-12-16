package cn.gribe.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import cn.gribe.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.gribe.modules.sys.entity.DcAgencyUserEntity;
import cn.gribe.modules.sys.service.DcAgencyUserService;
import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-07 17:38:54
 */
@RestController
@RequestMapping("sys/dcagencyuser")
public class DcAgencyUserController {
    @Autowired
    private DcAgencyUserService dcAgencyUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dcagencyuser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dcAgencyUserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:dcagencyuser:info")
    public R info(@PathVariable("id") Integer id){
        DcAgencyUserEntity dcAgencyUser = dcAgencyUserService.selectById(id);

        return R.ok().put("dcAgencyUser", dcAgencyUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dcagencyuser:save")
    public R save(@RequestBody DcAgencyUserEntity dcAgencyUser){
        dcAgencyUserService.insert(dcAgencyUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dcagencyuser:update")
    public R update(@RequestBody DcAgencyUserEntity dcAgencyUser){
        ValidatorUtils.validateEntity(dcAgencyUser);
        dcAgencyUserService.updateAllColumnById(dcAgencyUser);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dcagencyuser:delete")
    public R delete(@RequestBody Integer[] ids){
        dcAgencyUserService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
