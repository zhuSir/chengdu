package cn.gribe.service.impl;

import cn.gribe.service.CollectService;
import cn.gribe.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Autowired
    private CommentService commentService;

    @Autowired
    private CollectService collectService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<PostEntity> page = new Query<PostEntity>(params).getPage();// 当前页，总条数 构造 page 对象
        Object groupId = params.get("groupId");
        Object content = params.get("content");
        Object userId = params.get("userId");
        page.setRecords(this.baseMapper.selectPageByGroupId(page,groupId,content,userId));
        List<PostEntity> posts = page.getRecords();
        for(int i =0;i<posts.size();i++){
            PostEntity postEntity = posts.get(i);
            EntityWrapper wrapper = new EntityWrapper();
            wrapper.eq("post_id",postEntity.getId());
            int count = commentService.selectCount(wrapper);
            postEntity.setComments(count);
            posts.set(i,postEntity);
            postEntity.setCollected(false);
            if(userId != null){
                EntityWrapper collWrapper = new EntityWrapper();
                collWrapper.eq("user_id",userId);
                collWrapper.eq("post_id",postEntity.getId());
                int isExc = collectService.selectCount(collWrapper);
                if(isExc > 0){
                    postEntity.setCollected(true);
                }
            }
        }
        page.setRecords(posts);
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByUserId(Map<String, Object> params,Integer userId) {
        Page<PostEntity> page = new Query<PostEntity>(params).getPage();// 当前页，总条数 构造 page 对象
        page.setRecords(this.baseMapper.selectPageByUserId(page,userId));
        return new PageUtils(page);
    }

}
