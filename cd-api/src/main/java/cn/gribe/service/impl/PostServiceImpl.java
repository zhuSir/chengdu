package cn.gribe.service.impl;

import cn.gribe.entity.OrderEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;

import cn.gribe.dao.PostDao;
import cn.gribe.entity.PostEntity;
import cn.gribe.service.PostService;


@Service("cdPostService")
public class PostServiceImpl extends ServiceImpl<PostDao, PostEntity> implements PostService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<PostEntity> page = new Query<PostEntity>(params).getPage();// 当前页，总条数 构造 page 对象
        String groupId = params.get("groupId") == null ? null : (String) params.get("groupId");
        page.setRecords(this.baseMapper.selectPageByGroupId(groupId));
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByUserId(Integer userId) {
        Page<PostEntity> page = new Page<>();// 当前页，总条数 构造 page 对象
        page.setRecords(this.baseMapper.selectPageByUserId(userId));
        return new PageUtils(page);
    }

}
