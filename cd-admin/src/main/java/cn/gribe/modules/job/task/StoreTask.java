package cn.gribe.modules.job.task;

import cn.gribe.entity.CommentEntity;
import cn.gribe.entity.OrderEntity;
import cn.gribe.entity.StoreEntity;
import cn.gribe.modules.business.service.CdCommentService;
import cn.gribe.modules.business.service.CdOrderService;
import cn.gribe.modules.business.service.CdStoreService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 店鋪類任務
 */
@Component("storeTask")
public class StoreTask {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CdStoreService storeService;

	@Autowired
	private CdOrderService orderService;

	@Autowired
	private CdCommentService commentService;

	/**
	 * 評分任務
	 * 获取所有店铺根据店铺订单进行评分
	 */
	public void grade(){
		logger.info("===>>>:开始执行店铺评分及销量统计定时任务");
		List<StoreEntity> storeList = storeService.selectList(new EntityWrapper<>());
		for(StoreEntity store : storeList){
			EntityWrapper wrapper = new EntityWrapper();
			wrapper.eq("store_id",store.getId());
			List<CommentEntity> commentList = commentService.selectList(wrapper);
			double sumScore = 0;
			//分数
			for(CommentEntity comment : commentList){
				if(StringUtils.isNotEmpty(comment.getScore())){
					double score = Double.valueOf(comment.getScore());
					sumScore+=score;
				}
			}
			if(sumScore > 0){
				Double score = sumScore/commentList.size();
				store.setScore(score.intValue());
			}
			//销量
			wrapper.eq("state", OrderEntity.STATE_FINISHED);//完成状态
			int orderCount = orderService.selectCount(wrapper);
			store.setSales(orderCount);
			storeService.updateById(store);//更新店铺
		}
		logger.info("===>>>:结束执行店铺评分及销量统计定时任务");
	}

}
