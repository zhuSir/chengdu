/*********************************************************************************
 *    Copyright (c) 厦门科融联合信息科技有限公司
 *
 *    Package:     com.kerong.creditreport.core.aspect
 *    Filename:    WebLogAspect
 *    Description:
 *    Copyright:   Copyright (c) ©2018
 *    Company:      厦门科融联合信息科技有限公司
 *    @author :     hexp
 *    @version :    1.0
 *    Create at:   2018/06/03  15:19
 *
 ********************************************************************************/
package cn.gribe.aspect;

import cn.gribe.common.utils.R;
import cn.gribe.common.utils.Utils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * <p>Web层的日志切面</P>
 *
 * @author : hexp
 * @version v1.1.0
 * @ClassName WebLogAspect
 * @date: 2018/06/03 15:19
 */
@Aspect
@Component
public class WebLogAspect {
    private static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
    /**
     * <p>[功能描述: 切入点]</P>
     *
     * @param
     * @return void
     * @author hexp
     * @date 2018年06月03日 15:22:33
     */
    @Pointcut("execution(public * cn.gribe.controller.*.*(..))")
    public void webLog() {
    }
    /**
     * <p>[功能描述: 切入点开始处切入内容打印请求信息]</P>
     *
     * @param joinPoint
     * @return void
     * @author hexp
     * @date 2018年06月03日 15:23:15
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint){
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes !=null){
            HttpServletRequest request = attributes.getRequest();
            // 记录下请求内容
            logger.info("请求地址 : " + request.getRequestURL().toString());
            logger.info("请求方法 : " + request.getMethod());
            String ip = Utils.getRemoteHost();
            logger.info("IP地址 : " + ip);
            logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            logger.info("参数 : " + Arrays.toString(joinPoint.getArgs()));
        }
    }
    /**
     * <p>[功能描述: 环绕通知]</P>
     *
     * @param joinPoint
     * @author hexp
     * @date  2018年06月03日 22:48:50
     * @return java.lang.Object
     */
    @Around("webLog()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        Object result = null;
        long timeConsuming = 0;
        try {
            result = joinPoint.proceed();
            timeConsuming = System.currentTimeMillis() - start;
            logger.info("返回数据:{} 耗时:{}ms", JSONObject.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue), timeConsuming);
            return result;
        } catch (Exception e) {
            logger.error("切面通知失败", e);
            R data = R.error(e.getMessage());
            logger.info("返回数据:{} 耗时:{}ms", JSONObject.toJSONStringWithDateFormat(data, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue), timeConsuming);
            return data;
        }
    }

    /**
     * <p>[功能描述: 输出model数据]</p>
     *
     * @param joinPoint
     * @author hexp
     * @date 2018年07月06日 11:48:54
     * @return java.lang.Object
     */
    @After("webLog()")
    public Object doafter(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        for (Object o :args){
            if (o !=null && o.getClass().toString().contains("BindingAwareModelMap")){
                logger.info("\t\n返回model数据:"+ JSON.toJSONString(o));
            }
        }
        return null;
    }
}
