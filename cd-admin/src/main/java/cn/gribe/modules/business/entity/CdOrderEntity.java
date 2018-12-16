package cn.gribe.modules.business.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-24 15:34:15
 */
@TableName("cd_order")
public class CdOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 订单编号
	 */
	private String code;
	/**
	 * 收货人手机号
	 */
	private String phone;
	/**
	 * 收货人姓名
	 */
	private String userName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 订单状态
	 */
	private String state;
	/**
	 * 收货地址
	 */
	private String address;
	/**
	 * 商品id
	 */
	private Integer productId;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 数量
	 */
	private Integer count;
	/**
	 * 总价
	 */
	private Double sum;
	/**
	 * 运费
	 */
	private String freight;
	/**
	 * 支付类型
	 */
	private String payType;
	/**
	 * 商家id
	 */
	private Integer storeId;

	//商家名称
	@TableField(exist = false)
	private String storeName;

	//产品名称
	@TableField(exist = false)
	private String productName;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：订单编号
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：订单编号
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：收货人手机号
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：收货人手机号
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：收货人姓名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：收货人姓名
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：订单状态
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * 获取：订单状态
	 */
	public String getState() {
		return state;
	}
	/**
	 * 设置：收货地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：收货地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：商品id
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	/**
	 * 获取：商品id
	 */
	public Integer getProductId() {
		return productId;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：数量
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
	/**
	 * 获取：数量
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * 设置：总价
	 */
	public void setSum(Double sum) {
		this.sum = sum;
	}
	/**
	 * 获取：总价
	 */
	public Double getSum() {
		return sum;
	}
	/**
	 * 设置：运费
	 */
	public void setFreight(String freight) {
		this.freight = freight;
	}
	/**
	 * 获取：运费
	 */
	public String getFreight() {
		return freight;
	}
	/**
	 * 设置：支付类型
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}
	/**
	 * 获取：支付类型
	 */
	public String getPayType() {
		return payType;
	}
	/**
	 * 设置：商家id
	 */
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	/**
	 * 获取：商家id
	 */
	public Integer getStoreId() {
		return storeId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}
