package cn.gribe.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.codec.digest.DigestUtils;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 用户
 * Created by Zhugw on 2018/11/4 0004.
 */
@TableName("cd_user")
public class UserEntity {

    @TableId
    private Integer id;

    //用户名称
    private String userName;

    @NotBlank(message="手机号不能为空")
    private String phone;

    //状态：0:生效；1:失效
    private Integer state;

    private Integer age;

    private String lat;

    private String lon;

    private Date createTime;

    private String lastLoginIp;

    private Date lastLoginTime;

    private String headImg;

    @NotBlank(message="密码不能为空")
    private String password;

    private String realPassword;

    public UserEntity(){}

    public UserEntity(String phone,String password){
        this.phone = phone;
        this.password = DigestUtils.sha256Hex(password);
        this.realPassword = password;
        this.createTime = new Date();
        this.state = 0;
    }

    public UserEntity setEmpty(){
        this.setRealPassword("");
        this.setPassword("");
        return this;
    }

    public boolean isDisable(){
        if(this.state != null && this.state == 1){
            return true;
        }
        return false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealPassword() {
        return realPassword;
    }

    public void setRealPassword(String realPassword) {
        this.realPassword = realPassword;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", state=" + state +
                ", age=" + age +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", createTime=" + createTime +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", headImg='" + headImg + '\'' +
                ", password='" + password + '\'' +
                ", realPassword='" + realPassword + '\'' +
                '}';
    }
}
