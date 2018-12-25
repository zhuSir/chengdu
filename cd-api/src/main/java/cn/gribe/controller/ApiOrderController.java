package cn.gribe.controller;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;
import cn.gribe.common.utils.alipay.AlipayUtils;
import cn.gribe.common.validator.ValidatorUtils;
import cn.gribe.entity.*;
import cn.gribe.service.OrderService;
import cn.gribe.service.ProductService;
import cn.gribe.service.StoreService;
import cn.gribe.annotation.Login;
import cn.gribe.annotation.LoginUser;
import cn.gribe.common.validator.Assert;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private AlipayUtils alipayUtils;

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
        ValidatorUtils.validateEntity(order);//验证数据
        Assert.isNull(order,"数据错误，请刷新重试");
        order.setCode(UUID.randomUUID().toString());
        order.setUserId(user.getId());
        Assert.isNull(order.getProductId(),"商品错误，请刷新重试");
        ProductEntity product = productService.selectById(order.getProductId());
        Assert.isNull(product,"商品错误，请刷新重试");
        //保存订单运费
        order.setFreight(product.getFreight());
        //待支付
        order.setState(OrderEntity.STATE_AWAIT_PAY);
        String results = orderService.saveAndPay(order,product,user);
        if(results == null){
            return R.error("支付下单失败，请联系管理员");
        }
        R r = R.ok().put("results",results);
        r.put("orderCode",order.getCode());
        return r;
    }

    /**
     * 支付后回调接口，存入数据库更新状态为支付成功
     * @return
     */
    @RequestMapping("/callback")
    @ResponseBody
    public String checkParams(HttpServletRequest request){
        String tradeNo = request.getParameter("out_trade_no");
        if(StringUtils.isNotEmpty(tradeNo)){
            //查询单子
            OrderEntity orderEntity = orderService.queryByCode(tradeNo);
            if(orderEntity == null){
                return "fail";
            }
            //状态为待支付，则更改
            if(orderEntity != null
                    && !OrderEntity.PAY_STATUS_SUCCESS.equals(orderEntity.getPayStatus())
                    && OrderEntity.STATE_AWAIT_PAY.equals(orderEntity.getState())){
                boolean result = alipayUtils.checkParams(request);
                //验证支付宝
                if(result){
                    String tradeStatus = request.getParameter("trade_status");
                    Map<String,Object> status = alipayUtils.transferStatus(tradeStatus);
                    orderEntity.setPayStatus((Integer) status.get("status"));
                    orderEntity.setPayDescription((String) status.get("description"));
                    //支付成功
                    if(OrderEntity.PAY_STATUS_SUCCESS.equals(orderEntity.getPayStatus())){
                        orderEntity.setState(OrderEntity.STATE_AWAIT_USE);
                    }
                    orderService.updateById(orderEntity);
                    return "success";
                }
            }

        }
        return "fail";
    }

    /**
     * 查询支付是否成功
     */
    @Login
    @RequestMapping("/queryResult")
    @ResponseBody
    public R queryAliPayOrder(String orderCode,@LoginUser UserEntity user){
        org.springframework.util.Assert.state(orderCode != null,"订单错误，请联系管理员");
        OrderEntity orderEntity = orderService.queryByCode(orderCode);
        Assert.isNull(orderEntity,"订单错误，请联系管理员");
        Assert.state(orderEntity.getUserId().intValue() != user.getId().intValue(),
                "query error","回调用户错误；userInfo:"+user.toString());
        //判断支付结果
        if(!OrderEntity.STATE_AWAIT_PAY.equals(orderEntity.getState())){
            return R.ok("支付成功");
        }
        Map<String,Object> status = alipayUtils.queryAliPayOrder(orderCode);
        if(status != null){
            orderEntity.setPayStatus((Integer) status.get("status"));
            orderEntity.setPayDescription((String) status.get("description"));
            //支付成功
            if(OrderEntity.PAY_STATUS_SUCCESS.equals(orderEntity.getPayStatus())){
                orderEntity.setState(OrderEntity.STATE_AWAIT_USE);
            }
            orderService.updateById(orderEntity);
            if(OrderEntity.PAY_STATUS_SUCCESS.equals(orderEntity.getPayStatus())){
                return R.ok("支付成功");
            }else{
                return R.error(-1,"支付失败，"+orderEntity.getPayDescription());
            }
        }
        return R.error(-1,"查询支付错误，请稍后重试");
    }

    /**
     * 执行订单操作
     * @param orderId
     * @param status
     * @return
     */
    @Login
    @RequestMapping("/set")
    public R set(String orderId,Integer status){
        Assert.isNull(orderId,"订单错误，请刷新重试");
        Assert.isNull(status,"订单错误，请刷新重试");
        OrderEntity orderEntity = orderService.selectById(orderId);
        //TODO 订单操作
        return R.ok();
    }

}
