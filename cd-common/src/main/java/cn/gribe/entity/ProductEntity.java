package cn.gribe.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotBlank(message="产品名称不能为空")
    private String name;

    //产品类型
    @NotBlank(message="产品类型不能为空")
    private String type;

    //简介
    @NotBlank(message="产品简介不能为空")
    private String about;

    //简介图片
    private String imgs;

    //运费
    @NotBlank(message="产品运费不能为空")
    private String freight;


    //缩略图
    private String shortImg;

    //标签
    private String tag;

    //今日库存
    @NotNull(message="产品今日库存不能为空")
    private Integer todayInventory;

    //总库存
    @NotNull(message="产品总库存不能为空")
    private Integer sumInventory;

    //价格
    @NotNull(message="产品价格不能为空")
    private Double price;

    //商铺Id
    private Integer storeId;

    //状态
    @NotBlank(message="产品状态不能为空")
    private String state;

    private Date createTime;

    private Date updateTime;

    /**
     * 属性类型
     */
    private Integer attributeType;

    /**
     * 销量
     */
    private int sales;

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

    public String getShortImg() {
        return shortImg;
    }

    public void setShortImg(String shortImg) {
        this.shortImg = shortImg;
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

    public Integer getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(Integer attributeType) {
        this.attributeType = attributeType;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }
}
