package cn.gribe.modules.business.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-24 15:34:14
 */
@TableName("cd_user")
public class CdUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 状态（0：正常；1：禁用）
	 */
	private String state;
	/**
	 * 年龄
	 */
	private Integer age;
	/**
	 * 维度
	 */
	private String lat;
	/**
	 * 经度
	 */
	private String lon;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 最后一次登录ip
	 */
	private String lastLoginIp;
	/**
	 * 最后一次登录时间
	 */
	private Date lastLoginTime;
	/**
	 * 用户头像
	 */
	private String headImg;
	/**
	 * 密码(密文）
	 */
	private String password;
	/**
	 * 真实密码（未加密）
	 */
	private String realPassword;

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
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：手机号
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：状态（0：正常；1：禁用）
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * 获取：状态（0：正常；1：禁用）
	 */
	public String getState() {
		return state;
	}
	/**
	 * 设置：年龄
	 */
	public void setAge(Integer age) {
		this.age = age;
	}
	/**
	 * 获取：年龄
	 */
	public Integer getAge() {
		return age;
	}
	/**
	 * 设置：维度
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}
	/**
	 * 获取：维度
	 */
	public String getLat() {
		return lat;
	}
	/**
	 * 设置：经度
	 */
	public void setLon(String lon) {
		this.lon = lon;
	}
	/**
	 * 获取：经度
	 */
	public String getLon() {
		return lon;
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
	 * 设置：最后一次登录ip
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	/**
	 * 获取：最后一次登录ip
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	/**
	 * 设置：最后一次登录时间
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	/**
	 * 获取：最后一次登录时间
	 */
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	/**
	 * 设置：用户头像
	 */
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	/**
	 * 获取：用户头像
	 */
	public String getHeadImg() {
		return headImg;
	}
	/**
	 * 设置：密码(密文）
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：密码(密文）
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：真实密码（未加密）
	 */
	public void setRealPassword(String realPassword) {
		this.realPassword = realPassword;
	}
	/**
	 * 获取：真实密码（未加密）
	 */
	public String getRealPassword() {
		return realPassword;
	}
}
