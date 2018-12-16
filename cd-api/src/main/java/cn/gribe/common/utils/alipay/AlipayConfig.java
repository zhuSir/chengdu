package cn.gribe.common.utils.alipay;

/**
 * Created by Zhugw on 2018/9/5 0005.
 */
public class AlipayConfig {

    //app id
    private String APP_ID;//"2018090461287363";

    //阿里请求地址
    private String SERVER_URL;//"https://openapi.alipay.com/gateway.do";

    //APP私钥
    private String APP_PRIVATE_KEY;//"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCoh9wv0ZRbsfTGwBpmLKSLIMXdBap+qS9PnlpKCADFcfXuKs78drmKQmLBcy+74Up14kYfEOk7hf6EKPEgAvpjlPwHRZyoAyLw60Vc7yERUxON42GLgu9q9BXXXSk/DRsqvzHNsDJg4FNlLu40XGgTwe/KwmUNaJEunMlU8evx9ynwt41COBNfQXYQx8RQHN5FGbaLB2UqNN1toTiSjfAjhtJ5aHFX0iAp/Hke+XBhee0tFDzT6+9JRF2ENBcYAVba6hHVjYzHtyxxCEu0VREfAQpGYZInvhglFNbenzREfXDWP98pRn6wyTr/VkFghV50vuwcltfltNiTRKpRdQ5nAgMBAAECggEAJOOADbN5Hs0IAA0CekThS1bEGInp9MmBgpLtvnCcApvcMeKK5fXyzNGVHjThSOWaT81Ufe5Mruu4dyMjDFe6iNuOguT40knFdyh4SUxKV6lwiUkxC8GAOE/vC/HnWla7EJNDCnu7uzq+5lHsb+eeHF0B6Ma+Kb4usR4+b2p4s8CCRnYoNVEWvIrvcS7T1EyDciu3RTfZUUjYMFDNhO1bQFCsb+re9VQv/H18rfC9tr7M1IWQVgqsfRGUgUpe4OkKUInI6PgbxUjxEtvktd0mKUYfjjtM43sJVB+XiWdzrsVojefCj0dSRN43D8GMRWYK1alPswPVHLNGgYaI8pRyWQKBgQD+0Kf+Eq1AzW49m1yOc1TRNhg0oZ5sBtJdGaXRZgwNlCZFvDcxe8UuvezABZ4WvDWI2v0MXsB/ya8q3npM8KK2xYSu60mJXisSNcuO+XxvAbLutcwwtWlOAlWh/x67nA/juGSMDBtBC8n9MUisEXGp1j7LHPxDC+QTGUs6Mt5yAwKBgQCpUHyoTeknvK3VZMymJZY2PB4Wuu/lk6IBrC1EfHWak035/8m1o6MOtoq7aVEmAjMbUzU1bQggdprl9zZno6Hh9eDavLRk04WHXOUxVu3nuVO7cLIFb9WImkM4EUrOmZipRZykNIgHqDyB/qeOuYW8p7TW4OGo2LzjKGZcc4KWzQKBgFEVKD+qxcKXG/sykrMpc9XWtsEYpIaPbST+4krNhAxWFg+eopl2XvpOqSU5JGLrpMVVmav0AqEnFjw36wbMk5pBI8VdTVsOwfnbcFigfr379moLI1xkfYu7JnCT872THWNC64u81ndOPwn4Qr2uF1MJv5V2NbdH8104cT2lri7tAoGAddiWm1J3GPhjiea55l68+YzIaIu+k+040q8zZrq15Fvo5zvY+nsl8ASv9RetqqXXSoqUu1XjVS0YTT3O+T3UXAEWe0jiylbLVtcak0ftGN9OX2VmExCu8IBmjuzCQ6B2uELCbyw9Dg9T5isIFvgcFJ4p6XHmvu30MquY63peh3UCgYEA3xbhSiYvTHdrTCqaonQIUtIX5xGaJygCCgFtNEEDUIqFAadedaEoXIGHwAqtQTJzdwu7CMoqq+iZIGTboSpO4TWGQETDIMZbJsuBsiQ4v+lTNzM+3SPC0+VmSf7bmHjl6MHFWVMT6RZ5tWG+EWPVF78qMFMyab+k1V5r4lwg2R8=";

    //APP公钥
    private String APP_PUBLIC_KEY;//"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqIfcL9GUW7H0xsAaZiykiyDF3QWqfqkvT55aSggAxXH17irO/Ha5ikJiwXMvu+FKdeJGHxDpO4X+hCjxIAL6Y5T8B0WcqAMi8OtFXO8hEVMTjeNhi4LvavQV110pPw0bKr8xzbAyYOBTZS7uNFxoE8HvysJlDWiRLpzJVPHr8fcp8LeNQjgTX0F2EMfEUBzeRRm2iwdlKjTdbaE4ko3wI4bSeWhxV9IgKfx5HvlwYXntLRQ80+vvSURdhDQXGAFW2uoR1Y2Mx7cscQhLtFURHwEKRmGSJ74YJRTW3p80RH1w1j/fKUZ+sMk6/1ZBYIVedL7sHJbX5bTYk0SqUXUOZwIDAQAB";

    //阿里公钥
    private String ALIPAY_PUBLIC_KEY;//"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnwKp/0iPIMlrXJKcioE3d4paIFWWb+IanoFapoOBWujooWzFh6C7pNO/EFCrsoekB2/rFWUXTRC+VaFvwXM9fBQ8aZPJEvotA3Zi+1XDHbG+wMf5XmTtYiqzGdUTIPgxT70w9XLWarwpFCByQiXpYAFbxu84xnYN6aYbxvNs5KXUmro4otLD8Moebi2EKwJGLGkkQsG2Gg3pmO/LEIiIwTsbjj2RuICmkZTPs3P4FD9yZgtw6PPbEo4HxUd2Xv4ecJYStBsfqWAgOC+zF17lYmuE1IATUPmiZ1nB3G4pYomsD6Qru/urTt+e1RkL7buOeR8nZY8NLNf/LRXoS22W/wIDAQAB";

    //商户外网可以访问的异步地址
    private String NOTIFY_URL = "/pay/callback";

    public String getAPP_ID() {
        return APP_ID;
    }

    public void setAPP_ID(String APP_ID) {
        this.APP_ID = APP_ID;
    }

    public String getSERVER_URL() {
        return SERVER_URL;
    }

    public void setSERVER_URL(String SERVER_URL) {
        this.SERVER_URL = SERVER_URL;
    }

    public String getAPP_PRIVATE_KEY() {
        return APP_PRIVATE_KEY;
    }

    public void setAPP_PRIVATE_KEY(String APP_PRIVATE_KEY) {
        this.APP_PRIVATE_KEY = APP_PRIVATE_KEY;
    }

    public String getAPP_PUBLIC_KEY() {
        return APP_PUBLIC_KEY;
    }

    public void setAPP_PUBLIC_KEY(String APP_PUBLIC_KEY) {
        this.APP_PUBLIC_KEY = APP_PUBLIC_KEY;
    }

    public String getALIPAY_PUBLIC_KEY() {
        return ALIPAY_PUBLIC_KEY;
    }

    public void setALIPAY_PUBLIC_KEY(String ALIPAY_PUBLIC_KEY) {
        this.ALIPAY_PUBLIC_KEY = ALIPAY_PUBLIC_KEY;
    }

    public String getNOTIFY_URL() {
        return NOTIFY_URL;
    }

    public void setNOTIFY_URL(String NOTIFY_URL) {
        this.NOTIFY_URL = NOTIFY_URL;
    }

    @Override
    public String toString() {
        return "AlipayConfig{" +
                "APP_ID='" + APP_ID + '\'' +
                ", SERVER_URL='" + SERVER_URL + '\'' +
                ", APP_PRIVATE_KEY='" + APP_PRIVATE_KEY + '\'' +
                ", APP_PUBLIC_KEY='" + APP_PUBLIC_KEY + '\'' +
                ", ALIPAY_PUBLIC_KEY='" + ALIPAY_PUBLIC_KEY + '\'' +
                ", NOTIFY_URL='" + NOTIFY_URL + '\'' +
                '}';
    }
}
