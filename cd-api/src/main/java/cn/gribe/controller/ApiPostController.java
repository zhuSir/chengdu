package cn.gribe.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.gribe.annotation.Login;
import cn.gribe.annotation.LoginUser;
import cn.gribe.common.utils.CommonUtils;
import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;
import cn.gribe.common.utils.oss.OSSFactory;
import cn.gribe.entity.CollectEntity;
import cn.gribe.entity.GroupEntity;
import cn.gribe.entity.PostEntity;
import cn.gribe.service.*;
import cn.gribe.common.validator.Assert;
import cn.gribe.common.validator.ValidatorUtils;
import cn.gribe.entity.UserEntity;
import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
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

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @Autowired
    private CollectService collectService;

    /**
     * 列表
     * @param params
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        Object groupId = params.get("groupId");
        //Assert.isNull(groupId,"参数错误，请联系管理员");
        R r = R.ok();
        if(groupId != null){
            GroupEntity groupEntity = groupService.selectById((String) groupId);
            r.put("group",groupEntity);
        }
        PageUtils page = postService.queryPage(params);
        r.put("page", page);
        return r;
    }

    /**
     * 我的列表
     * @param params
     * @return
     */
    @Login
    @RequestMapping("/my/list")
    public R myList(@RequestParam Map<String, Object> params,@LoginUser UserEntity user){
        Object groupId = params.get("groupId");
        //Assert.isNull(groupId,"参数错误，请联系管理员");
        R r = R.ok();
        if(groupId != null){
            GroupEntity groupEntity = groupService.selectById((String) groupId);
            r.put("group",groupEntity);
        }
        if(user != null){
            params.put("userId",user.getId());
        }
        PageUtils page = postService.queryPage(params);
        r.put("page", page);
        return r;
    }


    /**
     * 信息
     * @param id
     * @return
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id, @LoginUser UserEntity user){
        Assert.isNull(id,"获取帖子错误，请刷新重试");
        PostEntity post = postService.selectById(id);
        Assert.isNull(post,"获取帖子错误，请刷新重试");
        //获取用户头像等信息
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("id",post.getUserId());
        UserEntity userEntity = userService.selectOne(wrapper);
        post.setUserHeadImg(userEntity.getHeadImg());
        post.setUserName(userEntity.getUserName());
        if(user != null){
            CollectEntity collectEntity = new CollectEntity();
            collectEntity.setUserId(user.getId());
            collectEntity.setPostId(post.getId());
            collectEntity = collectService.selectByParams(collectEntity);
            if(collectEntity != null){
                post.setCollected(true);
            }
        }
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
        //图片检测
        CommonUtils.validateImg(files);
        //图片内容
        CommonUtils.validateTxt(post.getContent());
        //上传文件
        if(files != null && files.length > 0){
            for(MultipartFile file : files){
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
                urls.append(url+",");
            }
            if(urls.length() > 0){
                urls.replace(urls.lastIndexOf(","),urls.length(),"");
            }
        }
        post.setUserId(user.getId());
        post.setCreateTime(new Date());
        post.setUpdateTime(new Date());
        post.setImgs(urls.toString());
        postService.insert(post);
        return R.ok().put("post",post);
    }

    /**
     * 信息
     * @param id
     * @return
     */
    @Login
    @RequestMapping("/delete")
    public R delete(Integer id, @LoginUser UserEntity user){
        Assert.isNull(id,"删除帖子错误，请刷新重试");
        PostEntity post = postService.selectById(id);
        Assert.isNull(post,"删除帖子错误，请刷新重试");
        Assert.state(post.getUserId() != user.getId(),"该帖子不是您的，请刷新重试");
        //删除帖子
        postService.deleteById(post.getId());
        R res = R.ok();
        return res;
    }

}
