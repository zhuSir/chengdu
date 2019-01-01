package cn.gribe.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.validator.Assert;
import cn.gribe.dao.CollectDao;
import cn.gribe.service.CollectService;
import cn.gribe.service.CommentService;
import cn.gribe.service.GroupService;
import cn.gribe.service.PostService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.gribe.entity.CollectEntity;

import java.util.List;


@Service("cdCollectService")
public class CollectServiceImpl extends ServiceImpl<CollectDao, CollectEntity> implements CollectService {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private GroupService groupService;

    @Override
    public PageUtils queryPage(Integer userId, Integer type) {
        PageUtils res = null;
        switch (type){
            case 1:
                //评论
                res = commentService.queryPageByUserId(userId);
                break;
            case 2:
                //帖子
                res = postService.queryPageByUserId(userId);
                break;
            case 3:
                //小组
                res = groupService.queryPageByUserId(userId);
                break;
            default:
                //TODO 获取全部的收藏
                res = groupService.queryPageByUserId(userId);
                break;
        }
        return res;
    }

    @Override
    public CollectEntity selectByParams(CollectEntity collect) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("user_id",collect.getUserId());
        int storeId = collect.getStoreId();
        if(storeId > 0){
            wrapper.eq("store_id",storeId);
        }
        int commentId = collect.getCommentId();
        if(commentId > 0){
            wrapper.eq("comment_id",commentId);
        }
        int groupId = collect.getGroupId();
        if(groupId > 0){
            wrapper.eq("group_id",groupId);
        }
        int postId = collect.getPostId();
        if(postId > 0 ){
            wrapper.eq("post_id",postId);
        }
        List<CollectEntity> collects = this.selectList(wrapper);
        Assert.state(collects == null || collects.size() < 1,"收藏错误，请联系管理员","查询收藏错误，参数："+collect.toString());
        if(collects != null && collects.size() > 0){
            return collects.get(0);
        }
        return null;
    }

}
