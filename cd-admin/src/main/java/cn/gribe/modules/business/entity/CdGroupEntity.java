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
 * @date 2018-11-24 15:34:15
 */
@TableName("cd_group")
public class CdGroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 宣传图
	 */
	private String publicityImgs;
	/**
	 * 背景图
	 */
	private String backstageImgs;
	/**
	 * 头像
	 */
	private String headImg;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 收藏数
	 */
	private Integer collectNum;

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
	 * 设置：名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：宣传图
	 */
	public void setPublicityImgs(String publicityImgs) {
		this.publicityImgs = publicityImgs;
	}
	/**
	 * 获取：宣传图
	 */
	public String getPublicityImgs() {
		return publicityImgs;
	}
	/**
	 * 设置：背景图
	 */
	public void setBackstageImgs(String backstageImgs) {
		this.backstageImgs = backstageImgs;
	}
	/**
	 * 获取：背景图
	 */
	public String getBackstageImgs() {
		return backstageImgs;
	}
	/**
	 * 设置：头像
	 */
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	/**
	 * 获取：头像
	 */
	public String getHeadImg() {
		return headImg;
	}
	/**
	 * 设置：描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：描述
	 */
	public String getDescription() {
		return description;
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
	 * 设置：收藏数
	 */
	public void setCollectNum(Integer collectNum) {
		this.collectNum = collectNum;
	}
	/**
	 * 获取：收藏数
	 */
	public Integer getCollectNum() {
		return collectNum;
	}
}
