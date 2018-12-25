package cn.gribe.modules.business.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;
import cn.gribe.entity.OrderEntity;
import cn.gribe.entity.PostEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.gribe.modules.business.dao.CdOrderDao;
import cn.gribe.modules.business.service.CdOrderService;

import java.util.HashMap;
import java.util.Map;


@Service("cdOrderService")
public class CdOrderServiceImpl extends ServiceImpl<CdOrderDao, OrderEntity> implements CdOrderService {

    @Override
    public PageUtils queryPage(Integer page,Integer limit,String phone, String storeName,Integer storeId, String startTime, String endTime) {
        Map<String, Object> param = new HashMap<>();
        param.put("page",String.valueOf(page));
        param.put("limit",String.valueOf(limit));
        Page<OrderEntity> resPage = new Query<OrderEntity>(param).getPage();// 当前页，总条数 构造 page 对象
        phone = StringUtils.isNotEmpty(phone) ? phone.trim() : null;
        startTime = StringUtils.isNotEmpty(startTime) ? startTime.trim()+" 00:00:00" : null;
        endTime = StringUtils.isNotEmpty(endTime)? endTime.trim()+" 23:59:59" : null;
        resPage.setRecords(this.baseMapper.selectPage(phone,storeName,storeId,startTime,endTime));
        return new PageUtils(resPage);
    }

}
