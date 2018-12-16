package cn.gribe.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @date 2018-11-15 17:53:36
 */
@TableName("cd_shopping_address")
public class ShoppingAddressEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private int id;
	/**
	 * 用户id
	 */
	private int userId;
	/**
	 * 用户名
	 */
	@NotBlank(message="用户名不能为空")
	private String userName;
	/**
	 * 手机号
	 */
	@NotBlank(message="手机号不能为空")
	private String mobile;
	/**
	 * 省
	 */
	@NotBlank(message="省份不能为空")
	private String province;
	/**
	 * 城市
	 */
	@NotBlank(message="城市不能为空")
	private String city;
	/**
	 * 地区
	 */
	@NotBlank(message="地区不能为空")
	private String area;
	/**
	 * 详细地址
	 */
	@NotBlank(message="详细地址不能为空")
	private String address;
	/**
	 * 是否默认地址
	 */
	private int isDefault;

	private Date createTime;

	private Date updateTime;

	private int state;

	private int isDelete;

	/**
	 * 设置：
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public int getId() {
		return id;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * 设置：用户名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：用户名
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置：手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：手机号
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：省
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * 获取：省
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * 设置：城市
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * 获取：城市
	 */
	public String getCity() {
		return city;
	}
	/**
	 * 设置：地区
	 */
	public void setArea(String area) {
		this.area = area;
	}
	/**
	 * 获取：地区
	 */
	public String getArea() {
		return area;
	}
	/**
	 * 设置：详细地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：详细地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：是否默认地址
	 */
	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}
	/**
	 * 获取：是否默认地址
	 */
	public int getIsDefault() {
		return isDefault;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
}
