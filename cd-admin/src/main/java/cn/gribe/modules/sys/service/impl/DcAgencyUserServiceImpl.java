package cn.gribe.modules.sys.service.impl;

import cn.gribe.common.utils.PageUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import cn.gribe.common.utils.Query;

import cn.gribe.modules.sys.dao.DcAgencyUserDao;
import cn.gribe.modules.sys.entity.DcAgencyUserEntity;
import cn.gribe.modules.sys.service.DcAgencyUserService;


@Service("dcAgencyUserService")
public class DcAgencyUserServiceImpl extends ServiceImpl<DcAgencyUserDao, DcAgencyUserEntity> implements DcAgencyUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DcAgencyUserEntity> page = this.selectPage(
                new Query<DcAgencyUserEntity>(params).getPage(),
                new EntityWrapper<DcAgencyUserEntity>()
        );

        return new PageUtils(page);
    }

}
