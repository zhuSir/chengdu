package cn.gribe.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 订单
 * Created by Zhugw on 2018/11/4 0004.
 */
@TableName("cd_order")
public class OrderEntity {

    //支付成功状态
    public static Integer PAY_STATUS_SUCCESS = 1;

    //待支付
    public static Integer STATE_AWAIT_PAY = 1;

    //待使用
    public static Integer STATE_AWAIT_USE = 2;

    //待评论
    public static Integer STATE_AWAIT_EVALUATE = 3;

    //退单
    public static Integer STATE_CHARGE_BACK = 4;

    //支付宝支付
    public static Integer PAY_TYPE_ALIPAY = 1;

    //微信支付
    public static Integer PAY_TYPE_WECHATPAY= 2;

    @TableId
    private Integer id;

    //订单号
    private String code;

    //收货人手机号
    @NotBlank(message="收货人手机号不能为空")
    private String phone;

    //收货人姓名
    @NotBlank(message="收货人姓名不能为空")
    private String userName;

    //创建时间
    private Date createTime;

    //订单状态( 1：待付款；2：待用/待收；3：待评价；4：退单；)
    private Integer state;

    //收货地址
    @NotBlank(message="收货人姓名不能为空")
    private String address;

    //商品id
    @NotNull(message="商品不能为空")
    private Integer productId;

    //更新时间
    private Date updateTime;

    //数量
    @NotNull(message="数量不能为空")
    private Integer count;

    //总价
    @NotNull(message="总价不能为空")
    private double sum;

    //运费
    @NotBlank(message="运费不能为空")
    private String freight;

    //支付类型 --new
    @NotNull(message="支付类型不能为空")
    private Integer payType;

    //商家id --new
    @NotNull(message="商家不能为空")
    private Integer storeId;

    //用户id
    private Integer userId;

    //支付状态
    private Integer payStatus;

    //支付描述
    private String payDescription;

    //商店名称
    @TableField(exist = false)
    private String storeName;

    //产品缩略图
    @TableField(exist = false)
    private String productShortImg;

    //产品名称
    @TableField(exist = false)
    private String productName;

    //产品简介
    @TableField(exist = false)
    private String productAbout;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getProductShortImg() {
        return productShortImg;
    }

    public void setProductShortImg(String productShortImg) {
        this.productShortImg = productShortImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductAbout() {
        return productAbout;
    }

    public void setProductAbout(String productAbout) {
        this.productAbout = productAbout;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayDescription() {
        return payDescription;
    }

    public void setPayDescription(String payDescription) {
        this.payDescription = payDescription;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", phone='" + phone + '\'' +
                ", userName='" + userName + '\'' +
                ", createTime=" + createTime +
                ", state=" + state +
                ", address='" + address + '\'' +
                ", productId=" + productId +
                ", updateTime=" + updateTime +
                ", count=" + count +
                ", sum=" + sum +
                ", freight='" + freight + '\'' +
                ", payType=" + payType +
                ", storeId=" + storeId +
                ", userId=" + userId +
                ", payStatus=" + payStatus +
                ", payDescription='" + payDescription + '\'' +
                ", storeName='" + storeName + '\'' +
                ", productShortImg='" + productShortImg + '\'' +
                ", productName='" + productName + '\'' +
                ", productAbout='" + productAbout + '\'' +
                '}';
    }
}
