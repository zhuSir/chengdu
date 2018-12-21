package cn.gribe.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.validation.constraints.NotBlank;

/**
 * Created by Zhugw on 2018/12/12 0012.
 */
@TableName("cd_product_tag")
public class ProductTagEntity {

    @TableId
    private Integer id;

    //商品id
    private Integer productId;

    //tag名称
    private String name;

    //tag值
    private String value;

    //tag code
    private String code;

    //排序位置
    private int sort;

    public String getTag(){
        return this.name+"/"+this.value;
    }

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
