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
	private int id;
	/**
	 * 店铺id
	 */
	private int storeId;
	/**
	 * 评论id
	 */
	private int commentId;
	/**
	 * 用户id
	 */
	private int userId;

	/**
	 * 小组id
	 */
	private int groupId;

	/**
	 * 帖id
	 */
	private int postId;

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
	 * 设置：店铺id
	 */
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	/**
	 * 获取：店铺id
	 */
	public int getStoreId() {
		return storeId;
	}
	/**
	 * 设置：评论id
	 */
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	/**
	 * 获取：评论id
	 */
	public int getCommentId() {
		return commentId;
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

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
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
