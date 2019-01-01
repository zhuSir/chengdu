package cn.gribe.controller;


import cn.gribe.annotation.LoginUser;
import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;
import cn.gribe.common.validator.Assert;
import cn.gribe.entity.CollectEntity;
import cn.gribe.entity.StoreEntity;
import cn.gribe.entity.UserEntity;
import cn.gribe.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 店铺接口
 */
@RestController
@RequestMapping("/api/store")
@Api(tags="店铺接口")
public class ApiStoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CollectService collectService;

    /**
     * 店铺列表
     * lat,lon,type,page,limit
     * @param params
     * @return
     */
    @RequestMapping("/list")
    @ApiOperation("店铺列表")
    public R list(@RequestParam Map<String, Object> params){
        Assert.isNull(params, "参数错误;");
        Assert.isNull(params.get("lat"), "参数错误; 经纬度获取失败");
        Assert.isNull(params.get("lon"), "参数错误; 经纬度获取失败");
        Assert.isNull(params.get("type"),"参数错误，获取类型失败");
        PageUtils page = storeService.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/info/{id}")
    @ApiOperation("店铺详情")
    public R info(@PathVariable("id") Integer id, @LoginUser UserEntity user){
        Assert.isNull(id, "参数错误;获取店铺详情失败");
        //获取店铺详情
        StoreEntity store = storeService.selectById(id);
        Assert.isNull(store,"获取店铺详情错误，请联系管理员");
        R r = R.ok().put("info",store);
        if(user != null){
            CollectEntity collectEntity = new CollectEntity();
            collectEntity.setUserId(user.getId());
            collectEntity.setStoreId(store.getId());
            collectEntity = collectService.selectByParams(collectEntity);
            if(collectEntity != null){
                store.setCollected(true);
            }
        }
        //获取产品列表
        Map params = new HashMap();
        params.put("storeId",store.getId());
        //获取产品列表
        PageUtils productList = productService.queryPage(params);
        r.put("productList",productList);
        //获取评论列表
        PageUtils commentList = commentService.queryPage(params);
        r.put("commentList",commentList);
        return r;
    }

}
