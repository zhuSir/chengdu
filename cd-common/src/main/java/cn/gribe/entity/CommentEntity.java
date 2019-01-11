package cn.gribe.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 */
@TableName("cd_comment")
public class CommentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//正常
	public static Integer STATUS_NORMAL = 0;

	//禁用
	public static Integer STATUS_DISABLE = 1;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 分数
	 */
	private String score;
	/**
	 * 内容
	 */
	@NotBlank(message="内容不能为空")
	private String content;
	/**
	 * 图片
	 */
	private String imgs;
	/**
	 * 产品id
	 */
	private Integer productId;
	/**
	 * 用户id
	 */
	private Integer userId;

	/**
	 * 店铺id
	 */
	private Integer storeId;

	/**
	 * 帖id
	 */
	private Integer postId;

	/**
	 * 类型
	 */
	private Integer type;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 评论id
	 */
	private Integer commentId;

	/**
	 * 评论列表
	 */
	@TableField(exist = false)
	List<CommentEntity> commentEntityList;

	/**
	 * 用户名
	 */
	@TableField(exist = false)
	private String userName;

	@TableField(exist = false)
	private String userHeadUrl;

	/**
	 * 评论名称
	 */
	@TableField(exist = false)
	private String name;

	/**
	 * 设置：id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：分数
	 */
	public void setScore(String score) {
		this.score = score;
	}
	/**
	 * 获取：分数
	 */
	public String getScore() {
		return score;
	}
	/**
	 * 设置：内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：内容
	 */
	public String getContent() {
		return content;
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
	 * 设置：图片
	 */
	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	/**
	 * 获取：图片
	 */
	public String getImgs() {
		return imgs;
	}
	/**
	 * 设置：产品id
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	/**
	 * 获取：产品id
	 */
	public Integer getProductId() {
		return productId;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Integer getUserId() {
		return userId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getUserHeadUrl() {
		return userHeadUrl;
	}

	public void setUserHeadUrl(String userHeadUrl) {
		this.userHeadUrl = userHeadUrl;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public List<CommentEntity> getCommentEntityList() {
		return commentEntityList;
	}

	public void setCommentEntityList(List<CommentEntity> commentEntityList) {
		this.commentEntityList = commentEntityList;
	}
}
