package cn.gribe.modules.business.controller;

import java.io.IOException;
import java.util.*;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;
import cn.gribe.modules.oss.cloud.OSSFactory;
import cn.gribe.modules.sys.entity.SysDictEntity;
import cn.gribe.common.validator.ValidatorUtils;
import cn.gribe.modules.sys.entity.SysUserEntity;
import cn.gribe.modules.sys.service.SysDictService;
import cn.gribe.modules.sys.service.SysUserService;
import cn.gribe.modules.sys.shiro.ShiroUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.gribe.entity.StoreEntity;
import cn.gribe.modules.business.service.CdStoreService;
import org.springframework.web.multipart.MultipartFile;


/**
 * 店铺
 */
@RestController
@RequestMapping("business/cdstore")
public class CdStoreController {
    @Autowired
    private CdStoreService cdStoreService;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CdStoreService storeService;

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
        SysUserEntity user = ShiroUtils.getUserEntity();
        //判断如果用户有关联店铺则给与他查询店铺的信息
        user = sysUserService.queryByRoleNameAndUserId("商家",user.getUserId());
        if(user != null){
            List<SysUserEntity> users = new ArrayList<>();
            users.add(user);
            r.put("userList",users);
        }else{
            //查询所有商家
            List<SysUserEntity> users = sysUserService.queryAllMerchants();
            if(users != null){
                r.put("userList",users);
            }
        }
        return r;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:cdstore:list")
    public R list(@RequestParam Map<String, Object> params){
        SysUserEntity user = ShiroUtils.getUserEntity();
        //判断如果用户有关联店铺则给与他查询店铺的信息
        StoreEntity storeEntity = cdStoreService.queryByUserId(user.getUserId());
        if(storeEntity != null){
            params.put("storeId",storeEntity.getId());
        }
        PageUtils page = cdStoreService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("business:cdstore:info")
    public R info(@PathVariable("id") Integer id){
        StoreEntity cdStore = cdStoreService.selectById(id);

        return R.ok().put("cdStore", cdStore);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:cdstore:save")
    public R save(@RequestParam(value = "desc_img_file", required = false) MultipartFile[] descImg,
                  @RequestParam(value = "short_img_file", required = false) MultipartFile shortImg,
                  StoreEntity store) throws IOException {
        ValidatorUtils.validateEntity(store);
        //TODO 判断用户是否绑定过一个商家
        if (descImg != null && descImg.length > 0) {
            String urls = "";
            for(MultipartFile file : descImg){
                if(file != null && !file.isEmpty()){
                    String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                    String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
                    urls+=url+",";
                }
            }
            if(StringUtils.isNotEmpty(urls)){
                store.setImgs(urls);
            }
        }
        if(shortImg != null && !shortImg.isEmpty()){
            String suffix = shortImg.getOriginalFilename().substring(shortImg.getOriginalFilename().lastIndexOf("."));
            String url = OSSFactory.build().uploadSuffix(shortImg.getBytes(), suffix);
            store.setShortImg(url);
        }
        if(store.getId() == null){
            store.setCreateTime(new Date());
        }
        store.setUpdateTime(new Date());
        cdStoreService.insertOrUpdate(store);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:cdstore:update")
    public R update(@RequestBody StoreEntity store){
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
