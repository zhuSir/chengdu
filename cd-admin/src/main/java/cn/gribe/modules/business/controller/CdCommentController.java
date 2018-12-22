package cn.gribe.modules.business.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.validator.Assert;
import cn.gribe.common.validator.ValidatorUtils;
import cn.gribe.entity.StoreEntity;
import cn.gribe.modules.sys.entity.SysDictEntity;
import cn.gribe.modules.sys.service.SysDictService;
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
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-24 15:34:15
 */
@RestController
@RequestMapping("business/cdcomment")
public class CdCommentController {
    @Autowired
    private CdCommentService commentService;

    @Autowired
    private SysDictService sysDictService;

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
        PageUtils page = commentService.queryPage(params);
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
