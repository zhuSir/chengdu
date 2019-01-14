package cn.gribe.entity;

import cn.gribe.common.utils.DateUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ProductDatePrice
 * @Description use product special price
 * @Author Zhugw
 * @Date 2019/1/11 14:41
 * @Version 1.0
 **/
@TableName("cd_product_special_price")
public class ProductSpecialPrice implements Serializable {

    @TableId
    private Integer id;

    /**
     * special date
     */
    private Date date;
    /**
     * product id
     */
    private int productId;
    /**
     * special price
     */
    private double price;

    /**
     * 库存
     */
    private int inventory;

    @TableField(exist = false)
    private String strDate;

    public void transDate(){
        if(this.date == null && StringUtils.isNotEmpty(this.strDate)){
            this.date = DateUtils.stringToDate(this.strDate,"yyyy-MM-dd");
        }
    }

    public Map getMap(){
        Map results = new HashMap();
        String date = DateUtils.format(this.date);
        results.put(date,this.price);
        return results;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStrDate() {
        if(this.date != null)
            return DateUtils.format(date);
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }
}
