package cn.gribe.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 商铺
 * Created by Zhugw on 2018/11/4 0004.
 */
@TableName("cd_store")
public class StoreEntity {

    @TableId
    private Integer id;

    //商铺名称
    @NotBlank(message="店铺名不能为空")
    private String name;

    //商铺地址
    @NotBlank(message="店铺地址不能为空")
    private String address;

    //备注
    private String remark;

    //商铺简介
    @NotBlank(message="店铺简介不能为空")
    private String about;

    //商铺类型
    @NotBlank(message="店铺类型不能为空")
    private String type;

    //商铺简介图片
    private String imgs;

    //手机号
    @NotBlank(message="店铺联系方式不能为空")
    private String phone;

    //备用手机号
    @NotBlank(message="店铺备用联系方式不能为空")
    private String backupPhone;

    //维度
    private String lat;

    //经度
    private String lon;

    //创建时间
    private Date createTime;

    //更新地址
    private Date updateTime;

    //评分 -- new
    private Integer score;

    //缩略图
    private String shortImg;

    //销量 --new
    //@NotNull(message="店铺销量不能为空")
    private Integer sales;

    //价格 --new
    @NotNull(message="店铺价格不能为空")
    private Double price;

    /**
     * 系统用户（商家号）
     */
    private Integer userId;

    //收藏
    @TableField(exist = false)
    private boolean isCollected;

    @TableField(exist = false)
    private String userName;

    //距离
    @TableField(exist = false)
    private double distance;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBackupPhone() {
        return backupPhone;
    }

    public void setBackupPhone(String backupPhone) {
        this.backupPhone = backupPhone;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShortImg() {
        return shortImg;
    }

    public void setShortImg(String shortImg) {
        this.shortImg = shortImg;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
