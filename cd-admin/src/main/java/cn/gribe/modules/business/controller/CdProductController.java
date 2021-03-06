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
import cn.gribe.entity.*;
import cn.gribe.entity.ProductEntity;
import cn.gribe.modules.business.service.CdProductService;
import cn.gribe.modules.business.service.CdStoreService;
import cn.gribe.modules.business.service.ProductSpecialPriceService;
import cn.gribe.modules.business.service.ProductTagService;
import cn.gribe.modules.oss.cloud.OSSFactory;
import cn.gribe.modules.sys.entity.SysDictEntity;
import cn.gribe.modules.sys.entity.SysUserEntity;
import cn.gribe.modules.sys.service.SysDictService;
import cn.gribe.common.validator.ValidatorUtils;
import cn.gribe.modules.sys.service.SysUserService;
import cn.gribe.modules.sys.shiro.ShiroUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ProductSpecialPriceService productSpecialPriceService;

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
        //判断如果用户有关联店铺则给与他查询店铺的信息
        user = sysUserService.queryByRoleNameAndUserId("商家",user.getUserId());
        PageUtils page = new PageUtils(new Page<>());
        if(user != null){
            if(storeEntity != null){
                params.put("storeId",storeEntity.getId());
                page = cdProductService.queryPage(params);
            }
        }else{
            page = cdProductService.queryPage(params);
        }
//        PageUtils page = cdProductService.queryPage(params);
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
        List<ProductSpecialPrice> priceList = productSpecialPriceService.selectListByProductId(id);
        R r = R.ok().put("cdProduct", cdProduct);
        r.put("specialPriceList",priceList);
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
        //图片内容
        CommonUtils.validateTxt(product.getAbout());
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
        if(product.getId() == null){
            product.setCreateTime(new Date());
        }
        product.setUpdateTime(new Date());
        cdProductService.insertOrUpdate(product);
        List<ProductTagEntity> tags = product.getTags();
        if(tags != null && tags.size() > 0){
            for(ProductTagEntity tag : tags){
                tag.setProductId(product.getId());
            }
            productTagService.insertOrUpdateBatch(tags);
        }
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

    /**
     * save special price
     */
    @RequestMapping("/save/price")
    public R saveSpecialPrice(@RequestBody ProductSpecialPrice[] prices){
        if(prices != null && prices.length > 0){
            Integer productId = prices[0].getProductId();
            for(ProductSpecialPrice specialPrice : prices){
                specialPrice.transDate();
                specialPrice.setProductId(productId);
                EntityWrapper wrapper = new EntityWrapper();
                wrapper.eq("date",specialPrice.getDate());
                wrapper.eq("product_id",specialPrice.getProductId());
                ProductSpecialPrice oldSpecialPrice = productSpecialPriceService.selectOne(wrapper);
                if(oldSpecialPrice != null){
                    oldSpecialPrice.setInventory(specialPrice.getInventory());
                    BeanUtils.copyProperties(oldSpecialPrice,specialPrice,"inventory","price");
                }
            }
            productSpecialPriceService.insertOrUpdateBatch(Arrays.asList(prices));
        }
        return R.ok();
    }

}
