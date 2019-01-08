package cn.gribe.modules.business.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.gribe.common.CommonUtils;
import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;
import cn.gribe.common.validator.Assert;
import cn.gribe.entity.ProductEntity;
import cn.gribe.entity.ProductEntity;
import cn.gribe.entity.ProductTagEntity;
import cn.gribe.entity.StoreEntity;
import cn.gribe.modules.business.service.CdProductService;
import cn.gribe.modules.business.service.CdStoreService;
import cn.gribe.modules.business.service.ProductTagService;
import cn.gribe.modules.oss.cloud.OSSFactory;
import cn.gribe.modules.sys.entity.SysDictEntity;
import cn.gribe.modules.sys.entity.SysUserEntity;
import cn.gribe.modules.sys.service.SysDictService;
import cn.gribe.common.validator.ValidatorUtils;
import cn.gribe.modules.sys.shiro.ShiroUtils;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("business/cdproduct")
public class CdProductController {
    @Autowired
    private CdProductService cdProductService;

    @Autowired
    private ProductTagService productTagService;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private CdStoreService storeService;

    /**
     * 信息
     */
    @RequestMapping("/init")
    public R init(){
        R r = R.ok();
        List<SysDictEntity> state = sysDictService.getDict("product_state");
        if(state != null){
            r.put("state",state);
        }
        SysUserEntity user = ShiroUtils.getUserEntity();
        //判断如果用户有关联店铺则给与他查询店铺的信息
        StoreEntity storeEntity = storeService.queryByUserId(user.getUserId());
        Integer storeId = null;
        if(storeEntity != null){
            storeId = storeEntity.getId();
        }
        List<StoreEntity> storesList = storeService.queryAllStore(storeId);
        if(storesList != null){
            r.put("storesList",storesList);
        }
        List<SysDictEntity> attributeTypeList = sysDictService.getDict("product_attribute_type");
        if(attributeTypeList != null){
            r.put("attributeTypeList",attributeTypeList);
        }

        return r;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:cdproduct:list")
    public R list(@RequestParam Map<String, Object> params){
        SysUserEntity user = ShiroUtils.getUserEntity();
        //判断如果用户有关联店铺则给与他查询店铺的信息
        StoreEntity storeEntity = storeService.queryByUserId(user.getUserId());
        if(storeEntity != null){
            params.put("storeId",storeEntity.getId());
        }
        PageUtils page = cdProductService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("business:cdproduct:info")
    public R info(@PathVariable("id") Integer id){
        Assert.isNull(id,"数据错误，请刷新重试！");
        ProductEntity cdProduct = cdProductService.selectById(id);
        List<ProductTagEntity> tags = cdProductService.queryTags(id);
        R r = R.ok().put("cdProduct", cdProduct);
        r.put("tags",tags);
        return r;
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:cdproduct:save")
    public R save(@RequestParam(value = "imgs_file", required = false) MultipartFile[] imgs,
                  @RequestParam(value = "short_img_file", required = false) MultipartFile shortImg,
                  ProductEntity product) throws IOException {
        ValidatorUtils.validateEntity(product);
        //图片检测
        CommonUtils.validateImg(imgs);
        //图片检测
        CommonUtils.validateImg(new MultipartFile[]{shortImg});
        if (shortImg != null && !shortImg.isEmpty()) {
            //上传文件
            String suffix = shortImg.getOriginalFilename().substring(shortImg.getOriginalFilename().lastIndexOf("."));
            String url = OSSFactory.build().uploadSuffix(shortImg.getBytes(), suffix);
            product.setShortImg(url);
        }
        StringBuilder imgsUrl = new StringBuilder();
        if(imgs.length > 0){
            for(MultipartFile img : imgs){
                if(!img.isEmpty()){
                    String suffix = img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf("."));
                    String url = OSSFactory.build().uploadSuffix(img.getBytes(), suffix);
                    imgsUrl.append(url+",");
                }
            }
        }
        if(imgsUrl.length() > 0){
            imgsUrl.replace(imgsUrl.lastIndexOf(","),imgsUrl.length(),"");
            product.setImgs(imgsUrl.toString());
        }
        List<ProductTagEntity> tags = product.getTags();
        if(tags != null && tags.size() > 0){
            productTagService.insertOrUpdateBatch(tags);
        }
        if(product.getId() == null){
            product.setCreateTime(new Date());
        }
        product.setUpdateTime(new Date());
        cdProductService.insertOrUpdate(product);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:cdproduct:update")
    public R update(@RequestBody ProductEntity cdProduct){
        ValidatorUtils.validateEntity(cdProduct);
        cdProductService.updateAllColumnById(cdProduct);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:cdproduct:delete")
    public R delete(@RequestBody Integer[] ids){
        cdProductService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
