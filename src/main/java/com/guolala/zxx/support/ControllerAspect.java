package com.guolala.zxx.support;

import com.alibaba.fastjson.JSON;
import com.guolala.zxx.util.GUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: pei.nie
 * @Date:2019/4/17
 * @Description:
 */
@Slf4j
//@Aspect
//@Component
public class ControllerAspect {
    @Around("execution(* com.guolala.zxx.controller.*Controller.*(..))")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        Object respData;
        String className = joinPoint.getTarget().getClass().getSimpleName(); //获取类名(这里只切面了Controller类)
        String methodName = joinPoint.getSignature().getName();              //获取方法名
        String methodInfo = className + "." + methodName;                    //组织类名.方法名
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        log.info("{}被调用,入参为{}", request.getRequestURI(), GUtil.getParamMapStr(request.getParameterMap()));
        respData = joinPoint.proceed();
        String returnInfo;
        if (null != respData && respData.getClass().isAssignableFrom(ResponseEntity.class)) {
            returnInfo = JSON.toJSONString(((ResponseEntity) respData).getBody());
        } else {
            returnInfo = JSON.toJSONString(respData);
        }
        log.info("{}被调用,出参为{}", request.getRequestURI(), returnInfo);
        return respData;
    }


}
