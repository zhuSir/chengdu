package cn.gribe.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.gribe.annotation.Login;
import cn.gribe.annotation.LoginUser;
import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;
import cn.gribe.common.utils.oss.OSSFactory;
import cn.gribe.entity.PostEntity;
import cn.gribe.service.CommentService;
import cn.gribe.service.PostService;
import cn.gribe.common.validator.Assert;
import cn.gribe.common.validator.ValidatorUtils;
import cn.gribe.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * 帖子
 */
@RestController
@RequestMapping("/api/post")
public class ApiPostController {
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    /**
     * 列表
     * @param params
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = postService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     * @param id
     * @return
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        Assert.isNull(id,"获取帖子错误，请刷新重试");
        PostEntity post = postService.selectById(id);
        Assert.isNull(post,"获取帖子错误，请刷新重试");
        //获取评论
        Map params = new HashMap();
        params.put("postId",post.getId());
        PageUtils commentList = commentService.queryPage(params);
        R res = R.ok().put("post", post);
        res.put("commentList",commentList);
        return res;
    }

    /**
     * 保存
     */
    @Login
    @RequestMapping("/save")
    public R save(@RequestParam(value = "file", required = false) MultipartFile[] files, PostEntity post,@LoginUser UserEntity user) throws IOException {
        //表单校验
        ValidatorUtils.validateEntity(post);
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
        post.setUserId(user.getId());
        post.setCreateTime(new Date());
        post.setUpdateTime(new Date());
        post.setImgs(urls.toString());
        postService.insert(post);
        return R.ok();
    }

}
