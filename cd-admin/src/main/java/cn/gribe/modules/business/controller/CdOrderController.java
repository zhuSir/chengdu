package cn.gribe.modules.business.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.validator.Assert;
import cn.gribe.common.validator.ValidatorUtils;
import cn.gribe.entity.StoreEntity;
import cn.gribe.modules.business.service.CdStoreService;
import cn.gribe.modules.sys.entity.SysDictEntity;
import cn.gribe.modules.sys.entity.SysUserEntity;
import cn.gribe.modules.sys.service.SysDictService;
import cn.gribe.modules.sys.shiro.ShiroUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.gribe.entity.OrderEntity;
import cn.gribe.modules.business.service.CdOrderService;
import cn.gribe.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-24 15:34:15
 */
@RestController
@RequestMapping("business/cdorder")
public class CdOrderController {
    @Autowired
    private CdOrderService cdOrderService;

    @Autowired
    private SysDictService dictService;

    @Autowired
    private CdStoreService cdStoreService;

    /**
     * 信息
     */
    @RequestMapping("/init")
    public R init(){
        R r = R.ok();
        List<SysDictEntity> payType = dictService.getDict("order_pay_type");
        if(payType != null){
            r.put("payType", payType);
        }
        List<SysDictEntity>  status = dictService.getDict("order_status");
        if(status != null){
            r.put("status",status);
        }
        return r;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:cdorder:list")
    public R list(@RequestParam Map<String, Object> params){
        //,Integer page, Integer limit, String phone, String storeName, String startTime, String endTime, Integer status
        SysUserEntity user = ShiroUtils.getUserEntity();
        //判断如果用户有关联店铺则给与他查询店铺的信息
        StoreEntity storeEntity = cdStoreService.queryByUserId(user.getUserId());
        if(storeEntity != null){
             params.put("storeId",storeEntity.getId());
        }
        PageUtils res = cdOrderService.queryPage(params);
        System.out.println("res:"+JSONObject.toJSONString(res));
        return R.ok().put("page", res);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("business:cdorder:info")
    public R info(@PathVariable("id") Integer id){
        OrderEntity cdOrder = cdOrderService.selectById(id);

        return R.ok().put("cdOrder", cdOrder);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:cdorder:save")
    public R save(@RequestBody OrderEntity cdOrder){
        cdOrder.setUpdateTime(new Date());
        cdOrderService.insert(cdOrder);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:cdorder:update")
    public R update(@RequestBody OrderEntity cdOrder){
        ValidatorUtils.validateEntity(cdOrder);
        cdOrder.setUpdateTime(new Date());
        cdOrderService.updateAllColumnById(cdOrder);//全部更新
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:cdorder:delete")
    public R delete(@RequestBody Integer[] ids){
        cdOrderService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 发货
     * @param expressCompany
     * @param expressCode
     * @param orderId
     * @return
     */
    @RequestMapping("/shipments")
    @RequiresPermissions("business:cdorder:shipments")
    public R shipments(String expressCompany,String expressCode,Integer orderId){
        Assert.isNull(orderId,"参数错误，请刷新重试");
        OrderEntity orderEntity = cdOrderService.selectById(orderId);
        Assert.isNull(orderEntity,"订单错误，请刷新重试");
        Assert.state(orderEntity.getState().intValue() != OrderEntity.STATE_AWAIT_USE.intValue(),
                "该订单待使用，不能实现发货操作");
        Assert.state(StringUtils.isEmpty(expressCompany),"参数错误");
        Assert.state(StringUtils.isEmpty(expressCode),"参数错误");
        orderEntity.setExpressCompany(expressCompany);
        orderEntity.setExpressCode(expressCode);
        cdOrderService.updateById(orderEntity);
        return R.ok();
    }

    /**
     * 退单
     * @param orderId
     * @return
     */
    @RequestMapping("/refund")
    @RequiresPermissions("business:cdorder:refund")
    public R refund(Integer orderId){
        Assert.isNull(orderId,"参数错误，请刷新重试");
        //退单
        cdOrderService.refundOrder(orderId);
        return R.ok();
    }

    /**
     * 完成订单
     * @param orderId
     * @return
     */
    @RequestMapping("/finished")
    @RequiresPermissions("business:cdorder:finished")
    public R finished(Integer orderId){
        Assert.isNull(orderId,"参数错误，请刷新重试");
        OrderEntity orderEntity = cdOrderService.selectById(orderId);
        Assert.isNull(orderEntity,"订单错误，请刷新重试");
        Assert.state(orderEntity.getState().intValue() != OrderEntity.STATE_AWAIT_USE.intValue(),
                "该订单不是待使用状态，不能进行完成操作");
        orderEntity.setState(OrderEntity.STATE_FINISHED);
        cdOrderService.updateById(orderEntity);
        return R.ok();
    }

}
