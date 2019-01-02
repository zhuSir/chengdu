package cn.gribe.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;
import cn.gribe.common.utils.Utils;
import cn.gribe.dao.StoreDao;
import cn.gribe.entity.StoreEntity;
import cn.gribe.service.StoreService;
import com.aliyuncs.utils.StringUtils;
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
        if("1".equals(params.get("type"))){
            String lon = (String) params.get("lon");
            String lat = (String) params.get("lat");
            wrapper.orderBy("("+lon+" - lon)+("+lat+" - lat)",true);
        }else if("2".equals(params.get("type"))){
            wrapper.orderBy("score",true);
        }else if("3".equals(params.get("type"))){
            wrapper.orderBy("sales",true);
        }
        Object storeType = params.get("storeType");
        if(storeType != null){
            wrapper.eq("type",storeType);
        }
        Object name = params.get("name");
        if(name != null){
            wrapper.like("name",String.valueOf(name));
        }
        Page<StoreEntity> page = this.selectPage(
                new Query<StoreEntity>(params).getPage(),
                wrapper
        );
        //计算商家距离
        if("1".equals(params.get("type"))
                && params.get("lon") != null
                && params.get("lat") != null){
            double lon = Double.valueOf((String) params.get("lon"));
            double lat = Double.valueOf((String) params.get("lat"));
            List<StoreEntity> stores = page.getRecords();
            for(int i =0;i<stores.size();i++){
                StoreEntity store = stores.get(i);
                String lon1 = store.getLon();
                String lat1 = store.getLat();
                if(StringUtils.isNotEmpty(lon1) && StringUtils.isNotEmpty(lat1)){
                    //计算距离
                    store.setDistance(Utils.getDistance(Double.valueOf(lat1),Double.valueOf(lon),lat,lon));
                    stores.set(i,store);
                }
            }
            page.setRecords(stores);
        }
        return new PageUtils(page);
    }

    @Override
    public List<StoreEntity> queryByLocation(String lat, String lon, Integer page, Integer limit) {
        //查询附近商铺
        List<StoreEntity> res = this.selectList(new EntityWrapper<>());
        return res;
    }

}
