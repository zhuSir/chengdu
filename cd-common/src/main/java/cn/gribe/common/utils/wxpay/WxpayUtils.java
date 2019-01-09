package cn.gribe.common.utils.wxpay;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 微信支付
 * Created by Zhugw on 2019/1/3 0003.
 */
public class WxpayUtils {

    public static final Logger logger = LoggerFactory.getLogger(WxpayUtils.class);

    private WXPay wxpay;
    private WXPayConfigImpl config;

    public WxpayUtils() throws Exception {
        config = WXPayConfigImpl.getInstance();
        wxpay = new WXPay(config);
    }

    /**
     * 下單接口
     * @param subject
     * @param tradeNo
     * @param amt
     * @param ip
     * @param notifyUrl
     */
    public String unifiedOrder(String subject, String tradeNo, String amt, String ip, String notifyUrl) {
        try {
            Map<String, String> data = new HashMap<String, String>();
            data.put("body", subject);
            data.put("out_trade_no", tradeNo);
            data.put("device_info", "");
            data.put("fee_type", "CNY");
            data.put("total_fee", amt);
            data.put("spbill_create_ip", ip);
            data.put("notify_url", notifyUrl);
            data.put("trade_type", "APP");
            Map<String, String> resp = wxpay.unifiedOrder(data);
            logger.info("===>>>:微信下单成功", resp);
            String resultsCode = resp.get("result_code");
            /**
             * 步骤3：统一下单接口返回正常的prepay_id，再按签名规范重新生成签名后，将数据传输给APP。
             * 参与签名的字段名为appid，partnerid，prepayid，noncestr，timestamp，package。注意：package的值格式为Sign=WXPay
             */
            if ("SUCCESS".equals(resultsCode)) {
                Map<String, String> reqData = new HashMap<>();
                reqData.put("appid", config.getAppID());
                reqData.put("partnerid", resp.get("mch_id"));
                reqData.put("prepayid", resp.get("prepay_id"));
                reqData.put("noncestr", resp.get("nonce_str"));
                reqData.put("timestamp", String.valueOf(System.currentTimeMillis()));
                reqData.put("package", "Sign=WXPay");
                String signString = WXPayUtil.generateSignature(reqData,config.getKey());
                logger.info("===>>>:微信支付统一下单后签名:"+signString);
                return signString;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("===>>>:微信下单错误", e.getMessage());
        }
        return null;
    }

    public boolean queryOrder(String tradeNo) {
        try {
            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", tradeNo);
            Map<String, String> resp = wxpay.orderQuery(data);
            System.out.println(resp);
            logger.info("===>>>:微信订单查询",resp);
            String tradeState= resp.get("trade_state");
            if("SUCCESS".equals(tradeState)){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("===>>>:微信支付查询错误", e.getMessage());
        }
        return false;
    }

    public boolean doRefund(String out_trade_no,String amt) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", out_trade_no);
        data.put("out_refund_no", out_trade_no);
        data.put("total_fee", amt);
        data.put("refund_fee", amt);
        data.put("refund_fee_type", "CNY");
        data.put("op_user_id", config.getMchID());
        try {
            Map<String, String> r = wxpay.refund(data);
            System.out.println(r);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("===>>>:微信退款失败",e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
//        String tradeNo = "201901091519012";
//        WxpayUtils.unifiedOrder("測試支付", tradeNo, "200", "120.36.144.200", "www.baidu.com");
//        WxpayUtils.queryOrder(tradeNo);
    }

}
