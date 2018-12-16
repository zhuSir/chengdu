package cn.gribe.common.utils.alipay;

/**
 * Created by Zhugw on 2018/9/5 0005.
 */
public class AlipayResponse {


    /**
     * code : 40004
     * msg : Business Failed
     * sub_code : PAYEE_NOT_EXIST
     * sub_msg : 收款账号不存在
     * out_biz_no : 2018090515045304
     */

    private String code;
    private String msg;
    private String sub_code;
    private String sub_msg;
    private String out_biz_no;

    private Boolean isSuccess;

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSub_code() {
        return sub_code;
    }

    public void setSub_code(String sub_code) {
        this.sub_code = sub_code;
    }

    public String getSub_msg() {
        return sub_msg;
    }

    public void setSub_msg(String sub_msg) {
        this.sub_msg = sub_msg;
    }

    public String getOut_biz_no() {
        return out_biz_no;
    }

    public void setOut_biz_no(String out_biz_no) {
        this.out_biz_no = out_biz_no;
    }
}
