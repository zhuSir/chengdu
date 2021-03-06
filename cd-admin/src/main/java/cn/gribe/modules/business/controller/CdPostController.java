package cn.gribe.modules.business.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.validator.ValidatorUtils;
import cn.gribe.entity.GroupEntity;
import cn.gribe.modules.business.service.CdGroupService;
import cn.gribe.modules.sys.entity.SysDictEntity;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.gribe.entity.PostEntity;
import cn.gribe.modules.business.service.CdPostService;
import cn.gribe.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-24 15:34:15
 */
@RestController
@RequestMapping("business/cdpost")
public class CdPostController {
    @Autowired
    private CdPostService cdPostService;

    @Autowired
    private CdGroupService groupService;

    /**
     * 信息
     */
    @RequestMapping("/init")
    public R init(){
        R r = R.ok();
        List<GroupEntity> groups = groupService.selectList(new EntityWrapper<>());
        if(groups != null){
            r.put("groups", groups);
        }
        return r;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:cdpost:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cdPostService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("business:cdpost:info")
    public R info(@PathVariable("id") Integer id){
        PostEntity cdPost = cdPostService.selectById(id);

        return R.ok().put("cdPost", cdPost);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:cdpost:save")
    public R save(@RequestBody PostEntity cdPost){
        cdPostService.insert(cdPost);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:cdpost:update")
    public R update(@RequestBody PostEntity cdPost){
        ValidatorUtils.validateEntity(cdPost);
        cdPostService.updateAllColumnById(cdPost);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:cdpost:delete")
    public R delete(@RequestBody Integer[] ids){
        cdPostService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
