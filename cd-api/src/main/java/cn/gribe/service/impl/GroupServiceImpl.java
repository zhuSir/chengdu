package cn.gribe.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.entity.GroupEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import cn.gribe.common.utils.Query;

import cn.gribe.dao.GroupDao;
import cn.gribe.service.GroupService;


@Service("cdGroupService")
public class GroupServiceImpl extends ServiceImpl<GroupDao, GroupEntity> implements GroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper wrapper =  new EntityWrapper<GroupEntity>();
        Object name = params.get("name");
        if(name != null){
            wrapper.like("name","%"+name+"%");
        }
        Page<GroupEntity> page = this.selectPage(
                new Query<GroupEntity>(params).getPage(),
                wrapper
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByUserId(Integer userId) {
        Page<GroupEntity> page = new Page<>();// 当前页，总条数 构造 page 对象
        page.setRecords(this.baseMapper.selectPageByUserId(userId));
        return new PageUtils(page);
    }

}
