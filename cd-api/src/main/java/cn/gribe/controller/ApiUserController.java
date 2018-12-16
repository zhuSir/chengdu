package cn.gribe.controller;

import cn.gribe.annotation.Login;
import cn.gribe.annotation.LoginUser;
import cn.gribe.common.utils.R;
import cn.gribe.common.utils.RedisUtils;
import cn.gribe.common.utils.SmsUtils;
import cn.gribe.common.utils.Utils;
import cn.gribe.common.utils.oss.OSSFactory;
import cn.gribe.service.ActivityService;
import cn.gribe.service.StoreService;
import cn.gribe.common.utils.*;
import cn.gribe.common.validator.Assert;
import cn.gribe.common.validator.ValidatorUtils;
import cn.gribe.entity.ActivityEntity;
import cn.gribe.entity.StoreEntity;
import cn.gribe.entity.TokenEntity;
import cn.gribe.entity.UserEntity;
import cn.gribe.service.TokenService;
import cn.gribe.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 用户接口
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户接口")
public class ApiUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private StoreService storeService;

    @RequestMapping("/index")
    @ApiOperation("首页")
    public R index(String lat, String lon, @LoginUser UserEntity user) {
        //获取数据
        List<ActivityEntity> activityList = activityService.queryAllActivity();
        List<StoreEntity> storeList = storeService.queryByLocation(lat, lon, 1, 10);
        R res = R.ok().put("activity", activityList);
        res.put("store", storeList);
        return res;
    }

    @RequestMapping("/login")
    @ApiOperation("登录")
    public R login(UserEntity user) {
        //参数校验
        ValidatorUtils.validateEntity(user);
        //登录
        TokenEntity token = userService.login(user.getPhone(), user.getPassword());
        //设置验证码，过期时间60秒
        Long time = token.getExpireTime().getTime() - System.currentTimeMillis();
        redisUtils.set(token.getToken(), token, time);
        Map<String, Object> map = new HashMap<>(2);
        map.put("token", token.getToken());
        map.put("expire", token.getExpireTime().getTime() - System.currentTimeMillis());
        return R.ok().put("res", map);
    }

    @Login
    @RequestMapping("/logout")
    @ApiOperation("登出")
    public R logout(String token) {
        TokenEntity tokenEntity = tokenService.queryByToken(token);
        tokenService.expireToken(tokenEntity.getUserId());
        TokenEntity tokenEntity1 = redisUtils.get("tokenEntity", TokenEntity.class);
        if (tokenEntity1 != null) {
            //登出
            redisUtils.set(token, tokenEntity, 0);
        }
        return R.ok();
    }

    /**
     * 发送验证码
     * @param phone
     * @return
     */
    @RequestMapping("/send/code")
    public R sendCode(String phone) {
        Assert.isNull(phone, "手机号错误，请重新输入");
        //获取验证码
        String times = redisUtils.get("times_" + phone);
        Random random = new Random();
        int nextInt = random.nextInt(9999);
        String authCode = String.format("%04d", nextInt);
        System.out.println(" 验证码：" + authCode);
        if (times == null) {
            //发送短信
            int res = SmsUtils.sendSms(phone, authCode);
            if (res == 1) {
                //设置验证码，过期时间180秒
                redisUtils.set("validate_" + phone, authCode, 180);
                //设置请求次数，过期时间3小时(每个手机号每天只能发6次)
                redisUtils.set("times_" + phone, 1, 3 * 60 * 60);
                return R.ok().put("message", "验证码已发送请耐心等待");
            }
        } else {
            //每天发6次
            Integer sendTimes = Integer.valueOf(times);
            Assert.state(sendTimes >= 6, "该手机号已发送超过当天限制，请稍后再发");
            String validate = redisUtils.get("validate_" + phone);
            Assert.state(validate != null, "验证码已发送请耐心等待");
            //发送短信
            int res = SmsUtils.sendSms(phone, authCode);
            if (res == 1) {
                redisUtils.set("validate_" + phone, authCode, 180);
                sendTimes++;
                redisUtils.set("times_" + phone, sendTimes);
                return R.ok().put("message", "验证码已发送请耐心等待");
            }
        }
        return R.ok().put("message", "验证码发送失败请重新请求");
    }

    /**
     * 注册接口
     * @param phone
     * @param password
     * @param validateCode
     * @return
     */
    @RequestMapping("/register")
    public R register(String phone, String password, String validateCode) {
        if(!Utils.validateSuperCode(validateCode)){
            String code = redisUtils.get("validate_" + phone);
            Assert.isNull(code, "请重试发送验证码");
            Assert.state(!code.equals(validateCode), "验证码错误，请重新填写");
        }
        UserEntity user = userService.queryByMobile(phone);
        Assert.isNotNull(user, "该手机号已被注册");
        //注册
        user = new UserEntity(phone, password);
        userService.insert(user);
        return R.ok("注册成功");
    }

    /**
     * 修改密码成功
     * @param phone
     * @param password
     * @param validateCode
     * @return
     */
    @RequestMapping("/reset")
    public R reSetPwd(String phone, String password, String validateCode) {
        if(!Utils.validateSuperCode(validateCode)){
            String code = redisUtils.get("validate_" + phone);
            Assert.isNull(code, "请重试发送验证码");
            Assert.state(!code.equals(validateCode), "验证码错误，请重新填写");
        }
        UserEntity user = userService.queryByMobile(phone);
        Assert.isNull(user, "该手机号未注册");
        //注册
        user.setRealPassword(password);
        user.setPassword(DigestUtils.sha256Hex(password));
        userService.updateById(user);
        return R.ok("修改密码成功");
    }

    /**
     * 上传头像
     * @param nickName
     * @param file
     * @param user
     * @return
     * @throws IOException
     */
    @Login
    @RequestMapping("/set")
    public R userSet(@RequestParam(required = false) String nickName,
                     @RequestParam(value = "file", required = false) MultipartFile file,
                     @LoginUser UserEntity user) throws IOException {
        //TODO 图片检测（是否涉黄）
        if (file != null) {
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
            user.setHeadImg(url);
        }
        user.setUserName(nickName);
        userService.insertOrUpdate(user);
        return R.ok("设置成功");
    }

    /**
     * 获取用户信息
     * @param user
     * @return
     */
    @Login
    @RequestMapping("/info")
    @ResponseBody
    public UserEntity info(@LoginUser UserEntity user) {
        Assert.state(user.isDisable(),"用户被禁用");
        user = user.setEmpty();
        return user;
    }

}
