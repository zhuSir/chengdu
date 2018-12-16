package cn.gribe.controller;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;
import cn.gribe.entity.OrderEntity;
import cn.gribe.entity.ProductEntity;
import cn.gribe.service.OrderService;
import cn.gribe.service.ProductService;
import cn.gribe.service.StoreService;
import cn.gribe.annotation.Login;
import cn.gribe.annotation.LoginUser;
import cn.gribe.common.validator.Assert;
import cn.gribe.entity.StoreEntity;
import cn.gribe.entity.UserEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;


/**
 * 订单
 */
@RestController
@RequestMapping("/api/order")
public class ApiOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ProductService productService;

    /**
     * 列表
     */
    @Login
    @RequestMapping("/list")
    @ApiOperation("订单列表")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     * @param id
     * @return
     */
    @Login
    @RequestMapping("/info/{id}")
    @ApiOperation("订单详情")
    public R info(@PathVariable("id") Integer id){
        Assert.isNull(id, "参数错误;获取订单详情失败");
        OrderEntity order= orderService.selectById(id);
        //TODO 物流信息;
        Assert.isNull(order,"订单获取失败；请联系管理员");
        StoreEntity storeEntity = storeService.selectById(order.getStoreId());
        R r = R.ok().put("order", order);
        r.put("store",storeEntity);
        return r;
    }

    /**
     * 保存
     * @param order
     * @return
     */
    @Login
    @RequestMapping("/save")
    @ApiOperation("保存订单")
    public R save(OrderEntity order, @LoginUser UserEntity user){
        Assert.isNull(order,"数据错误，请刷新重试");
        order.setCode(UUID.randomUUID().toString());
        order.setUserId(user.getId());
        Assert.isNull(order.getProductId(),"商品错误，请刷新重试");
        ProductEntity product = productService.selectById(order.getProductId());
        Assert.isNull(product,"商品错误，请刷新重试");
        //保存订单运费
        order.setFreight(product.getFreight());
        orderService.insert(order);
        return R.ok();
    }

    /**
     * 执行订单操作
     * @param orderId
     * @param status
     * @return
     */
    @Login
    @RequestMapping("/set")
    @ApiOperation("保存订单")
    public R set(String orderId,Integer status){
        Assert.isNull(orderId,"订单错误，请刷新重试");
        Assert.isNull(status,"订单错误，请刷新重试");
        OrderEntity orderEntity = orderService.selectById(orderId);
        //TODO 订单操作
        return R.ok();
    }

    //TODO 支付接口

}
