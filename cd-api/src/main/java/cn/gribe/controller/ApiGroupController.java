package cn.gribe.controller;

import java.util.HashMap;
import java.util.Map;

import cn.gribe.annotation.LoginUser;
import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;
import cn.gribe.entity.CollectEntity;
import cn.gribe.entity.GroupEntity;
import cn.gribe.entity.UserEntity;
import cn.gribe.service.CollectService;
import cn.gribe.service.GroupService;
import cn.gribe.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 小组
 */
@RestController
@RequestMapping("/api/group")
public class ApiGroupController {
    @Autowired
    private GroupService cdGroupService;
    @Autowired
    private PostService postService;

    @Autowired
    private CollectService collectService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cdGroupService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id,@LoginUser UserEntity user){
        Assert.notNull(id,"获取小组信息错误，请重新获取");
        GroupEntity group = cdGroupService.selectById(id);
        //帖列表
        Assert.notNull(group,"获取小组信息错误，请重新获取");
        if(user != null) {
            CollectEntity collectEntity = new CollectEntity();
            collectEntity.setUserId(user.getId());
            collectEntity.setGroupId(group.getId());
            collectEntity = collectService.selectByParams(collectEntity);
            if (collectEntity != null) {
                group.setCollected(true);
            }
        }
        Map params =new HashMap();
        params.put("groupId",group.getId());
        PageUtils postList = postService.queryPage(params);
        R res = R.ok().put("group", group);
        res.put("postList",postList);
        return res;
    }

}
