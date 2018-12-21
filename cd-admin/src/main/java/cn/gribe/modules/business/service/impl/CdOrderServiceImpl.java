package cn.gribe.modules.business.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.entity.OrderEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.gribe.modules.business.dao.CdOrderDao;
import cn.gribe.modules.business.service.CdOrderService;


@Service("cdOrderService")
public class CdOrderServiceImpl extends ServiceImpl<CdOrderDao, OrderEntity> implements CdOrderService {

    @Override
    public PageUtils queryPage(String phone, String storeName, String startTime, String endTime) {
        Page<OrderEntity> page = new Page<>();// 当前页，总条数 构造 page 对象
//        Object phone = params.get("phone");
//        String phoneNumber = phone == null ? null : (String)phone;
//        Object store_name = params.get("storeName");
//        String storeName = store_name == null ? null : (String)store_name;
        phone = StringUtils.isNotEmpty(phone) ? phone.trim() : null;
        startTime = StringUtils.isNotEmpty(startTime) ? startTime.trim()+" 00:00:00" : null;
        endTime = StringUtils.isNotEmpty(endTime)? endTime.trim()+" 23:59:59" : null;
        page.setRecords(this.baseMapper.selectPage(phone,storeName,startTime,endTime));
        return new PageUtils(page);
    }

}
