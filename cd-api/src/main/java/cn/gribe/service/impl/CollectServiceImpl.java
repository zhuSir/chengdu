package cn.gribe.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.dao.CollectDao;
import cn.gribe.service.CollectService;
import cn.gribe.service.CommentService;
import cn.gribe.service.GroupService;
import cn.gribe.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.gribe.entity.CollectEntity;


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

}
