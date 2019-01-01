/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package cn.gribe.interceptor;


import cn.gribe.annotation.Login;
import cn.gribe.common.utils.RedisUtils;
import cn.gribe.common.exception.RRException;
import cn.gribe.entity.TokenEntity;
import cn.gribe.service.TokenService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限(Token)验证
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:38
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisUtils redisUtils;

    public static final String USER_KEY = "userId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Login annotation;
        if(handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
        }else{
            return true;
        }
        //从header中获取token
        String token = request.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = request.getParameter("token");
        }
        if(annotation != null){
            //token为空
            if(StringUtils.isBlank(token)){
                RRException error = new RRException("token不能为空");
                error.setCode(401);
                throw error;
            }
            //查询token信息
            TokenEntity tokenEntity = redisUtils.get("tokenEntity",TokenEntity.class);
            if(tokenEntity == null){
                tokenEntity = tokenService.queryByToken(token);
            }
            if(tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()){
                RRException error = new RRException("token失效，请重新登录");
                error.setCode(401);
                throw error;
            }
            //设置userId到request里，后续根据userId，获取用户信息
            request.setAttribute(USER_KEY, tokenEntity.getUserId());
            return true;
        }else{
            //如有token，则不管是否注解了login
            if(StringUtils.isNotBlank(token)){
                //查询token信息
                TokenEntity tokenEntity = redisUtils.get("tokenEntity",TokenEntity.class);
                if(tokenEntity == null){
                    tokenEntity = tokenService.queryByToken(token);
                }
                if(tokenEntity != null && tokenEntity.getExpireTime().getTime() > System.currentTimeMillis()){
                    //设置userId到request里，后续根据userId，获取用户信息
                    request.setAttribute(USER_KEY, tokenEntity.getUserId());
                }
            }
        }
        return true;
    }
}
