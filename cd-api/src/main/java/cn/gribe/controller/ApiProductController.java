package cn.gribe.controller;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;
import cn.gribe.entity.ProductEntity;
import cn.gribe.service.OrderService;
import cn.gribe.service.ProductService;
import cn.gribe.entity.ProductTagEntity;
import cn.gribe.service.ProductTagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 产品
 */
@RestController
@RequestMapping("/api/product")
public class ApiProductController {
    @Autowired
    private ProductService cdProductService;

    @Autowired
    private ProductTagService productTagService;

    @Autowired
    private OrderService orderService;

    /**
     * 列表
     */
    @ApiOperation("产品列表")
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cdProductService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @ApiOperation("产品详情")
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        ProductEntity cdProduct = cdProductService.selectById(id);
        List<ProductTagEntity> productTags = productTagService.selectList(id);
        cdProduct.setTags(productTags);
        int Sales = orderService.selectSales(id);
        cdProduct.setSales(Sales);
        return R.ok().put("info", cdProduct);
    }

}
