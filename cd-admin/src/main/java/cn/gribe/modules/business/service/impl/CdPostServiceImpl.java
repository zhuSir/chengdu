package cn.gribe.modules.business.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;
import cn.gribe.modules.business.dao.CdPostDao;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.gribe.entity.PostEntity;
import cn.gribe.modules.business.service.CdPostService;


@Service("cdPostService")
public class CdPostServiceImpl extends ServiceImpl<CdPostDao, PostEntity> implements CdPostService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<PostEntity> page = this.selectPage(
                new Query<PostEntity>(params).getPage(),
                new EntityWrapper<PostEntity>()
        );

        return new PageUtils(page);
    }

}
