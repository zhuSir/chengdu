//package cn.gribe.controller;
//
//import PageUtils;
//import R;
//import ActivityEntity;
//import StoreEntity;
//import ActivityService;
//import StoreService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * Created by Zhugw on 2018/12/6 0006.
// */
//@RestController
//@RequestMapping("/api")
//public class ApiIndexController {
//
//    @Autowired
//    private ActivityService activityService;
//
//    @Autowired
//    private StoreService storeService;
//
//    @RequestMapping("/index")
//    public R list(String lon,String lat){
//        R r = R.ok();
//        //pictures;
//        List<ActivityEntity> pictures = activityService.queryAllActivity();
//        r.put("pictures",pictures);
//        //stores
//        PageUtils storePage = storeService.queryPage(1,lon,lat,1,10);
//        List stores = storePage.getList();
//        r.put("stores",stores);
//        return r;
//    }
//
//    @RequestMapping("/index/search")
//    public R search(){
//        R r = R.ok();
//        //pictures;
//
//        //stores
//
//        return r;
//    }
//
//}
