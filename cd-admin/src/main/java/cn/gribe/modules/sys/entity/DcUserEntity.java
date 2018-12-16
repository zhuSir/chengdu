package cn.gribe.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-07 17:38:54
 */
@TableName("dc_user")
public class DcUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private String idCard;
	/**
	 * 
	 */
	private String mobile;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String password;
	/**
	 * 
	 */
	private Integer sesameNum;
	/**
	 * 
	 */
	private Integer type;
	/**
	 * 
	 */
	private Date updateTime;
	/**
	 * 
	 */
	private Integer agencyId;
	/**
	 * 
	 */
	private Integer applicationId;
	/**
	 * 
	 */
	private String applicationName;
	/**
	 * 
	 */
	private String lastLoginAddress;
	/**
	 * 
	 */
	private String lastLoginIp;
	/**
	 * 
	 */
	private String registerAddress;
	/**
	 * 
	 */
	private String registerIp;
	/**
	 * 
	 */
	private String dtype;
	/**
	 * 
	 */
	private String channelCode;

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
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	/**
	 * 获取：
	 */
	public String getIdCard() {
		return idCard;
	}
	/**
	 * 设置：
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：
	 */
	public void setSesameNum(Integer sesameNum) {
		this.sesameNum = sesameNum;
	}
	/**
	 * 获取：
	 */
	public Integer getSesameNum() {
		return sesameNum;
	}
	/**
	 * 设置：
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：
	 */
	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}
	/**
	 * 获取：
	 */
	public Integer getAgencyId() {
		return agencyId;
	}
	/**
	 * 设置：
	 */
	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}
	/**
	 * 获取：
	 */
	public Integer getApplicationId() {
		return applicationId;
	}
	/**
	 * 设置：
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	/**
	 * 获取：
	 */
	public String getApplicationName() {
		return applicationName;
	}
	/**
	 * 设置：
	 */
	public void setLastLoginAddress(String lastLoginAddress) {
		this.lastLoginAddress = lastLoginAddress;
	}
	/**
	 * 获取：
	 */
	public String getLastLoginAddress() {
		return lastLoginAddress;
	}
	/**
	 * 设置：
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	/**
	 * 获取：
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	/**
	 * 设置：
	 */
	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}
	/**
	 * 获取：
	 */
	public String getRegisterAddress() {
		return registerAddress;
	}
	/**
	 * 设置：
	 */
	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}
	/**
	 * 获取：
	 */
	public String getRegisterIp() {
		return registerIp;
	}
	/**
	 * 设置：
	 */
	public void setDtype(String dtype) {
		this.dtype = dtype;
	}
	/**
	 * 获取：
	 */
	public String getDtype() {
		return dtype;
	}
	/**
	 * 设置：
	 */
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	/**
	 * 获取：
	 */
	public String getChannelCode() {
		return channelCode;
	}
}
