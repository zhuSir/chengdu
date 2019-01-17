package cn.gribe.common.utils.wxpay;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 微信支付
 * Created by Zhugw on 2019/1/3 0003.
 */
@Component
public class WxpayUtils {

    public static final Logger logger = LoggerFactory.getLogger(WxpayUtils.class);

    public WXPay wxpay;

    @Autowired
    private WXPayConfigImpl config;

    public void init(){
        if (wxpay == null) {
            synchronized (WXPayConfigImpl.class) {
                if (wxpay == null) {
                    wxpay = new WXPay(config);
                }
            }
        }
    }

    /**
     * 下單接口
     * @param subject
     * @param tradeNo
     * @param amt 金额 分 单位
     */
    public Map unifiedOrder(String subject, String tradeNo, String amt) {
        try {
            init();
            Map<String, String> data = new HashMap<String, String>();
            data.put("body", subject);
            data.put("out_trade_no", tradeNo);
            data.put("device_info", "");
            data.put("fee_type", "CNY");
            data.put("total_fee", amt);
            data.put("spbill_create_ip", config.getIp());
            data.put("notify_url", config.getNotifyUrl());
            data.put("trade_type", "APP");
            logger.info("===>>>:微信下單參數:"+JSONObject.toJSONString(data));
            Map<String, String> resp = wxpay.unifiedOrder(data);
            logger.info("===>>>:微信下单成功 "+JSONObject.toJSONString(resp));
            String returnCode = resp.get("return_code");
            if("FAIL".equals(returnCode)){
                return null;
            }
            String resultsCode = resp.get("result_code");
            /**
             * 步骤3：统一下单接口返回正常的prepay_id，再按签名规范重新生成签名后，将数据传输给APP。
             * 参与签名的字段名为appid，partnerid，prepayid，noncestr，timestamp，package。注意：package的值格式为Sign=WXPay
             */
            if ("SUCCESS".equals(resultsCode)) {
//                Map<String, String> reqData = new HashMap<>();
//                reqData.put("appid", config.getAppID());
//                reqData.put("partnerid", config.getMchID());//resp.get("mch_id")
//                reqData.put("prepayid", resp.get("prepay_id"));
//                reqData.put("noncestr", WXPayUtil.generateNonceStr());//WXPayUtil.generateNonceStr()
//                reqData.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));//
//                reqData.put("package", "Sign=WXPay");
//                String signString = WXPayUtil.generateSignature(reqData,config.getKey());
//                logger.info("簽名參數:"+JSONObject.toJSONString(reqData));
//                logger.info("===>>>:微信支付统一下单后签名:"+signString);
//                reqData.put("sign",signString);
//                logger.info("===>>>:微信支付返回前端:"+JSONObject.toJSONString(reqData));
//                return reqData;
                //签名并返回
                return orderSign(resp.get("prepay_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("===>>>:微信下单错误", e.getMessage());
        }
        return null;
    }

    /**
     * 重新签名
     * @param prepayId
     * @return
     * @throws Exception
     */
    public Map orderSign(String prepayId) throws Exception {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("appid", config.getAppID());
        reqData.put("partnerid", config.getMchID());//resp.get("mch_id")
        reqData.put("prepayid", prepayId);
        reqData.put("noncestr", WXPayUtil.generateNonceStr());//WXPayUtil.generateNonceStr()
        reqData.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));//
        reqData.put("package", "Sign=WXPay");
        String signString = WXPayUtil.generateSignature(reqData,config.getKey());
        logger.info("簽名參數:"+JSONObject.toJSONString(reqData));
        logger.info("===>>>:微信支付统一下单后签名:"+signString);
        reqData.put("sign",signString);
        logger.info("===>>>:微信支付返回前端:"+JSONObject.toJSONString(reqData));
        return reqData;
    }

    /**
     * 查询支付
     * @param tradeNo
     * @return
     */

    public Map queryOrder(String tradeNo) {
        try {
            init();
            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", tradeNo);
            Map<String, String> resp = wxpay.orderQuery(data);
            System.out.println(resp);
            logger.info("===>>>:微信订单查询",resp);
            String tradeState= resp.get("trade_state");
            return transferStatus(tradeState);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("===>>>:微信支付查询错误", e.getMessage());
        }
        return null;
    }

    /**
     * 退款
     * @param out_trade_no
     * @param amt
     * @return
     */
    public boolean doRefund(String out_trade_no,String amt) {
        init();
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", out_trade_no);
        data.put("out_refund_no", out_trade_no);
        data.put("total_fee", amt);
        data.put("refund_fee", amt);
        data.put("refund_fee_type", "CNY");
        data.put("op_user_id", config.getMchID());
        try {
            Map<String, String> r = wxpay.refund(data);
            String resultCode = r.get("result_code");
            if("SUCCESS".equals(resultCode)){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("===>>>:微信退款失败",e.getMessage());
        }
        return false;
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
            if(status.equals("SUCCESS")){
                results.put("status",1);
                results.put("description","支付成功");
            }else if("REFUND".equals(status)){
                results.put("status",-2);
                results.put("description","转入退款");
            }else if("NOTPAY".equals(status)){
                results.put("status",-1);
                results.put("description","未支付");
            }else if("CLOSED".equals(status)){
                results.put("status",1);
                results.put("description","已关闭");
            }else if("REVOKED".equals(status)){
                results.put("status",-3);
                results.put("description","已撤销（刷卡支付）");
            }else if("USERPAYING".equals(status)){
                results.put("status",-4);
                results.put("description","用户支付中");
            }else if("PAYERROR".equals(status)){
                results.put("status",0);
                results.put("description","支付失败(其他原因，如银行返回失败)");
            }else {
                results.put("status",0);
                results.put("description","交易支付失败");
            }
        }else{
            results.put("status",0);
            results.put("description","交易错误");
        }
        return results;
    }

}
