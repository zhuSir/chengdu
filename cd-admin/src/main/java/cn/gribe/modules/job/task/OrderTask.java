package cn.gribe.modules.job.task;

import cn.gribe.common.utils.DateUtils;
import cn.gribe.entity.OrderEntity;
import cn.gribe.entity.ProductEntity;
import cn.gribe.modules.business.service.CdOrderService;
import cn.gribe.modules.business.service.CdProductService;
import com.aliyun.oss.common.utils.DateUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 订单类任務
 */
@Component("orderTask")
public class OrderTask {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CdOrderService orderService;

    /**
     * 发货后7天订单自动收货转待评价，15天自动转完成订单
	 */
	public void statusCheck(){
		logger.info("===>>>:开始执行订单状态检测定时任务");
		//发货后7天订单自动收货转待评价
		EntityWrapper wrapper7 = new EntityWrapper();
		String time7 = DateUtils.format(DateUtils.addDateDays(new Date(),-7));
		wrapper7.like("create_time",time7);
		wrapper7.eq("state", OrderEntity.STATE_AWAIT_USE);
		List<OrderEntity> orderList = orderService.selectList(wrapper7);
		for(OrderEntity order : orderList){
			logger.info("==>>:待使用七天转待评价订单: "+order.toString());
			order.setState(OrderEntity.STATE_AWAIT_EVALUATE);
			orderService.updateById(order);
		}
		//15天自动转完成订单
		EntityWrapper wrapper15 = new EntityWrapper();
		String time15 = DateUtils.format(DateUtils.addDateDays(new Date(),-15));
		wrapper15.like("create_time",time15);
		wrapper15.eq("state", OrderEntity.STATE_AWAIT_EVALUATE);
		List<OrderEntity> orderList15 = orderService.selectList(wrapper15);
		for(OrderEntity order : orderList15){
			logger.info("==>>:待评价十五天转完成订单: "+order.toString());
			order.setState(OrderEntity.STATE_FINISHED);
			orderService.updateById(order);
		}
		logger.info("===>>>:结束执行订单状态检测定时任务");
	}

}
