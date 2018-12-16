package cn.gribe.service;

import cn.gribe.common.utils.PageUtils;
import cn.gribe.entity.TokenEntity;
import com.baomidou.mybatisplus.service.IService;
import cn.gribe.entity.UserEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-06 16:49:57
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    TokenEntity login(String phone, String password);

    UserEntity queryByMobile(String mobile);
}

