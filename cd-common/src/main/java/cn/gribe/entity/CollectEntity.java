package cn.gribe.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 收藏类
 */
@TableName("cd_collect")
public class CollectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Integer id;
	/**
	 * 店铺id
	 */
	private Integer storeId;
	/**
	 * 评论id
	 */
	private Integer commentId;
	/**
	 * 用户id
	 */
	private Integer userId;

	/**
	 * 小组id
	 */
	private Integer groupId;

	/**
	 * 帖id
	 */
	private Integer postId;

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
	 * 设置：店铺id
	 */
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	/**
	 * 获取：店铺id
	 */
	public Integer getStoreId() {
		return storeId;
	}
	/**
	 * 设置：评论id
	 */
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	/**
	 * 获取：评论id
	 */
	public Integer getCommentId() {
		return commentId;
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

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	@Override
	public String toString() {
		return "CollectEntity{" +
				"id=" + id +
				", storeId=" + storeId +
				", commentId=" + commentId +
				", userId=" + userId +
				", groupId=" + groupId +
				", postId=" + postId +
				'}';
	}
}
