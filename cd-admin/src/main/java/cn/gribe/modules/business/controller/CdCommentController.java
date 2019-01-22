package cn.gribe.modules.business.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.validator.ValidatorUtils;
import cn.gribe.entity.StoreEntity;
import cn.gribe.modules.business.service.CdStoreService;
import cn.gribe.modules.sys.entity.SysDictEntity;
import cn.gribe.modules.sys.entity.SysUserEntity;
import cn.gribe.modules.sys.service.SysDictService;
import cn.gribe.modules.sys.service.SysUserService;
import cn.gribe.modules.sys.shiro.ShiroUtils;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.gribe.entity.CommentEntity;
import cn.gribe.modules.business.service.CdCommentService;
import cn.gribe.common.utils.R;



/**
 *
 */
@RestController
@RequestMapping("business/cdcomment")
public class CdCommentController {
    @Autowired
    private CdCommentService commentService;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private CdStoreService storeService;

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/init")
    public R init(){
        R r = R.ok();
        List<SysDictEntity> type = sysDictService.getDict("comment_type");
        if(type != null){
            r.put("type",type);
        }
        List<SysDictEntity> status = sysDictService.getDict("state");
        r.put("status",status);
        return r;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:cdcomment:list")
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
                page = commentService.queryPage(params);
            }
        }else{
            page = commentService.queryPage(params);
        }
//        PageUtils page = commentService.queryPage(params);
        return R.ok().put("page",page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("business:cdcomment:info")
    public R info(@PathVariable("id") Integer id){
        CommentEntity cdComment = commentService.selectById(id);

        return R.ok().put("cdComment", cdComment);
    }

    /**
     * 保存
     */
//    @RequestMapping("/save")
//    @RequiresPermissions("business:cdcomment:save")
//    public R save(@RequestBody CommentEntity comment){
//        ValidatorUtils.validateEntity(comment);
//        comment.setCreateTime(new Date());
//        comment.setUpdateTime(new Date());
//        commentService.insert(comment);
//        return R.ok();
//    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:cdcomment:update")
    public R update(@RequestBody CommentEntity cdComment){
        ValidatorUtils.validateEntity(cdComment);
        commentService.updateAllColumnById(cdComment);//全部更新
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:cdcomment:delete")
    public R delete(@RequestBody Integer[] ids){
        commentService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

}
