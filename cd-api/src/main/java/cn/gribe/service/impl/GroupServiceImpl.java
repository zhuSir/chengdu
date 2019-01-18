package cn.gribe.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.entity.GroupEntity;
import cn.gribe.service.CollectService;
//import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import cn.gribe.common.utils.Query;

import cn.gribe.dao.GroupDao;
import cn.gribe.service.GroupService;


@Service("cdGroupService")
public class GroupServiceImpl extends ServiceImpl<GroupDao, GroupEntity> implements GroupService {

    @Autowired
    private CollectService collectService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper wrapper =  new EntityWrapper<GroupEntity>();
        Object name = params.get("name");
        if(name != null){
            wrapper.like("name","%"+name+"%");
        }
        wrapper.orderBy("rand()",false);
        Page<GroupEntity> page = this.selectPage(
                new Query<GroupEntity>(params).getPage(),
                wrapper
        );
        List<GroupEntity> groups = page.getRecords();
        for(int i =0;i<groups.size();i++){
            GroupEntity groupEntity = groups.get(i);
            EntityWrapper groupWrapper = new EntityWrapper();
            groupWrapper.eq("group_id",groupEntity.getId());
            int count = collectService.selectCount(groupWrapper);
            groupEntity.setCollectNum(count);
            groups.set(i,groupEntity);
        }
        page.setRecords(groups);
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByUserId(Map<String, Object> params,Integer userId) {
        Page<GroupEntity> page = new Query<GroupEntity>(params).getPage();// 当前页，总条数 构造 page 对象
        page.setRecords(this.baseMapper.selectPageByUserId(page,userId));
        return new PageUtils(page);
    }

}
