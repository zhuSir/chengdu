package cn.gribe.controller;

import cn.gribe.annotation.LoginUser;
import cn.gribe.common.utils.CommonUtils;
import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;
import cn.gribe.service.CommentService;
import cn.gribe.annotation.Login;
import cn.gribe.common.validator.Assert;
import cn.gribe.common.validator.ValidatorUtils;
import cn.gribe.entity.CommentEntity;
import cn.gribe.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


/**
 * 评论
 */
@RestController
@RequestMapping("/api/comment")
public class ApiCommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,@LoginUser UserEntity user){
        if(user != null){
            params.put("userId",user.getId());
        }
        PageUtils page = commentService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        CommentEntity comment = commentService.selectById(id);
        if(comment == null){
            comment = new CommentEntity();
        }
        return R.ok().put("comment", comment);
    }

    /**
     * 保存
     */
    @Login
    @RequestMapping("/save")
    public R save(@RequestParam(value = "file", required = false) MultipartFile[] files,
                  CommentEntity comment, @LoginUser UserEntity user) throws IOException {
        //验证内容
        ValidatorUtils.validateEntity(comment);
        //图片检测
        CommonUtils.validateImg(files);
        //订单
        commentService.save(files,comment,user);
        return R.ok().put("comment",comment);
    }

    /**
     * 删除
     */
    @Login
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer id){
        Assert.isNull(id,"数据错误，请刷新重试s");
        commentService.deleteById(id);
        return R.ok();
    }

}
