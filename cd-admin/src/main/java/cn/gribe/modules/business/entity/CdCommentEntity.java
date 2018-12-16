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
@TableName("cd_comment")
public class CdCommentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

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
	 * 评论id
	 */
	private Integer commentId;

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
	 * 设置：帖id
	 */
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	/**
	 * 获取：帖id
	 */
	public Integer getPostId() {
		return postId;
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
}
