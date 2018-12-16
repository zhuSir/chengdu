package cn.gribe.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;
import cn.gribe.dao.StoreDao;
import cn.gribe.entity.StoreEntity;
import cn.gribe.service.StoreService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("cdStoreService")
public class StoreServiceImpl extends ServiceImpl<StoreDao, StoreEntity> implements StoreService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper<StoreEntity> wrapper = new EntityWrapper<StoreEntity>();
        if(params.get("type").equals("1")){
            String lon = (String) params.get("lon");
            String lat = (String) params.get("lat");
            wrapper.orderBy("("+lon+" - lon)+("+lat+" - lat)",true);
        }else if(params.get("type").equals("2")){
            wrapper.orderBy("score",true);
        }else if(params.get("type").equals("3")){
            wrapper.orderBy("sales",true);
        }
        Page<StoreEntity> page = this.selectPage(
                new Query<StoreEntity>(params).getPage(),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<StoreEntity> queryByLocation(String lat, String lon, Integer page, Integer limit) {
        //查询附近商铺
        List<StoreEntity> res = this.selectList(new EntityWrapper<>());
        return res;
    }

}
