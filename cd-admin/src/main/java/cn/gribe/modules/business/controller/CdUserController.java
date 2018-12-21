package cn.gribe.modules.business.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;
import cn.gribe.entity.UserEntity;
import cn.gribe.modules.business.service.CdUserService;
import cn.gribe.modules.sys.entity.SysDictEntity;
import cn.gribe.modules.sys.service.SysDictService;
import cn.gribe.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-24 15:34:14
 */
@RestController
@RequestMapping("business/cduser")
public class CdUserController {
    @Autowired
    private CdUserService cdUserService;

    @Autowired
    private SysDictService sysDictService;

    /**
     * 初始化信息
     */
    @RequestMapping("/init")
    public R init(){
        R r = R.ok();
        List<SysDictEntity> state = sysDictService.getDict("state");
        if(state != null){
            r.put("state",state);
        }
        return r;
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:cduser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cdUserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("business:cduser:info")
    public R info(@PathVariable("id") Integer id){
        UserEntity cdUser = cdUserService.selectById(id);

        return R.ok().put("cdUser", cdUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:cduser:save")
    public R save(@RequestBody UserEntity cdUser){
        ValidatorUtils.validateEntity(cdUser);
        cdUserService.insert(cdUser);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:cduser:update")
    public R update(@RequestBody UserEntity cdUser){
        ValidatorUtils.validateEntity(cdUser);
        cdUserService.updateAllColumnById(cdUser);//全部更新
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:cduser:delete")
    public R delete(@RequestBody Integer[] ids){
        cdUserService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
