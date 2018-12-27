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

import java.util.List;
import java.util.Map;


@Service("cdOrderService")
public class CdOrderServiceImpl extends ServiceImpl<CdOrderDao, OrderEntity> implements CdOrderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<OrderEntity> resPage = new Query<OrderEntity>(params).getPage();// 当前页，总条数 构造 page 对象
        //String phone, String storeName,Integer storeId,Integer status, String startTime, String endTime
        Object phone = params.get("phone");
        String startTime = params.get("startTime") != null && StringUtils.isNotEmpty((String) params.get("startTime"))? params.get("startTime")+" 00:00:00" : null;
        String endTime = params.get("endTime") != null && StringUtils.isNotEmpty((String) params.get("endTime")) ? params.get("endTime")+" 23:59:59" : null;
        Object storeName = params.get("storeName");
        Object storeId = params.get("storeId");
        Object status = params.get("status");
        List<OrderEntity> records = this.baseMapper.selectPage(phone,storeName,storeId,status,startTime,endTime);
        resPage.setRecords(records);
        return new PageUtils(resPage);
    }

}
