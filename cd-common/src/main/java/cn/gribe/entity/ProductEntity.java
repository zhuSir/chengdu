package cn.gribe.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;
import java.util.List;

/**
 * 产品
 * Created by Zhugw on 2018/11/4 0004.
 */
@TableName("cd_product")
public class ProductEntity {

    @TableId
    private Integer id;

    //产品名称
    private String name;

    //产品类型
    private String type;

    //简介
    private String about;

    //简介图片
    private String imgs;

    //运费
    private String freight;

    //缩略图
    private String short_img;

    //标签
    private String tag;

    //今日库存
    private Integer todayInventory;

    //总库存
    private Integer sumInventory;

    //价格
    private Double price;

    //商铺Id
    private Integer storeId;

    //状态
    private String state;

    private Date createTime;

    private Date updateTime;

    //标签
    @TableField(exist = false)
    private List<ProductTagEntity> tags;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getShort_img() {
        return short_img;
    }

    public void setShort_img(String short_img) {
        this.short_img = short_img;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getTodayInventory() {
        return todayInventory;
    }

    public void setTodayInventory(Integer todayInventory) {
        this.todayInventory = todayInventory;
    }

    public Integer getSumInventory() {
        return sumInventory;
    }

    public void setSumInventory(Integer sumInventory) {
        this.sumInventory = sumInventory;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public List<ProductTagEntity> getTags() {
        return tags;
    }

    public void setTags(List<ProductTagEntity> tags) {
        this.tags = tags;
    }
}
