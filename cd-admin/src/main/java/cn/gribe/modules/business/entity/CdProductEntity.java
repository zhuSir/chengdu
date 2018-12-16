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
@TableName("cd_product")
public class CdProductEntity implements Serializable {
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
	 * 商铺类别
	 */
	private String type;
	/**
	 * 简介
	 */
	private String about;
	/**
	 * 简介图片
	 */
	private String imgs;
	/**
	 * 运费
	 */
	private String freight;
	/**
	 * 缩略图
	 */
	private String shortImg;
	/**
	 * 属性tag
	 */
	private String tag;
	/**
	 *  今日库存
	 */
	private String todayInventory;
	/**
	 * 总库存
	 */
	private String sumInventory;
	/**
	 * 价格
	 */
	private BigDecimal price;
	/**
	 * 商铺id
	 */
	private Integer storeId;
	/**
	 * 状态
	 */
	private String state;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

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
	 * 设置：商铺类别
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：商铺类别
	 */
	public String getType() {
		return type;
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
	 * 设置：简介图片
	 */
	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	/**
	 * 获取：简介图片
	 */
	public String getImgs() {
		return imgs;
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
	 * 设置：缩略图
	 */
	public void setShortImg(String shortImg) {
		this.shortImg = shortImg;
	}
	/**
	 * 获取：缩略图
	 */
	public String getShortImg() {
		return shortImg;
	}
	/**
	 * 设置：属性tag
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}
	/**
	 * 获取：属性tag
	 */
	public String getTag() {
		return tag;
	}
	/**
	 * 设置： 今日库存
	 */
	public void setTodayInventory(String todayInventory) {
		this.todayInventory = todayInventory;
	}
	/**
	 * 获取： 今日库存
	 */
	public String getTodayInventory() {
		return todayInventory;
	}
	/**
	 * 设置：总库存
	 */
	public void setSumInventory(String sumInventory) {
		this.sumInventory = sumInventory;
	}
	/**
	 * 获取：总库存
	 */
	public String getSumInventory() {
		return sumInventory;
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
	/**
	 * 设置：商铺id
	 */
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	/**
	 * 获取：商铺id
	 */
	public Integer getStoreId() {
		return storeId;
	}
	/**
	 * 设置：状态
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * 获取：状态
	 */
	public String getState() {
		return state;
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
}
