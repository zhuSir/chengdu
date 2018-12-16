package cn.gribe.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;

import cn.gribe.modules.sys.dao.DcUserDao;
import cn.gribe.modules.sys.entity.DcUserEntity;
import cn.gribe.modules.sys.service.DcUserService;


@Service("dcUserService")
public class DcUserServiceImpl extends ServiceImpl<DcUserDao, DcUserEntity> implements DcUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DcUserEntity> page = this.selectPage(
                new Query<DcUserEntity>(params).getPage(),
                new EntityWrapper<DcUserEntity>()
        );

        return new PageUtils(page);
    }

}
