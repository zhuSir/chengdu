package cn.gribe.modules.business.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;
import cn.gribe.modules.business.entity.CdUserEntity;
import cn.gribe.modules.business.service.CdUserService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.gribe.modules.business.dao.CdUserDao;


@Service("cdUserService")
public class CdUserServiceImpl extends ServiceImpl<CdUserDao, CdUserEntity> implements CdUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CdUserEntity> page = this.selectPage(
                new Query<CdUserEntity>(params).getPage(),
                new EntityWrapper<CdUserEntity>()
        );

        return new PageUtils(page);
    }

}
