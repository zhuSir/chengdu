package cn.gribe.modules.business.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;
import cn.gribe.entity.UserEntity;
import cn.gribe.modules.business.service.CdUserService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.gribe.modules.business.dao.CdUserDao;


@Service("cdUserService")
public class CdUserServiceImpl extends ServiceImpl<CdUserDao, UserEntity> implements CdUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<UserEntity> page = this.selectPage(
                new Query<UserEntity>(params).getPage(),
                new EntityWrapper<UserEntity>()
        );

        return new PageUtils(page);
    }

}
