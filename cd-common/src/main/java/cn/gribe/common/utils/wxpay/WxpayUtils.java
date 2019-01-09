package cn.gribe.common.utils.wxpay;

import java.security.MessageDigest;
import java.util.*;

/**
 * Created by Zhugw on 2019/1/3 0003.
 */
public class WxpayUtils {

    private final String appid = "";

    private final String mch_id = "";


    public String unifedorder(){

        String params =
                "<xml>\n" +
                "   <appid>wx2421b1c4370ec43b</appid>\n" +
                "   <attach>支付测试</attach>\n" +
                "   <body>APP支付测试</body>\n" +
                "   <mch_id>10000100</mch_id>\n" +
                "   <nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>\n" +
                "   <notify_url>http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php</notify_url>\n" +
                "   <out_trade_no>1415659990</out_trade_no>\n" +
                "   <spbill_create_ip>14.23.150.211</spbill_create_ip>\n" +
                "   <total_fee>1</total_fee>\n" +
                "   <trade_type>APP</trade_type>\n" +
                "   <sign>0CB01533B8C1EF103065174F50BCA001</sign>\n" +
                "</xml>";

        return null;
    }

    /**
     * 调起微信APP支付，签名
     * 生成签名
     */
    private String genPackageSign(LinkedHashMap<String, String> params, String key) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(key);

        String packageSign = getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return packageSign;
    }

    /**
     * md5加密
     *
     * @param buffer
     * @return
     */
    private String getMessageDigest(byte[] buffer) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取随机数
     *
     * @return
     */
    private String genNonceStr() {
        Random random = new Random();
        return getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }


    /**
     * 获取时间戳
     *
     * @return
     */
    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

}
