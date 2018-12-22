package cn.gribe.common.utils.alipay;

import cn.gribe.entity.AliPayOrder;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 阿里支付工具类
 * Created by Zhugw on 2018/9/4 0004.
 */
@Component
public class AlipayUtils {

    public static Logger logger = LoggerFactory.getLogger(AlipayUtils.class);

    @Value("${alipay.isSandBox}")
    private boolean isSandBox = true;

    @Value("${alipay.TEST_APP_ID}")
    private String TEST_APP_ID;

    @Value("${alipay.APP_ID}")
    private String APP_ID;

    @Value("${alipay.TEST_SERVER_URL}")
    private String TEST_SERVER_URL;

    @Value("${alipay.SERVER_URL}")
    private String SERVER_URL;

    //APP私钥
    @Value("${alipay.APP_PRIVATE_KEY}")
    private String APP_PRIVATE_KEY;

    //APP公钥
    @Value("${alipay.APP_PUBLIC_KEY}")
    private String APP_PUBLIC_KEY;

    //阿里公钥
    @Value("${alipay.APP_PUBLIC_KEY}")
    private String ALIPAY_PUBLIC_KEY;

    //商户外网可以访问的异步地址
    @Value("${alipay.NOTIFY_URL}")
    private String NOTIFY_URL;

    private AlipayClient alipayClient;

    public AlipayClient getAlipayClient(){
        if(alipayClient == null){
            if(isSandBox){
                alipayClient = new DefaultAlipayClient(TEST_SERVER_URL, TEST_APP_ID, APP_PRIVATE_KEY, "json", "UTF-8", ALIPAY_PUBLIC_KEY, "RSA2");
            }else{
                alipayClient = new DefaultAlipayClient(SERVER_URL, APP_ID, APP_PRIVATE_KEY, "json", "UTF-8", ALIPAY_PUBLIC_KEY, "RSA2");;
            }
        }
        return alipayClient;
    }

    /**
     * 创建支付订单（返回签名）
     * @param payOrder
     */
    public String getAliPayOrder(AliPayOrder payOrder){
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(payOrder.getRemark());//交易描述
        model.setSubject(payOrder.getSubject());//商品名称
        model.setOutTradeNo(payOrder.getOrderNo());//订单号
        //model.setTimeoutExpress("30m");//超时时间
        model.setTotalAmount(payOrder.getAmount());//金额
        model.setProductCode("QUICK_MSECURITY_PAY");//销售类固定值
        request.setBizModel(model);
        request.setNotifyUrl(NOTIFY_URL);//回调通知url（必须和后台设置中一样）
        try {

            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = getAlipayClient().sdkExecute(request);
            //就是orderString 可以直接给客户端请求，无需再做处理。
            String orderString = response.getBody();
            logger.info("下单成功，"+orderString);
            return orderString;
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        logger.error("支付下单错误，"+ JSONObject.toJSONString(payOrder));
        return null;
    }

    /**
     * 状态判断
     * WAIT_BUYER_PAY	交易创建，等待买家付款
     * TRADE_CLOSED	未付款交易超时关闭，或支付完成后全额退款
     * TRADE_FINISHED	交易结束，不可退款
     * TRADE_SUCCESS	交易支付成功
     * @param status
     */
    public Map<String,Object> transferStatus(String status){
        Map results = new HashMap();
        if(status != null){
            if(status.equals("WAIT_BUYER_PAY")){
                results.put("status",-1);
                results.put("description","交易创建，等待买家付款");
            }else if(status.equals("TRADE_CLOSED")){
                results.put("status",-2);
                results.put("description","未付款交易超时关闭，或支付完成后全额退款");
            }else if(status.equals("TRADE_FINISHED")){
                results.put("status",1);
                results.put("description","交易结束，不可退款");
            }else if(status.equals("TRADE_SUCCESS")){
                results.put("status",1);
                results.put("description","交易支付成功");
            }
        }else{
            results.put("status",0);
            results.put("description","交易错误");
        }
        return results;
    }

    /**
     * 查询支付结果
     * @param orderNo
     * @return
     */
    public Map queryAliPayOrder(String orderNo) {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//创建API对应的request类
        Map params = new HashMap();
        params.put("out_trade_no",String.valueOf(orderNo));
        //params.put("trade_no",String.valueOf(payOrder.getTradeNo()));
        request.setBizContent(params.toString());//设置业务参数
        AlipayTradeQueryResponse response = null;//通过alipayClient调用API，获得对应的response类
        try {
            response = getAlipayClient().execute(request);
            String tradeStatus = response.getTradeStatus();
            Map statusMap = transferStatus(tradeStatus);
            //返回接口结果
            return statusMap;
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        logger.error("查询支付结果错误，orderNo:"+ orderNo);
        return null;
    }

    /**
     * 验证支付结果
     * @param request
     * @return
     * @throws AlipayApiException
     */
    public boolean checkParams(HttpServletRequest request){
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        boolean flag = false;
        try {
            flag = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, "UTF-8","RSA2");
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 退款
     * @param payOrder
     * @return
     * @throws AlipayApiException
     */
    public String orderRefund(AliPayOrder payOrder){
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();//创建API对应的request类
        Map params = new HashMap();
        params.put("out_trade_no",payOrder.getOrderNo());
        params.put("trade_no",payOrder.getTradeNo());
        params.put("out_request_no",payOrder.getOrderNo());
        params.put("refund_amount",payOrder.getAmount());
        request.setBizContent(JSONObject.toJSONString(params));//设置业务参数
        AlipayTradeRefundResponse response = null;//通过alipayClient调用API，获得对应的response类
        try {
            response = getAlipayClient().execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        System.out.print(response.getBody());
        // 根据response中的结果继续业务逻辑处理
        return response.getBody();
    }

    /**
     * 转账
     * @param payOrder
     * @throws AlipayApiException
     */
    public AlipayResponse transferOrder(AliPayOrder payOrder){

        System.out.println("payOrder :"+payOrder.toString());

        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        Map params = new HashMap();
        params.put("out_biz_no",payOrder.getOrderNo());
        params.put("payee_type","ALIPAY_LOGONID");
        params.put("payee_account",payOrder.getAccount());
        params.put("amount",payOrder.getAmount());
        params.put("payer_show_name",payOrder.getSubject());
        params.put("payee_real_name",payOrder.getAccountRealName());
        params.put("remark",payOrder.getRemark());
        request.setBizContent(JSONObject.toJSONString(params));
        AlipayFundTransToaccountTransferResponse response = null;
        AlipayResponse results = new AlipayResponse();
        try {
            response = getAlipayClient().execute(request);
            results.setCode(response.getCode());
            results.setMsg(response.getMsg());
            results.setOut_biz_no(response.getOutBizNo());
            results.setSub_code(response.getSubCode());
            results.setSub_msg(response.getSubMsg());
            results.setSuccess(response.isSuccess());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * 生成订单号
     * @return
     */
    public String createOrderNo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyMMddHHmmssSS");
        String no = sdf.format(new Date());
        return no;
    }

    public static void main(String[] args){
        AlipayUtils alipayUtils = new AlipayUtils();
        AliPayOrder payOrder = new AliPayOrder();
//        payOrder.setOrderNo(createOrderNo());
        payOrder.setAccount("jfdhfp2236@sandbox.com");
        payOrder.setAmount("0.1");
        payOrder.setRemark("remark");
        payOrder.setSubject("商品名称");
        payOrder.setAccountRealName("沙箱环境");
        alipayUtils.transferOrder(payOrder);
    }


}
