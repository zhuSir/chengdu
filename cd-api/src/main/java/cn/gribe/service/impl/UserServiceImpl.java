package cn.gribe.service.impl;

import cn.gribe.common.utils.PageUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import cn.gribe.common.exception.RRException;
import cn.gribe.common.utils.Query;
import cn.gribe.common.utils.Utils;
import cn.gribe.common.validator.Assert;
import cn.gribe.dao.CdUserDao;
import cn.gribe.entity.TokenEntity;
import cn.gribe.entity.UserEntity;
import cn.gribe.service.TokenService;
import cn.gribe.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;


@Service("UserService")
public class UserServiceImpl extends ServiceImpl<CdUserDao, UserEntity> implements UserService {

    @Autowired
    private TokenService tokenService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<UserEntity> page = this.selectPage(
                new Query<UserEntity>(params).getPage(),
                new EntityWrapper<UserEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public UserEntity queryByMobile(String mobile) {
        Assert.state(StringUtils.isEmpty(mobile),"手机号错误");
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("mobile",mobile);
        return selectOne(wrapper);
    }

    @Override
    public TokenEntity login(String phone, String password) {
        UserEntity user = queryByMobile(phone);
        Assert.isNull(user, "手机号或密码错误");
        //密码错误
        if(!user.getPassword().equals(DigestUtils.sha256Hex(password))){
            throw new RRException("手机号或密码错误");
        }
        Assert.state(user.getState() == 1,"该用户已被禁用");
        //更新用户登录ip和登陆时间
        user.setLastLoginIp(Utils.getRemoteHost());
        user.setLastLoginTime(new Date());
        updateById(user);
        //获取登录token
        TokenEntity tokenEntity = tokenService.createToken(user.getId());
        return tokenEntity;
    }

}
