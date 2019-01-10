package cn.gribe.modules.job.task;

import cn.gribe.entity.ProductEntity;
import cn.gribe.modules.business.service.CdOrderService;
import cn.gribe.modules.business.service.CdProductService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 商品類任務
 */
@Component("productTask")
public class ProductTask {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CdOrderService orderService;

	@Autowired
	private CdProductService productService;

	/**
	 * 销量定时任务
	 */
	public void sales(){
		logger.info("===>>>:开始执行商品销量统计定时任务");
		List<ProductEntity> productList = productService.selectList(new EntityWrapper<>());
		for(ProductEntity product : productList){
			EntityWrapper wrapper = new EntityWrapper();
			wrapper.eq("store_id",product.getId());
			int orderCount = orderService.selectCount(wrapper);
			//销量
			product.setSales(orderCount);
			productService.updateById(product);//更新
		}
		logger.info("===>>>:结束执行商品销量统计定时任务");
	}

}
