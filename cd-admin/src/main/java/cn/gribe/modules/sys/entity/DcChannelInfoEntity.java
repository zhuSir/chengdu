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
@TableName("dc_channel_info")
public class DcChannelInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private Integer channelId;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Long downloadCount;
	/**
	 * 
	 */
	private Long filterAddressCount;
	/**
	 * 
	 */
	private Long filterIpCount;
	/**
	 * 
	 */
	private Long filterRateCount;
	/**
	 * 
	 */
	private Long filterRateSum;
	/**
	 * 
	 */
	private Integer registerCount;
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
	private String agencyName;
	/**
	 * 
	 */
	private String appliationName;
	/**
	 * 
	 */
	private String channelCode;
	/**
	 * 
	 */
	private String channelName;
	/**
	 * 
	 */
	private String applicationName;

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
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	/**
	 * 获取：
	 */
	public Integer getChannelId() {
		return channelId;
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
	public void setDownloadCount(Long downloadCount) {
		this.downloadCount = downloadCount;
	}
	/**
	 * 获取：
	 */
	public Long getDownloadCount() {
		return downloadCount;
	}
	/**
	 * 设置：
	 */
	public void setFilterAddressCount(Long filterAddressCount) {
		this.filterAddressCount = filterAddressCount;
	}
	/**
	 * 获取：
	 */
	public Long getFilterAddressCount() {
		return filterAddressCount;
	}
	/**
	 * 设置：
	 */
	public void setFilterIpCount(Long filterIpCount) {
		this.filterIpCount = filterIpCount;
	}
	/**
	 * 获取：
	 */
	public Long getFilterIpCount() {
		return filterIpCount;
	}
	/**
	 * 设置：
	 */
	public void setFilterRateCount(Long filterRateCount) {
		this.filterRateCount = filterRateCount;
	}
	/**
	 * 获取：
	 */
	public Long getFilterRateCount() {
		return filterRateCount;
	}
	/**
	 * 设置：
	 */
	public void setFilterRateSum(Long filterRateSum) {
		this.filterRateSum = filterRateSum;
	}
	/**
	 * 获取：
	 */
	public Long getFilterRateSum() {
		return filterRateSum;
	}
	/**
	 * 设置：
	 */
	public void setRegisterCount(Integer registerCount) {
		this.registerCount = registerCount;
	}
	/**
	 * 获取：
	 */
	public Integer getRegisterCount() {
		return registerCount;
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
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	/**
	 * 获取：
	 */
	public String getAgencyName() {
		return agencyName;
	}
	/**
	 * 设置：
	 */
	public void setAppliationName(String appliationName) {
		this.appliationName = appliationName;
	}
	/**
	 * 获取：
	 */
	public String getAppliationName() {
		return appliationName;
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
	/**
	 * 设置：
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	/**
	 * 获取：
	 */
	public String getChannelName() {
		return channelName;
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
}
