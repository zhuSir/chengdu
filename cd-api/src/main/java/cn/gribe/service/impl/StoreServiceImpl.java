package cn.gribe.service.impl;

import cn.gribe.common.utils.DistanceUtils;
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

import java.util.HashMap;
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
            wrapper.orderBy("score",false);
        }else if("3".equals(params.get("type"))){
            wrapper.orderBy("sales",false);
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
                    store.setDistance(Double.valueOf(DistanceUtils.LantitudeLongitudeDist(Double.valueOf(lon1),Double.valueOf(lat1),lon,lat)));
                    stores.set(i,store);
                }
            }
            sortDistance(stores);//排序
            page.setRecords(stores);
        }
        return new PageUtils(page);
    }

    /**
     * 权重冒泡排序
     * @param data
     */
    public static List<StoreEntity> sortDistance(List<StoreEntity> data){
        for(int i=0;i<data.size()-1;i++){//外层循环控制排序趟数
            for(int j=0;j<data.size()-1-i;j++){//内层循环控制每一趟排序多少次
                StoreEntity d1 = data.get(j);
                StoreEntity d2 = data.get(j+1);
                if(d1.getDistance() > d2.getDistance()){
                    data.set(j,d2);
                    data.set(j+1,d1);
                }
            }
        }
        return data;
    }

    @Override
    public List<StoreEntity> queryByLocation(String latStr, String lonStr, Integer page, Integer limit) {
        //查询附近商铺
        Map params = new HashMap();
        params.put("page",String.valueOf(page));
        params.put("limit",String.valueOf(limit));
        Page queryPage = new Query<StoreEntity>(params).getPage();
        EntityWrapper wrapper = new EntityWrapper<>();
        Page pageRes = this.selectPage(queryPage,wrapper);
        double lon = Double.valueOf(lonStr);
        double lat = Double.valueOf(latStr);
        List<StoreEntity> stores = pageRes.getRecords();
        for(int i =0;i<stores.size();i++){
            StoreEntity store = stores.get(i);
            String lon1 = store.getLon();
            String lat1 = store.getLat();
            if(StringUtils.isNotEmpty(lon1) && StringUtils.isNotEmpty(lat1)){
                //计算距离
                double distance = Double.valueOf(
                        DistanceUtils.LantitudeLongitudeDist(
                            Double.valueOf(lon1),
                            Double.valueOf(lat1),
                            lon,
                            lat));
                store.setDistance(distance);
                stores.set(i,store);
            }
        }
        stores = sortDistance(stores);//排序
        return stores;
    }

    @Override
    public PageUtils queryPageByUserId(Map<String, Object> params, Integer userId) {
        Page<StoreEntity> page = new Query<StoreEntity>(params).getPage();
        page.setRecords(this.baseMapper.selectPageByUserId(page,userId));
        return new PageUtils(page);
    }

}
