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
 * @date 2018-11-24 15:34:23
 */
@TableName("cd_activity")
public class CdActivityEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 活动类型
	 */
	private String type;
	/**
	 * 显示位置
	 */
	private String locationType;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 状态
	 */
	private String state;
	/**
	 * 商铺id
	 */
	private Integer storeId;
	/**
	 * 链接
	 */
	private String link;
	/**
	 * 图片简介
	 */
	private String imgs;

	//店铺名称
	@TableField(exist = false)
	private String storeName;

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
	 * 设置：活动类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：活动类型
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：显示位置
	 */
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	/**
	 * 获取：显示位置
	 */
	public String getLocationType() {
		return locationType;
	}
	/**
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
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
	 * 设置：链接
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * 获取：链接
	 */
	public String getLink() {
		return link;
	}
	/**
	 * 设置：图片简介
	 */
	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	/**
	 * 获取：图片简介
	 */
	public String getImgs() {
		return imgs;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	@Override
	public String toString() {
		return "CdActivityEntity{" +
				"id=" + id +
				", type='" + type + '\'' +
				", locationType='" + locationType + '\'' +
				", title='" + title + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", state='" + state + '\'' +
				", storeId=" + storeId +
				", link='" + link + '\'' +
				", imgs='" + imgs + '\'' +
				", storeName='" + storeName + '\'' +
				'}';
	}
}
