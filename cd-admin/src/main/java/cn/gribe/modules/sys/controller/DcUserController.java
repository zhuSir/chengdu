package cn.gribe.modules.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;
import cn.gribe.entity.UserEntity;
import cn.gribe.modules.sys.entity.SysUserEntity;
import cn.gribe.modules.sys.service.DcUserService;
import cn.gribe.common.validator.ValidatorUtils;
import cn.gribe.modules.sys.shiro.ShiroUtils;
import com.alibaba.fastjson.JSONArray;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.gribe.modules.sys.entity.DcUserEntity;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-07 17:38:54
 */
@RestController
@RequestMapping("sys/dcuser")
public class DcUserController {
    @Autowired
    private DcUserService dcUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dcuser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dcUserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:dcuser:info")
    public R info(@PathVariable("id") Integer id){
        DcUserEntity dcUser = dcUserService.selectById(id);

        return R.ok().put("dcUser", dcUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dcuser:save")
    public R save(@RequestBody DcUserEntity dcUser){
        dcUserService.insert(dcUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dcuser:update")
    public R update(@RequestBody DcUserEntity dcUser){
        ValidatorUtils.validateEntity(dcUser);
        dcUserService.updateAllColumnById(dcUser);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dcuser:delete")
    public R delete(@RequestBody Integer[] ids){
        dcUserService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
