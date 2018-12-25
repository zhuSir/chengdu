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
        EntityWrapper wrapper = new EntityWrapper<UserEntity>();
        String phone = params.get("phone") != null ? (String) params.get("phone") : null;
        if(phone != null){
            wrapper.like("phone",phone);
        }
        Page<UserEntity> page = this.selectPage(
                new Query<UserEntity>(params).getPage(),
                wrapper
        );
        return new PageUtils(page);
    }

}
