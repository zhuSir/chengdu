package cn.gribe.service.impl;

import cn.gribe.common.utils.CommonUtils;
import cn.gribe.common.utils.PageUtils;
import cn.gribe.common.utils.Query;
import cn.gribe.common.utils.oss.OSSFactory;
import cn.gribe.entity.UserEntity;
import cn.gribe.service.CommentService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import cn.gribe.dao.CommentDao;
import cn.gribe.entity.CommentEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService {

    private static Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CommentEntity> page = new Query<CommentEntity>(params).getPage();// 当前页，总条数 构造 page 对象
        Object postId = params.get("postId") == null ? null : params.get("postId");
        Object storeId = params.get("storeId") == null ? null : params.get("storeId");
        Object name = params.get("name") == null ? null : params.get("name");
        Object userId = params.get("userId") == null ? null : params.get("userId");
        List<CommentEntity> res = this.baseMapper.selectPage(page,String.valueOf(postId),storeId,name,userId);
        if(res != null && res.size() > 0){
            //迭代遍历子数据
            res = querySubComment(res,100);
        }
        page.setRecords(res);
        return new PageUtils(page);
    }

    /**
     * 帖子评论列表
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPostCommentPage(Map<String, Object> params) {
        Page<CommentEntity> page = new Query<CommentEntity>(params).getPage();// 当前页，总条数 构造 page 对象
        Object userId = params.get("userId") == null ? null : params.get("userId");
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.isNotNull("post_id");
        if(userId != null){
            wrapper.eq("user_id",userId);
        }
        List<CommentEntity> res = this.baseMapper.selectPage(page,wrapper);
        if(res != null && res.size() > 0){
            //迭代遍历子数据
            res = querySubComment(res,100);
        }
        page.setRecords(res);
        return new PageUtils(page);
    }

    /**
     * 迭代遍历
     * @param commentEntityList
     * @param val 最大遍历数，防止死循环
     * @return
     */
    public List<CommentEntity> querySubComment(List<CommentEntity> commentEntityList,int val){
        if(val < 1){
            logger.error("到达遍历最大数，评论树错误。");
            return commentEntityList;
        }
        val--;
        if(commentEntityList != null && commentEntityList.size() > 0){
            for(int i =0;i<commentEntityList.size();i++){
                CommentEntity commentEntity = commentEntityList.get(i);
                List<CommentEntity> subRes = this.baseMapper.selectPageByCommentId(commentEntity.getId());
                subRes = querySubComment(subRes,val);
                if (subRes != null){
                    commentEntity.setCommentEntityList(subRes);
                    commentEntityList.set(i,commentEntity);
                }
            }
        }
        return commentEntityList;
    }

    @Override
    public PageUtils queryPageByUserId(Map<String, Object> params,Integer userId) {
        Page<CommentEntity> page = new Query<CommentEntity>(params).getPage();// 当前页，总条数 构造 page 对象
        page.setRecords(this.baseMapper.selectPageByUserId(page,userId));
        return new PageUtils(page);
    }

    @Override
    public void save(MultipartFile[] files, CommentEntity comment, UserEntity user) throws IOException {
        //图片检测
        CommonUtils.validateImg(files);
        //图片内容
        CommonUtils.validateTxt(user.getUserName());
        StringBuffer urls = new StringBuffer();
        //上传文件
        if(files != null && files.length > 0){
            for(MultipartFile file : files){
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
                urls.append(url+",");
            }
            if(urls.length() > 0){
                urls.replace(urls.lastIndexOf(","),urls.length(),"");
            }
        }
        //上传文件
        comment.setImgs(urls.toString());
        comment.setUserId(user.getId());
        comment.setCreateTime(new Date());
        comment.setUpdateTime(new Date());
        comment.setStatus(CommentEntity.STATUS_DISABLE);//默认未生效
        this.baseMapper.insert(comment);
    }


}
