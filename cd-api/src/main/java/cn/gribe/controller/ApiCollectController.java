package cn.gribe.controller;

import cn.gribe.annotation.Login;
import cn.gribe.annotation.LoginUser;
import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;
import cn.gribe.common.validator.Assert;
import cn.gribe.entity.CollectEntity;
import cn.gribe.entity.UserEntity;
import cn.gribe.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 收藏
 */
@RestController
@RequestMapping("/api/collect")
public class ApiCollectController {
    @Autowired
    private CollectService collectService;

    /**
     * 列表
     */
    @Login
    @RequestMapping("/list")
    public R list(@LoginUser UserEntity user, Integer type){
        Assert.isNull(type,"数据错误，请刷新重试");
        PageUtils page = collectService.queryPage(user.getId(),type);
        return R.ok().put("page", page);
    }

    /**
     * 保存
     */
    @Login
    @RequestMapping("/save")
    public R save(CollectEntity collect,@LoginUser UserEntity user){
        collect.setUserId(user.getId());
        CollectEntity collectEntity = collectService.selectByParams(collect);
        if(collectEntity != null){
            collect.setId(collectEntity.getId());
        }
        collectService.insertOrUpdate(collect);
        return R.ok();
    }

    /**
     * 删除
     */
    @Login
    @RequestMapping("/delete")
    public R delete(@RequestBody CollectEntity collect,@LoginUser UserEntity user){
        collect.setUserId(user.getId());
        CollectEntity resCollect = collectService.selectByParams(collect);
        Assert.state(resCollect == null,"删除收藏错误，请刷新重试");
        collectService.deleteById(resCollect);
        return R.ok();
    }

}
