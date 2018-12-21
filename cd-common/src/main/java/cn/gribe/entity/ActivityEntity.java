package cn.gribe.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * 活动
 * Created by Zhugw on 2018/11/4 0004.
 */
@TableName("cd_activity")
public class ActivityEntity {

    @TableId
    private Integer id;

    //类型（0：网站推广链接；1：商铺链接）
    private String type;

    //推广显示位置
    private String locationType;

    //标题
    private String title;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    //状态（0：使用；1：禁用）
    private String state;

    //店铺id
    private Integer storeId;

    //链接地址
    private String link;

    //链接图片
    private String imgs;

    //店铺名称
    @TableField(exist = false)
    private String storeName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
