package cn.gribe.modules.business.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;
import cn.gribe.modules.oss.cloud.OSSFactory;
import cn.gribe.modules.sys.entity.SysDictEntity;
import cn.gribe.modules.sys.service.SysDictService;
import cn.gribe.common.validator.ValidatorUtils;
import cn.gribe.entity.StoreEntity;
import cn.gribe.modules.business.service.CdStoreService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.gribe.entity.ActivityEntity;
import cn.gribe.modules.business.service.CdActivityService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 */
@RestController
@RequestMapping("business/cdactivity")
public class CdActivityController {
    @Autowired
    private CdActivityService cdActivityService;

    @Autowired
    private SysDictService dictService;

    @Autowired
    private CdStoreService storeService;

    /**
     * 信息
     */
    @RequestMapping("/init")
    public R init(){
        R r = R.ok();
        List<SysDictEntity> type = dictService.getDict("activity_type");
        if(type != null){
            r.put("type", type);
        }
        List<SysDictEntity>  locations = dictService.getDict("activity_locations");
        if(locations != null){
            r.put("locationType",locations);
        }
        List<SysDictEntity>  states = dictService.getDict("activity_state");
        if(states != null){
            r.put("states",states);
        }
        List<StoreEntity> storesList = storeService.queryAllStore();
        if(storesList != null){
            r.put("storesList",storesList);
        }
        return r;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:cdactivity:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cdActivityService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("business:cdactivity:info")
    public R info(@PathVariable("id") Integer id){
        ActivityEntity cdActivity = cdActivityService.selectById(id);

        return R.ok().put("cdActivity", cdActivity);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:cdactivity:save")
    public R save(@RequestParam(value = "file", required = false) MultipartFile file,ActivityEntity cdActivity) throws IOException {
        //TODO 验证参数；保存图片到阿里云；内容需通过审核接口审核
        //TODO 图片检测（是否涉黄）
        if (file != null && !file.isEmpty()) {
            //上传文件
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
            cdActivity.setImgs(url);
        }
        if(cdActivity.getId() != null){
            cdActivity.setCreateTime(new Date());
        }
        cdActivity.setUpdateTime(new Date());
        cdActivityService.insertOrUpdate(cdActivity);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:cdactivity:update")
    public R update(@RequestParam(value = "file", required = false) MultipartFile file,ActivityEntity cdActivity) throws IOException {
        ValidatorUtils.validateEntity(cdActivity);
        //TODO 验证参数；保存图片到阿里云；内容需通过审核接口审核
        //TODO 图片检测（是否涉黄）
        //上传文件
        System.out.println("=== cdActivity: "+cdActivity.toString());
        if (file != null && !file.isEmpty()) {
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
            cdActivity.setImgs(url);
        }
        cdActivity.setUpdateTime(new Date());
        cdActivityService.updateAllColumnById(cdActivity);//全部更新
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:cdactivity:delete")
    public R delete(@RequestBody Integer[] ids){
        cdActivityService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

}
