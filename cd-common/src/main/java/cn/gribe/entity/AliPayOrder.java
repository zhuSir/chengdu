package cn.gribe.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * 支付订单对象
 * Created by Zhugw on 2018/9/4 0004.
 */
public class AliPayOrder {

    //订单id
    private Integer id;

    //订单金额
    private String amount;

    //支付宝账号
    private String account;

    //支付真实名称
    private String AccountRealName;

    //订单号(对应支付宝返回值 out_trade_no)
    private String orderNo;

    //支付宝订单号
    private String tradeNo;

    //订单主题
    private String subject;

    //订单描述
    private String remark;

    //订单创建时间
    private String createTime;

    //来源描述
    private String sourceDesc;

    //状态
    private Integer status;

    //状态描述
    private String statusDescription;

    public AliPayOrder(){}

    public AliPayOrder(String amount,String remark,String subject,String orderNo){
        this.amount = amount;
        this.remark = remark;
        this.subject = subject;
        this.orderNo = orderNo;
    }

    public String getAccountRealName() {
        return AccountRealName;
    }

    public void setAccountRealName(String accountRealName) {
        AccountRealName = accountRealName;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSourceDesc() {
        return sourceDesc;
    }

    public void setSourceDesc(String sourceDesc) {
        this.sourceDesc = sourceDesc;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    @Override
    public String toString() {
        return "AliPayOrder{" +
                "id=" + id +
                ", amount='" + amount + '\'' +
                ", account='" + account + '\'' +
                ", AccountRealName='" + AccountRealName + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", subject='" + subject + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime='" + createTime + '\'' +
                ", sourceDesc='" + sourceDesc + '\'' +
                ", status=" + status +
                ", statusDescription='" + statusDescription + '\'' +
                '}';
    }
}
