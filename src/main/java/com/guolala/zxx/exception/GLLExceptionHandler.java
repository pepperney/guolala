package com.guolala.zxx.exception;

import com.guolala.zxx.constant.SysCode;
import com.guolala.zxx.entity.CommonResp;
import com.guolala.zxx.util.GUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: pei.nie
 * @Date:2019/4/10
 * @Description:
 */
@ControllerAdvice
@Slf4j
public class GLLExceptionHandler {


    /**
     * 业务异常处理
     *
     * @param ex
     * @param request
     * @param response
     */
    @ExceptionHandler(GLLException.class)
    public void gllExceptionHandle(GLLException ex, HttpServletRequest request, HttpServletResponse response) {
        log.error("======>业务异常:code={},message={}", ex.getCode(), ex.getMsg(), ex);
        GUtil.returnErrorMap(CommonResp.fail(ex.getCode(), ex.getMessage()), request, response);
    }

    /**
     * 未知异常处理
     *
     * @param e
     * @param request
     * @param response
     */
    @ExceptionHandler(Exception.class)
    public void exceptionHandle(Exception e, HttpServletRequest request, HttpServletResponse response) {
        log.error("======>未知异常:message={}", e.getMessage(), e);
        GUtil.returnErrorMap(CommonResp.fail(SysCode.SYS_ERROR), request, response);
    }


}
