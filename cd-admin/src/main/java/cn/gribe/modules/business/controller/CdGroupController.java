package cn.gribe.modules.business.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import cn.gribe.common.CommonUtils;
import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;
import cn.gribe.entity.GroupEntity;
import cn.gribe.common.validator.ValidatorUtils;
import cn.gribe.modules.oss.cloud.OSSFactory;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.gribe.modules.business.service.CdGroupService;
import org.springframework.web.multipart.MultipartFile;


/**
 * 小组管理
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
        GroupEntity cdGroup = cdGroupService.selectById(id);
        return R.ok().put("cdGroup", cdGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:cdgroup:save")
    public R save(@RequestParam(value = "publicity_imgs_file", required = false) MultipartFile[] publicityImgs,
                  @RequestParam(value = "backstage_imgs_file", required = false) MultipartFile[] backstageImgs,
                  @RequestParam(value = "head_img_file", required = false) MultipartFile headImg,
                  GroupEntity group) throws IOException {
        ValidatorUtils.validateEntity(group);
        //图片检测
        CommonUtils.validateImg(publicityImgs);
        //图片检测
        CommonUtils.validateImg(backstageImgs);
        if (headImg != null && !headImg.isEmpty()) {
            //上传文件
            String suffix = headImg.getOriginalFilename().substring(headImg.getOriginalFilename().lastIndexOf("."));
            String url = OSSFactory.build().uploadSuffix(headImg.getBytes(), suffix);
            group.setHeadImg(url);
        }
        if (publicityImgs != null && publicityImgs.length > 0) {
            StringBuilder urls = new StringBuilder();
            for(MultipartFile file : publicityImgs){
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
                urls.append(url+",");
            }
            if(urls.length() > 0){
                urls.replace(urls.lastIndexOf(","),urls.length(),"");
                group.setPublicityImgs(urls.toString());
            }
        }
        if (backstageImgs != null && backstageImgs.length > 0) {
            StringBuilder urls = new StringBuilder();
            for(MultipartFile file : backstageImgs){
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
                urls.append(url+",");
            }
            if(StringUtils.isNotEmpty(urls)){
                urls.replace(urls.lastIndexOf(","),urls.length(),"");
                group.setBackstageImgs(urls.toString());
            }
        }
        cdGroupService.insertOrUpdate(group);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:cdgroup:update")
    public R update(@RequestBody GroupEntity cdGroup){
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
