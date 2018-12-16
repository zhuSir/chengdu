package cn.gribe.modules.business.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-24 15:34:15
 */
@TableName("cd_store")
public class CdStoreEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 表id
	 */
	@TableId
	private Integer id;
	/**
	 * 店铺名称
	 */
	private String name;
	/**
	 * 店铺地址
	 */
	private String address;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 简介
	 */
	private String about;
	/**
	 * 店铺类型
	 */
	private String type;
	/**
	 * 轮播图
	 */
	private String imgs;
	/**
	 * 店铺联系电话
	 */
	private String phone;
	/**
	 * 备用电话
	 */
	private String backupPhone;
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
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 评分
	 */
	private Integer score;
	/**
	 * 销量
	 */
	private Integer sales;
	/**
	 * 价格
	 */
	private BigDecimal price;

	/**
	 * 设置：表id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：表id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：店铺名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：店铺名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：店铺地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：店铺地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：简介
	 */
	public void setAbout(String about) {
		this.about = about;
	}
	/**
	 * 获取：简介
	 */
	public String getAbout() {
		return about;
	}
	/**
	 * 设置：店铺类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：店铺类型
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：轮播图
	 */
	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	/**
	 * 获取：轮播图
	 */
	public String getImgs() {
		return imgs;
	}
	/**
	 * 设置：店铺联系电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：店铺联系电话
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：备用电话
	 */
	public void setBackupPhone(String backupPhone) {
		this.backupPhone = backupPhone;
	}
	/**
	 * 获取：备用电话
	 */
	public String getBackupPhone() {
		return backupPhone;
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
	 * 设置：评分
	 */
	public void setScore(Integer score) {
		this.score = score;
	}
	/**
	 * 获取：评分
	 */
	public Integer getScore() {
		return score;
	}
	/**
	 * 设置：销量
	 */
	public void setSales(Integer sales) {
		this.sales = sales;
	}
	/**
	 * 获取：销量
	 */
	public Integer getSales() {
		return sales;
	}
	/**
	 * 设置：价格
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取：价格
	 */
	public BigDecimal getPrice() {
		return price;
	}
}
