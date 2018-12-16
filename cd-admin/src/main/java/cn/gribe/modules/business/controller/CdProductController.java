package cn.gribe.modules.business.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;
import cn.gribe.modules.business.entity.CdProductEntity;
import cn.gribe.modules.business.service.CdProductService;
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
 * @date 2018-11-24 15:34:15
 */
@RestController
@RequestMapping("business/cdproduct")
public class CdProductController {
    @Autowired
    private CdProductService cdProductService;

    @Autowired
    private SysDictService sysDictService;

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
        return r;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:cdproduct:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cdProductService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("business:cdproduct:info")
    public R info(@PathVariable("id") Integer id){
        CdProductEntity cdProduct = cdProductService.selectById(id);

        return R.ok().put("cdProduct", cdProduct);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:cdproduct:save")
    public R save(@RequestBody CdProductEntity cdProduct){
        cdProductService.insert(cdProduct);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:cdproduct:update")
    public R update(@RequestBody CdProductEntity cdProduct){
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
