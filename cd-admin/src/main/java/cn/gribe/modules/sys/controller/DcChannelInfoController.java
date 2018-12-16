package cn.gribe.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import cn.gribe.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.gribe.modules.sys.entity.DcChannelInfoEntity;
import cn.gribe.modules.sys.service.DcChannelInfoService;
import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-07 17:38:54
 */
@RestController
@RequestMapping("sys/dcchannelinfo")
public class DcChannelInfoController {
    @Autowired
    private DcChannelInfoService dcChannelInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dcchannelinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dcChannelInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:dcchannelinfo:info")
    public R info(@PathVariable("id") Integer id){
        DcChannelInfoEntity dcChannelInfo = dcChannelInfoService.selectById(id);

        return R.ok().put("dcChannelInfo", dcChannelInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dcchannelinfo:save")
    public R save(@RequestBody DcChannelInfoEntity dcChannelInfo){
        dcChannelInfoService.insert(dcChannelInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dcchannelinfo:update")
    public R update(@RequestBody DcChannelInfoEntity dcChannelInfo){
        ValidatorUtils.validateEntity(dcChannelInfo);
        dcChannelInfoService.updateAllColumnById(dcChannelInfo);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dcchannelinfo:delete")
    public R delete(@RequestBody Integer[] ids){
        dcChannelInfoService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
