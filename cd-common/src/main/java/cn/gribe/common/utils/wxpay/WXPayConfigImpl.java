package cn.gribe.common.utils.wxpay;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.*;


public class WXPayConfigImpl implements WXPayConfig {

    private String appId = "wxac580e862dd447ef";
    private String mchId = "1521731881";//商户号
    private String key = "wgl61052819911220721618780176563";//密钥key

    private byte[] certData;
    private static WXPayConfigImpl INSTANCE;

    private WXPayConfigImpl() throws Exception {
        String path = "E:/workspace/chengdu/cd-api/src/main/resources/apiclient_cert.p12";
        String certPath = "src/resources/apiclient_cert.p12";//证书位置
        File file = new File(certPath);
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
}

