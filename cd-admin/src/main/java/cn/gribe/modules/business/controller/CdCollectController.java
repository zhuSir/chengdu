package cn.gribe.modules.business.controller;

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

import cn.gribe.entity.CollectEntity;
import cn.gribe.modules.business.service.CdCollectService;
import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-24 15:34:15
 */
@RestController
@RequestMapping("business/cdcollect")
public class CdCollectController {
    @Autowired
    private CdCollectService cdCollectService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:cdcollect:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cdCollectService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("business:cdcollect:info")
    public R info(@PathVariable("id") Integer id){
        CollectEntity cdCollect = cdCollectService.selectById(id);

        return R.ok().put("cdCollect", cdCollect);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:cdcollect:save")
    public R save(@RequestBody CollectEntity cdCollect){
        cdCollectService.insert(cdCollect);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:cdcollect:update")
    public R update(@RequestBody CollectEntity cdCollect){
        ValidatorUtils.validateEntity(cdCollect);
        cdCollectService.updateAllColumnById(cdCollect);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:cdcollect:delete")
    public R delete(@RequestBody Integer[] ids){
        cdCollectService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
