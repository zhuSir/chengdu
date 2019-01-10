package cn.gribe.modules.business.controller;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.gribe.common.CommonUtils;
import cn.gribe.common.utils.DateUtils;
import cn.gribe.common.utils.ExcelUtil;
import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.validator.Assert;
import cn.gribe.common.validator.ValidatorUtils;
import cn.gribe.entity.StoreEntity;
import cn.gribe.modules.business.service.CdStoreService;
import cn.gribe.modules.sys.entity.SysDictEntity;
import cn.gribe.modules.sys.entity.SysUserEntity;
import cn.gribe.modules.sys.service.SysDictService;
import cn.gribe.modules.sys.service.SysUserService;
import cn.gribe.modules.sys.shiro.ShiroUtils;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.gribe.entity.OrderEntity;
import cn.gribe.modules.business.service.CdOrderService;
import cn.gribe.common.utils.R;

import javax.servlet.http.HttpServletResponse;


/**
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

    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 信息
     */
    @RequestMapping("/init")
    public R init() {
        R r = R.ok();
        List<SysDictEntity> payType = dictService.getDict("order_pay_type");
        if (payType != null) {
            r.put("payType", payType);
        }
        List<SysDictEntity> status = dictService.getDict("order_status");
        if (status != null) {
            r.put("status", status);
        }
        List<SysDictEntity> payResults = dictService.getDict("order_pay_status");
        if (payResults != null) {
            r.put("payResults", payResults);
        }
        return r;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:cdorder:list")
    public R list(@RequestParam Map<String, Object> params) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        //判断如果用户有关联店铺则给与他查询店铺的信息
        StoreEntity storeEntity = cdStoreService.queryByUserId(user.getUserId());
        //判断如果用户有关联店铺则给与他查询店铺的信息
        user = sysUserService.queryByRoleNameAndUserId("商家",user.getUserId());
        PageUtils page = new PageUtils(new Page<>());
        if(user != null){
            if(storeEntity != null){
                params.put("storeId",storeEntity.getId());
                page = cdOrderService.queryPage(params);
            }
        }else{
            page = cdOrderService.queryPage(params);
        }
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("business:cdorder:info")
    public R info(@PathVariable("id") Integer id) {
        OrderEntity cdOrder = cdOrderService.selectById(id);

        return R.ok().put("cdOrder", cdOrder);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:cdorder:save")
    public R save(@RequestBody OrderEntity cdOrder) {
        cdOrder.setUpdateTime(new Date());
        cdOrderService.insert(cdOrder);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:cdorder:update")
    public R update(@RequestBody OrderEntity cdOrder) {
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
    public R delete(@RequestBody Integer[] ids) {
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
    public R shipments(String expressCompany, String expressCode, Integer orderId) {
        Assert.isNull(orderId, "参数错误，请刷新重试");
        OrderEntity orderEntity = cdOrderService.selectById(orderId);
        Assert.isNull(orderEntity, "订单错误，请刷新重试");
        Assert.state(orderEntity.getState().intValue() != OrderEntity.STATE_AWAIT_USE.intValue(),
                "该订单待使用，不能实现发货操作");
        Assert.state(StringUtils.isEmpty(expressCompany), "参数错误");
        Assert.state(StringUtils.isEmpty(expressCode), "参数错误");
        orderEntity.setExpressCompany(expressCompany);
        orderEntity.setExpressCode(expressCode);
        cdOrderService.updateById(orderEntity);
        return R.ok();
    }

    /**
     * 退单
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/refund")
    @RequiresPermissions("business:cdorder:refund")
    public R refund(Integer orderId) {
        Assert.isNull(orderId, "参数错误，请刷新重试");
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
    public R finished(Integer orderId) {
        Assert.isNull(orderId, "参数错误，请刷新重试");
        OrderEntity orderEntity = cdOrderService.selectById(orderId);
        Assert.isNull(orderEntity, "订单错误，请刷新重试");
        Assert.state(orderEntity.getState().intValue() != OrderEntity.STATE_AWAIT_USE.intValue(),
                "该订单不是待使用状态，不能进行完成操作");
        orderEntity.setState(OrderEntity.STATE_FINISHED);
        cdOrderService.updateById(orderEntity);
        return R.ok();
    }


    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public void export(@RequestParam Map<String, Object> params,HttpServletResponse response) throws Exception {
        SysUserEntity user = ShiroUtils.getUserEntity();
        //判断如果用户有关联店铺则给与他查询店铺的信息
        StoreEntity storeEntity = cdStoreService.queryByUserId(user.getUserId());
        Integer storeId = null;
        if (storeEntity != null) {
            storeId = storeEntity.getId();
        }
        //获取数据
        params.put("storeId",storeId);
        List<OrderEntity> list = cdOrderService.selectByParams(params);
        //excel标题
        String[] title = {"订单号", "订单创建时间", "商品名称", "店铺名称",
                "订单状态","收货人姓名","收货人手机号",
                "收货地址","数量","价格","支付类型","支付结果"
        };
        //excel文件名
        String fileName = "订单信息" + DateUtils.format(new Date()) + ".xls";
        //sheet名
        String sheetName = "订单信息";
        String[][] content = new String[list.size()][title.length];
        for (int i = 0; i < list.size(); i++) {
            content[i] = new String[title.length];
            OrderEntity obj = list.get(i);
            content[i][0] = obj.getCode();
            content[i][1] = DateUtils.format(obj.getCreateTime());
            content[i][2] = obj.getProductName();
            content[i][3] = obj.getStoreName();
            content[i][4] = getDistValue("order_status",String.valueOf(obj.getState()));
            content[i][5] = String.valueOf(obj.getUserName());
            content[i][6] = String.valueOf(obj.getPhone());
            content[i][7] = String.valueOf(obj.getAddress());
            content[i][8] = String.valueOf(obj.getCount());
            content[i][9] = String.valueOf(obj.getSum());
            content[i][10] = getDistValue("order_pay_type",String.valueOf(obj.getPayType()));
            content[i][11] = String.valueOf(obj.getPayDescription());
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

        //响应到客户端
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getDistValue(String type,String code){
        List<SysDictEntity> dictList = dictService.getDict(type);
        for(SysDictEntity dict : dictList) {
            if(code.equals(dict.getCode())){
                return dict.getValue();
            }
        }
        return null;
    }

}
