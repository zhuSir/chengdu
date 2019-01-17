package cn.gribe.controller;

import cn.gribe.common.utils.CommonUtils;
import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;
import cn.gribe.common.utils.alipay.AlipayUtils;
import cn.gribe.common.utils.wxpay.WxpayUtils;
import cn.gribe.common.validator.ValidatorUtils;
import cn.gribe.entity.*;
import cn.gribe.service.CommentService;
import cn.gribe.service.OrderService;
import cn.gribe.service.ProductService;
import cn.gribe.service.StoreService;
import cn.gribe.annotation.Login;
import cn.gribe.annotation.LoginUser;
import cn.gribe.common.validator.Assert;
import com.github.wxpay.sdk.WXPayUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
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

    @Autowired
    private CommentService commentService;


    /**
     * 列表
     */
    @Login
    @RequestMapping("/list")
    @ApiOperation("订单列表")
    public R list(@RequestParam Map<String, Object> params,@LoginUser UserEntity user){
        params.put("userId",user.getId());
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
        Assert.isNull(order,"订单获取失败；请联系管理员");
        StoreEntity storeEntity = storeService.selectById(order.getStoreId());
        ProductEntity productEntity= productService.selectById(order.getProductId());
        Assert.isNull(productEntity,"订单错误，请联系管理员");
        order.setProductShortImg(productEntity.getShortImg());
        order.setProductAbout(productEntity.getAbout());
        order.setProductName(productEntity.getName());
        R r = R.ok().put("order", order);
        r.put("product",productEntity);
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
        order.setUserId(user.getId());
        Assert.isNull(order.getProductId(),"商品错误，请刷新重试");
        ProductEntity product = productService.selectById(order.getProductId());
        Assert.isNull(product,"商品错误，请刷新重试");
        //保存订单运费
        order.setFreight(product.getFreight());
        //待支付
        order.setState(OrderEntity.STATE_AWAIT_PAY);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        Object results = orderService.saveAndPay(order,product,user);
        if(results == null){
            return R.error("支付下单失败，请联系管理员");
        }
        R r = R.ok().put("orderCode",order.getCode());
        r.put("results",results);
        r.put("callback",alipayUtils.NOTIFY_URL);
        return r;
    }

    /**
     * 支付后回调接口，存入数据库更新状态为支付成功
     * @return
     */
    @RequestMapping("/callback")
    @ResponseBody
    public String checkParams(HttpServletRequest request){
        String orderNo = request.getParameter("out_trade_no");
        String tradeNo = request.getParameter("trade_no");
        if(StringUtils.isNotEmpty(orderNo)){
            //查询单子
            OrderEntity orderEntity = orderService.queryByCode(orderNo);
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
                    orderEntity.setTradeNo(tradeNo);//支付宝订单号
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
    public R queryAliPayOrder(String orderCode,String tradeNo,@LoginUser UserEntity user){
        org.springframework.util.Assert.state(orderCode != null,"订单错误，请联系管理员");
        OrderEntity orderEntity = orderService.queryByCode(orderCode);
        Assert.isNull(orderEntity,"订单错误，请联系管理员");
        Assert.state(orderEntity.getUserId().intValue() != user.getId().intValue(),
                "query error","回调用户错误；userInfo:"+user.toString());
        //判断支付结果
        if(!OrderEntity.STATE_AWAIT_PAY.equals(orderEntity.getState())){
            return R.ok("支付成功");
        }
        Map<String,Object> status = null;
        //查询是否微信支付
        if(OrderEntity.PAY_TYPE_WECHATPAY.equals(orderEntity.getPayType())){
            status = wxPayUtil.queryOrder(orderCode);
        }else{
            status = alipayUtils.queryAliPayOrder(orderCode);
        }
        if(status != null){
            orderEntity.setPayStatus((Integer) status.get("status"));
            orderEntity.setPayDescription((String) status.get("description"));
            orderEntity.setTradeNo(tradeNo);//支付宝订单号
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

    @Autowired
    private WxpayUtils wxPayUtil;

    /**
     * 支付后回调接口，存入数据库更新状态为支付成功
     * @return
     */
    @RequestMapping("/callback/wx")
    @ResponseBody
    public String wxCallback(HttpServletRequest request) throws Exception {
        System.out.println("微信支付回调");
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        String resultxml = new String(outSteam.toByteArray(), "utf-8");
        Map<String, String> params = WXPayUtil.xmlToMap(resultxml);
        outSteam.close();
        inStream.close();
        //验证微信签名
        if (wxPayUtil.wxpay.isPayResultNotifySignatureValid(params)) {
            String orderNo = params.get("out_trade_no");
            if(StringUtils.isNotEmpty(orderNo)){
                //查询单子
                OrderEntity orderEntity = orderService.queryByCode(orderNo);
                if(orderEntity == null){
                    return "fail";
                }
                //状态为待支付，则更改
                if(orderEntity != null
                        && !OrderEntity.PAY_STATUS_SUCCESS.equals(orderEntity.getPayStatus())
                        && OrderEntity.STATE_AWAIT_PAY.equals(orderEntity.getState())){
                    String resultCode = params.get("result_code");
                    //验证支付宝
                    if("SUCCESS".equals(resultCode)){
                        String tradeStatus = request.getParameter("trade_status");
                        Map<String,Object> status = wxPayUtil.transferStatus(tradeStatus);
                        orderEntity.setPayStatus((Integer)status.get("status"));
                        orderEntity.setPayDescription((String) status.get("description"));
                        orderEntity.setTradeNo(params.get("transaction_id"));//支付宝订单号
                        //支付成功
                        if(OrderEntity.PAY_STATUS_SUCCESS.equals(orderEntity.getPayStatus())){
                            orderEntity.setState(OrderEntity.STATE_AWAIT_USE);
                        }
                        orderService.updateById(orderEntity);
                        return "success";
                    }
                }
            }
        }
        return "fail";
    }

    /**
     * 订单取消
     * @param orderId
     * @return
     */
    @Login
    @RequestMapping("/cancel")
    public R cancel(String orderId,@LoginUser UserEntity userEntity){
        Assert.isNull(orderId,"订单错误，请刷新重试");
        OrderEntity orderEntity = orderService.selectById(orderId);
        Assert.isNull(orderEntity,"订单参数错误，请联系管理员");
        Assert.state(!OrderEntity.STATE_AWAIT_PAY.equals(orderEntity.getState()),"该订单不允许取消");
        Assert.state(orderEntity.getUserId().intValue() != userEntity.getId().intValue(),"您当前无权限修改该记录");
        orderEntity.setState(OrderEntity.STATE_FINISHED);
        orderEntity.setPayStatus(OrderEntity.PAY_STATUS_FAIL);
        orderEntity.setPayDescription("支付失败");
        orderService.updateById(orderEntity);
        return R.ok();
    }

    /**
     * 完成订单
     * @param orderId
     * @return
     */
    @Login
    @RequestMapping("/finished")
    public R finished(String orderId,@LoginUser UserEntity userEntity){
        Assert.isNull(orderId,"订单错误，请刷新重试");
        OrderEntity orderEntity = orderService.selectById(orderId);
        Assert.isNull(orderEntity,"订单参数错误，请联系管理员");
        Assert.state(!OrderEntity.STATE_AWAIT_USE.equals(orderEntity.getState()),"该订单错误，不允许完成，请联系管理员");
        Assert.state(orderEntity.getUserId().intValue() != userEntity.getId().intValue(),"您当前无权限修改该记录");
        orderEntity.setState(OrderEntity.STATE_AWAIT_EVALUATE);
        orderService.updateById(orderEntity);
        return R.ok();
    }

    /**
     * 订单评论
     * @param orderId
     * @return
     */
    @Login
    @RequestMapping("/comment")
    public R comment(@RequestParam(value = "file", required = false) MultipartFile[] files,
                     CommentEntity comment, String orderId,@LoginUser UserEntity userEntity) throws IOException {
        Assert.isNull(orderId,"订单错误，请刷新重试");
        OrderEntity orderEntity = orderService.selectById(orderId);
        Assert.isNull(orderEntity,"订单参数错误，请联系管理员");
        Assert.state(!OrderEntity.STATE_AWAIT_EVALUATE.equals(orderEntity.getState()),"该订单不允许评论");
        Assert.state(orderEntity.getUserId().intValue() != userEntity.getId().intValue(),"您当前无权限修改该记录");
        //图片检测
        CommonUtils.validateImg(files);
        //图片内容
        CommonUtils.validateTxt(comment.getContent());
        comment.setProductId(orderEntity.getProductId());//商品id
        comment.setStoreId(orderEntity.getStoreId());
        commentService.save(files,comment,userEntity);//保存评论
        orderEntity.setState(OrderEntity.STATE_FINISHED);
        orderService.updateById(orderEntity);
        return R.ok();
    }

    /**
     * 订单签名（微信）
     * @param orderId
     * @return
     */
    @Login
    @RequestMapping("/sign")
    public R sign(String orderId,@LoginUser UserEntity userEntity){
        Assert.isNull(orderId,"订单错误，请刷新重试");
        OrderEntity orderEntity = orderService.selectById(orderId);
        Assert.isNull(orderEntity,"订单参数错误，请联系管理员");
        Assert.state(orderEntity.getUserId() != userEntity.getId(),"订单错误，请联系管理员");
        Assert.state(!OrderEntity.PAY_TYPE_WECHATPAY.equals(orderEntity.getPayType()),"订单错误，该订单支付方式不是微信支付");
        Assert.state(!OrderEntity.STATE_AWAIT_PAY.equals(orderEntity.getState()),"订单错误，该订单状态不允许支付");
        try {
            Map res = orderService.wechatPaySign(orderEntity);
            return R.ok().put("results",res);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }



}
