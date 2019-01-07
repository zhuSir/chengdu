package cn.gribe.modules.business.service.impl;

import cn.gribe.entity.CommentEntity;
import cn.gribe.entity.OrderEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;

import cn.gribe.modules.business.dao.CdCommentDao;
import cn.gribe.modules.business.service.CdCommentService;


@Service("cdCommentService")
public class CdCommentServiceImpl extends ServiceImpl<CdCommentDao, CommentEntity> implements CdCommentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
//        EntityWrapper wrapper = new EntityWrapper<CommentEntity>();
//        wrapper.orderBy("create_time",false);
//        Integer storeId = params.get("storeId") != null ? (Integer) params.get("storeId") : null;
//        if(storeId != null){
//            wrapper.eq("store_id",storeId);
//        }
//        Page<CommentEntity> page = this.selectPage(
//                new Query<CommentEntity>(params).getPage(),
//                wrapper
//        );
//        return new PageUtils(page);

        Page<CommentEntity> page = new Query<CommentEntity>(params).getPage();// 当前页，总条数 构造 page 对象
        String content = params.get("content") != null  ? (String) params.get("content") : null;
        Integer storeId = params.get("storeId") != null ? (Integer) params.get("storeId") : null;
        page.setRecords(this.baseMapper.selectPage(page,content,storeId));
        return new PageUtils(page);
    }

}
