package cn.gribe.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;

import cn.gribe.modules.sys.dao.DcChannelInfoDao;
import cn.gribe.modules.sys.entity.DcChannelInfoEntity;
import cn.gribe.modules.sys.service.DcChannelInfoService;


@Service("dcChannelInfoService")
public class DcChannelInfoServiceImpl extends ServiceImpl<DcChannelInfoDao, DcChannelInfoEntity> implements DcChannelInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DcChannelInfoEntity> page = this.selectPage(
                new Query<DcChannelInfoEntity>(params).getPage(),
                new EntityWrapper<DcChannelInfoEntity>()
        );

        return new PageUtils(page);
    }

}
