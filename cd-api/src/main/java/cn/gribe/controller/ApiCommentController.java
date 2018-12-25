package cn.gribe.controller;

import cn.gribe.annotation.LoginUser;
import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;
import cn.gribe.service.CommentService;
import cn.gribe.annotation.Login;
import cn.gribe.common.utils.oss.OSSFactory;
import cn.gribe.common.validator.Assert;
import cn.gribe.common.validator.ValidatorUtils;
import cn.gribe.entity.CommentEntity;
import cn.gribe.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
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
    public R list(@RequestParam Map<String, Object> params){
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
        //TODO 验证参数；保存图片到阿里云；内容需通过审核接口审核
        StringBuffer urls = new StringBuffer();
        //TODO 图片检测（是否涉黄）
        //上传文件
        if(files != null && files.length > 0){
            for(MultipartFile file : files){
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
                urls.append(url+",");
            }
        }
        //上传文件
        comment.setImgs(urls.toString());
        comment.setUserId(user.getId());
        comment.setCreateTime(new Date());
        comment.setUpdateTime(new Date());
        comment.setStatus(CommentEntity.STATUS_DISABLE);//默认未生效
        commentService.insert(comment);
        return R.ok();
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
