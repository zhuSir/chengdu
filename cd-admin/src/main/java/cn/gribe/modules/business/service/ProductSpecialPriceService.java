package cn.gribe.modules.business.service;

import cn.gribe.entity.ProductSpecialPrice;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * @ClassName ProductSpecialPriceService
 * @Description TODO
 * @Author Administrator
 * @Date 2019/1/11 14:50
 * @Version 1.0
 **/
public interface ProductSpecialPriceService  extends IService<ProductSpecialPrice> {

    List<ProductSpecialPrice> selectListByProductId(Integer id);
}
