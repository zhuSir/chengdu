package cn.gribe.common.utils.alipay;

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
public class AlipayUtils {

    Logger logger = LoggerFactory.getLogger(AlipayUtils.class);

    private static boolean isSandBox = true;

    private static String APP_ID = isSandBox ? "2016091700530265" : "2018090461287363";//"2018090361255303"

    private static String SERVER_URL = isSandBox ? "https://openapi.alipaydev.com/gateway.do" : "https://openapi.alipay.com/gateway.do";

    //APP私钥
    private static String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCoh9wv0ZRbsfTGwBpmLKSLIMXdBap+qS9PnlpKCADFcfXuKs78drmKQmLBcy+74Up14kYfEOk7hf6EKPEgAvpjlPwHRZyoAyLw60Vc7yERUxON42GLgu9q9BXXXSk/DRsqvzHNsDJg4FNlLu40XGgTwe/KwmUNaJEunMlU8evx9ynwt41COBNfQXYQx8RQHN5FGbaLB2UqNN1toTiSjfAjhtJ5aHFX0iAp/Hke+XBhee0tFDzT6+9JRF2ENBcYAVba6hHVjYzHtyxxCEu0VREfAQpGYZInvhglFNbenzREfXDWP98pRn6wyTr/VkFghV50vuwcltfltNiTRKpRdQ5nAgMBAAECggEAJOOADbN5Hs0IAA0CekThS1bEGInp9MmBgpLtvnCcApvcMeKK5fXyzNGVHjThSOWaT81Ufe5Mruu4dyMjDFe6iNuOguT40knFdyh4SUxKV6lwiUkxC8GAOE/vC/HnWla7EJNDCnu7uzq+5lHsb+eeHF0B6Ma+Kb4usR4+b2p4s8CCRnYoNVEWvIrvcS7T1EyDciu3RTfZUUjYMFDNhO1bQFCsb+re9VQv/H18rfC9tr7M1IWQVgqsfRGUgUpe4OkKUInI6PgbxUjxEtvktd0mKUYfjjtM43sJVB+XiWdzrsVojefCj0dSRN43D8GMRWYK1alPswPVHLNGgYaI8pRyWQKBgQD+0Kf+Eq1AzW49m1yOc1TRNhg0oZ5sBtJdGaXRZgwNlCZFvDcxe8UuvezABZ4WvDWI2v0MXsB/ya8q3npM8KK2xYSu60mJXisSNcuO+XxvAbLutcwwtWlOAlWh/x67nA/juGSMDBtBC8n9MUisEXGp1j7LHPxDC+QTGUs6Mt5yAwKBgQCpUHyoTeknvK3VZMymJZY2PB4Wuu/lk6IBrC1EfHWak035/8m1o6MOtoq7aVEmAjMbUzU1bQggdprl9zZno6Hh9eDavLRk04WHXOUxVu3nuVO7cLIFb9WImkM4EUrOmZipRZykNIgHqDyB/qeOuYW8p7TW4OGo2LzjKGZcc4KWzQKBgFEVKD+qxcKXG/sykrMpc9XWtsEYpIaPbST+4krNhAxWFg+eopl2XvpOqSU5JGLrpMVVmav0AqEnFjw36wbMk5pBI8VdTVsOwfnbcFigfr379moLI1xkfYu7JnCT872THWNC64u81ndOPwn4Qr2uF1MJv5V2NbdH8104cT2lri7tAoGAddiWm1J3GPhjiea55l68+YzIaIu+k+040q8zZrq15Fvo5zvY+nsl8ASv9RetqqXXSoqUu1XjVS0YTT3O+T3UXAEWe0jiylbLVtcak0ftGN9OX2VmExCu8IBmjuzCQ6B2uELCbyw9Dg9T5isIFvgcFJ4p6XHmvu30MquY63peh3UCgYEA3xbhSiYvTHdrTCqaonQIUtIX5xGaJygCCgFtNEEDUIqFAadedaEoXIGHwAqtQTJzdwu7CMoqq+iZIGTboSpO4TWGQETDIMZbJsuBsiQ4v+lTNzM+3SPC0+VmSf7bmHjl6MHFWVMT6RZ5tWG+EWPVF78qMFMyab+k1V5r4lwg2R8=";

    //APP公钥
    private static String APP_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqIfcL9GUW7H0xsAaZiykiyDF3QWqfqkvT55aSggAxXH17irO/Ha5ikJiwXMvu+FKdeJGHxDpO4X+hCjxIAL6Y5T8B0WcqAMi8OtFXO8hEVMTjeNhi4LvavQV110pPw0bKr8xzbAyYOBTZS7uNFxoE8HvysJlDWiRLpzJVPHr8fcp8LeNQjgTX0F2EMfEUBzeRRm2iwdlKjTdbaE4ko3wI4bSeWhxV9IgKfx5HvlwYXntLRQ80+vvSURdhDQXGAFW2uoR1Y2Mx7cscQhLtFURHwEKRmGSJ74YJRTW3p80RH1w1j/fKUZ+sMk6/1ZBYIVedL7sHJbX5bTYk0SqUXUOZwIDAQAB";

    //阿里公钥
    private static String ALIPAY_PUBLIC_KEY = isSandBox ?
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAomIh+6Dl7kl8+zNQA843fXElFBdb9Ljxax7J+xQNML+c75aCaKBeSkih25qi/+uN13T29k9aBV9UFvbnakAzx3RhBooj1MCqLvDXS+GzwDh0q3WQ77FWtbMLmW+/2ZFMOGDSQNpxtdGfbUJwQ0yuwq++Gbek5kSFQzTX6RN+sM+1TcnJHYFzlOg+zZobqkF5ff+oYJP88RhrGLckfKgBs+VGvhmVgAkdV0BxiqZd2wdltXGjdMeJtKhHDfh+7MNWpZ3hjSxXB1SIHfiI6IVRvbAP45YCmW6vIIma8GnMrEcFDTWouNAg52RiszX4xnVsUWzUrMowpK3uV0RtmNRWBQIDAQAB" :
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnwKp/0iPIMlrXJKcioE3d4paIFWWb+IanoFapoOBWujooWzFh6C7pNO/EFCrsoekB2/rFWUXTRC+VaFvwXM9fBQ8aZPJEvotA3Zi+1XDHbG+wMf5XmTtYiqzGdUTIPgxT70w9XLWarwpFCByQiXpYAFbxu84xnYN6aYbxvNs5KXUmro4otLD8Moebi2EKwJGLGkkQsG2Gg3pmO/LEIiIwTsbjj2RuICmkZTPs3P4FD9yZgtw6PPbEo4HxUd2Xv4ecJYStBsfqWAgOC+zF17lYmuE1IATUPmiZ1nB3G4pYomsD6Qru/urTt+e1RkL7buOeR8nZY8NLNf/LRXoS22W/wIDAQAB";

    //"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqIfcL9GUW7H0xsAaZiykiyDF3QWqfqkvT55aSggAxXH17irO/Ha5ikJiwXMvu+FKdeJGHxDpO4X+hCjxIAL6Y5T8B0WcqAMi8OtFXO8hEVMTjeNhi4LvavQV110pPw0bKr8xzbAyYOBTZS7uNFxoE8HvysJlDWiRLpzJVPHr8fcp8LeNQjgTX0F2EMfEUBzeRRm2iwdlKjTdbaE4ko3wI4bSeWhxV9IgKfx5HvlwYXntLRQ80+vvSURdhDQXGAFW2uoR1Y2Mx7cscQhLtFURHwEKRmGSJ74YJRTW3p80RH1w1j/fKUZ+sMk6/1ZBYIVedL7sHJbX5bTYk0SqUXUOZwIDAQAB";


    //商户外网可以访问的异步地址
    private static String NOTIFY_URL = "http://project.tunnel.qydev.com/api/pay/callback";

    private AlipayClient alipayClient;

    public AlipayUtils(){
        this.alipayClient = new DefaultAlipayClient(SERVER_URL, APP_ID, APP_PRIVATE_KEY, "json", "UTF-8", ALIPAY_PUBLIC_KEY, "RSA2");
    }

    public AlipayUtils(AlipayConfig config){
        //实例化客户端
        this.SERVER_URL = config.getSERVER_URL();
        this.APP_ID = config.getAPP_ID();
        this.APP_PRIVATE_KEY = config.getAPP_PRIVATE_KEY();
        this.ALIPAY_PUBLIC_KEY = config.getALIPAY_PUBLIC_KEY();
        this.NOTIFY_URL = config.getNOTIFY_URL();
        this.alipayClient = new DefaultAlipayClient(SERVER_URL, APP_ID, APP_PRIVATE_KEY, "json", "UTF-8", ALIPAY_PUBLIC_KEY, "RSA2");
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
        request.setNotifyUrl(this.NOTIFY_URL);//回调通知url（必须和后台设置中一样）
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
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
    public static Map<String,Object> transferStatus(String status){
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
     * @param payOrder
     * @return
     */
    public Map queryAliPayOrder(AliPayOrder payOrder) {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//创建API对应的request类
        Map params = new HashMap();
        params.put("out_trade_no",String.valueOf(payOrder.getOrderNo()));
        params.put("trade_no",String.valueOf(payOrder.getTradeNo()));
        request.setBizContent(params.toString());//设置业务参数
        AlipayTradeQueryResponse response = null;//通过alipayClient调用API，获得对应的response类
        try {
            response = alipayClient.execute(request);
            String tradeStatus = response.getTradeStatus();
            Map statusMap = transferStatus(tradeStatus);
            //返回接口结果
            return statusMap;
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        logger.error("查询支付结果错误，"+ JSONObject.toJSONString(payOrder));
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
            response = alipayClient.execute(request);
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
            response = alipayClient.execute(request);
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
    public static String createOrderNo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyMMddHHmmssSS");
        String no = sdf.format(new Date());
        return no;
    }

    public static void main(String[] args){
        AlipayUtils alipayUtils = new AlipayUtils();
        AliPayOrder payOrder = new AliPayOrder();
        payOrder.setOrderNo(createOrderNo());
        payOrder.setAccount("jfdhfp2236@sandbox.com");
        payOrder.setAmount("0.1");
        payOrder.setRemark("remark");
        payOrder.setSubject("商品名称");
        payOrder.setAccountRealName("沙箱环境");
        alipayUtils.transferOrder(payOrder);
    }


}
