package cn.gribe.modules.business.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;
import cn.gribe.entity.OrderEntity;
import cn.gribe.modules.business.dao.CdPostDao;
import org.apache.commons.lang.StringUtils;
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
        Page<PostEntity> page = new Query<PostEntity>(params).getPage();// 当前页，总条数 构造 page 对象
        page.setRecords(this.baseMapper.selectPage());
        return new PageUtils(page);
    }

}
