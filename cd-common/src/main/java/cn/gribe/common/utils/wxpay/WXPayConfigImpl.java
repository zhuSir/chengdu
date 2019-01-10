package cn.gribe.common.utils.wxpay;

import com.github.wxpay.sdk.WXPayConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;

@Component
public class WXPayConfigImpl implements WXPayConfig {

    @Value("${wxpay.appId}")
    private String appId;// = "wxac580e862dd447ef";

    @Value("${wxpay.mchId}")
    private String mchId;// = "1521731881";//商户号

    @Value("${wxpay.key}")
    private String key;// = "wgl61052819911220721618780176563";//密钥key

    @Value("${wxpay.ip}")
    private String ip;

    @Value("${wxpay.notifyUrl}")
    private String notifyUrl;

    private byte[] certData;

    private static WXPayConfigImpl INSTANCE;

    private WXPayConfigImpl() throws Exception {
        String path = "E:/workspace/chengdu/cd-api/src/main/resources/apiclient_cert.p12";
        String certPath = "classpath:apiclient_cert.p12";//证书位置
        File file = ResourceUtils.getFile(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    public static WXPayConfigImpl getInstance() throws Exception {
        if (INSTANCE == null) {
            synchronized (WXPayConfigImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WXPayConfigImpl();
                }
            }
        }
        return INSTANCE;
    }

    public String getAppID() {
        return appId;
    }

    public String getMchID() {
        return mchId;
    }

    public String getKey() {
        return key;
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }


    public int getHttpConnectTimeoutMs() {
        return 2000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}

