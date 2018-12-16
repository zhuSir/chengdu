package cn.gribe.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.dao.OrderDao;
import cn.gribe.entity.OrderEntity;
import cn.gribe.service.OrderService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import cn.gribe.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cdOrderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper wrapper = new EntityWrapper<OrderEntity>();
        Object state = params.get("state");
        if(state != null && !state.equals("0")){
            wrapper.eq("state",state);
        }
        Page<OrderEntity> page = this.selectPage(
                new Query<OrderEntity>(params).getPage(),
                wrapper
        );

        return new PageUtils(page);
    }

}
