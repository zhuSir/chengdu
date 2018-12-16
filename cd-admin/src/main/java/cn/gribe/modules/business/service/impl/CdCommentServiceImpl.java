package cn.gribe.modules.business.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;

import cn.gribe.modules.business.dao.CdCommentDao;
import cn.gribe.modules.business.entity.CdCommentEntity;
import cn.gribe.modules.business.service.CdCommentService;


@Service("cdCommentService")
public class CdCommentServiceImpl extends ServiceImpl<CdCommentDao, CdCommentEntity> implements CdCommentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CdCommentEntity> page = this.selectPage(
                new Query<CdCommentEntity>(params).getPage(),
                new EntityWrapper<CdCommentEntity>()
        );

        return new PageUtils(page);
    }

}
