package cn.gribe.modules.business.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;
import cn.gribe.modules.business.dao.CdProductDao;
import cn.gribe.modules.business.entity.CdProductEntity;
import cn.gribe.modules.business.service.CdProductService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("cdProductService")
public class CdProductServiceImpl extends ServiceImpl<CdProductDao, CdProductEntity> implements CdProductService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CdProductEntity> page = this.selectPage(
                new Query<CdProductEntity>(params).getPage(),
                new EntityWrapper<CdProductEntity>()
        );

        return new PageUtils(page);
    }

}
