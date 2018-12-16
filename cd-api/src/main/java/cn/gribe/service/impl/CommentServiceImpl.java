package cn.gribe.service.impl;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.service.CommentService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import cn.gribe.dao.CommentDao;
import cn.gribe.entity.CommentEntity;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
//        EntityWrapper wrapper = new EntityWrapper<CommentEntity>();
//        Object storeId = params.get("storeId");
//        if(storeId != null ){
//            wrapper.eq("store_id",storeId);
//        }
//        Object postId = params.get("postId");
//        if(postId != null){
//            wrapper.eq("post_id",postId);
//        }
//        Page<CommentEntity> page = this.selectPage(
//                new Query<CommentEntity>(params).getPage(),
//                wrapper
//        );
//        Integer page = params.get("page");
        Page<CommentEntity> page = new Page<>();// 当前页，总条数 构造 page 对象
        Object post_id = params.get("postId");
        Integer postId = post_id == null ? null : (Integer)post_id;
        Object store_id = params.get("storeId");
        Integer storeId = store_id == null ? null : (Integer)store_id;
        String name = params.get("name") == null ? null : (String) params.get("name");
        String userId = params.get("userId") == null ? null : (String) params.get("userId");
        page.setRecords(this.baseMapper.selectPage(String.valueOf(postId),String.valueOf(storeId),name,userId));
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByUserId(Integer userId) {
        Page<CommentEntity> page = new Page<>();// 当前页，总条数 构造 page 对象
        page.setRecords(this.baseMapper.selectPageByUserId(userId));
        return new PageUtils(page);
    }

}
