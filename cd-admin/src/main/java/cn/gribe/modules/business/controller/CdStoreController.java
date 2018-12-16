package cn.gribe.modules.business.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;
import cn.gribe.modules.sys.entity.SysDictEntity;
import cn.gribe.common.validator.ValidatorUtils;
import cn.gribe.modules.sys.service.SysConfigService;
import cn.gribe.modules.sys.service.SysDictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.gribe.modules.business.entity.CdStoreEntity;
import cn.gribe.modules.business.service.CdStoreService;


/**
 * 店铺
 */
@RestController
@RequestMapping("business/cdstore")
public class CdStoreController {
    @Autowired
    private CdStoreService cdStoreService;

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private SysDictService sysDictService;

    /**
     * 初始化信息
     */
    @RequestMapping("/init")
    public R init(){
        R r = R.ok();
        List<SysDictEntity> storeType = sysDictService.getDict("store_type");
        if(storeType != null){
            r.put("storeType",storeType);
        }
        return r;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:cdstore:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cdStoreService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("business:cdstore:info")
    public R info(@PathVariable("id") Integer id){
        CdStoreEntity cdStore = cdStoreService.selectById(id);

        return R.ok().put("cdStore", cdStore);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:cdstore:save")
    public R save(@RequestBody CdStoreEntity store){
        store.setCreateTime(new Date());
        store.setUpdateTime(new Date());
        cdStoreService.insert(store);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:cdstore:update")
    public R update(@RequestBody CdStoreEntity store){
        ValidatorUtils.validateEntity(store);
        store.setUpdateTime(new Date());
        cdStoreService.updateAllColumnById(store);//全部更新
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:cdstore:delete")
    public R delete(@RequestBody Integer[] ids){
        cdStoreService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
