package com.guolala.zxx.support;

import com.alibaba.fastjson.JSON;


import com.google.common.collect.Lists;
import com.guolala.zxx.config.AuthConfig;
import com.guolala.zxx.constant.Const;
import com.guolala.zxx.constant.RedisKey;
import com.guolala.zxx.constant.SysCode;
import com.guolala.zxx.entity.CommonResp;
import com.guolala.zxx.entity.vo.UserVo;
import com.guolala.zxx.exception.GLLException;
import com.guolala.zxx.util.GUtil;
import com.guolala.zxx.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.logging.LogException;
import org.slf4j.MDC;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: pei.nie
 * @Date:2019/4/12
 * @Description:
 */
@Slf4j
public class GFilter extends OncePerRequestFilter {


    private static final String MATCH_ALL = "/**";
    private static final String DELIMITER = "=";
    private static final Map<String, String> AUTH_MAP = new HashMap<>();
    private static final AntPathMatcher antMatcher = new AntPathMatcher();
    private static final List<String> NO_LOG_IN_PATHS = Lists.newArrayList("/file/v1");
    private static final List<String> NO_LOG_OUT_PATHS = Lists.newArrayList("/file/v1");
    private static final List<String> SKIP_PATHS = Lists.newArrayList("/file/v1/downloadLocal");


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AuthConfig authConfig;

    /**
     * 此方法在容器初始化的时候会被执行两次，疑似spring的bug
     * 参考 https://blog.csdn.net/f641385712/article/details/87793736
     */
    @Override
    protected void initFilterBean() {
        if (!CollectionUtils.isEmpty(AUTH_MAP)) return;
        for (String securityPattern : authConfig.getChains()) {
            String[] strs = StringUtils.split(securityPattern, DELIMITER);
            String pattern = strs[0];
            String checks = strs[1];
            if (isNotAntPattern(pattern)) {
                pattern = pattern.substring(0, pattern.length() - 3);
            }
            AUTH_MAP.put(pattern, checks);
        }
        log.info("init security maps : {}", JSON.toJSONString(AUTH_MAP));
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String url = getRequestPath(request);
        boolean isMatch = false;
        for (Map.Entry<String, String> entry : AUTH_MAP.entrySet()) {
            String pattern = entry.getKey();
            if (isNotAntPattern(pattern)) {
                assert !pattern.contains("*");
                isMatch = url.startsWith(pattern) && (url.length() == pattern.length() || url.charAt(pattern.length()) == '/');
            } else {
                isMatch = antMatcher.match(entry.getKey(), url);
            }
        }
        return !isMatch;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Long beginTime = System.currentTimeMillis();
        GRequestWrapper requestWrapper = new GRequestWrapper(request);
        GResponseWrapper responseWrapper = new GResponseWrapper(response);
        String requestId = GUtil.getKeyFromRequest(request, Const.REQUEST_ID);
        MDC.put(Const.REQUEST_ID, StringUtils.isEmpty(requestId) ? GUtil.getUUID() : requestId);
        String reqStr = IOUtils.toString(requestWrapper.getInputStream(), Const.DEFAULT_CHARSET);
        if (StringUtils.isEmpty(reqStr)) {
            reqStr = GUtil.getParamMapStr(request.getParameterMap());
        }
        String path = getRequestPath(request);
        CommonResp commonResp = null;
        logInput(path, requestId, reqStr);
        boolean isMatch = false;
        try {
            for (Map.Entry<String, String> entry : AUTH_MAP.entrySet()) {
                String pattern = entry.getKey();
                String checks = entry.getValue();
                if (isNotAntPattern(pattern)) {
                    assert !pattern.contains("*");
                    isMatch = path.startsWith(pattern) && (path.length() == pattern.length() || path.charAt(pattern.length()) == '/');
                } else {
                    isMatch = antMatcher.match(entry.getKey(), path);
                }
                if (isMatch) {
                    if (checks.contains(Const.SIGN)) {
                        validateSign(request);
                    }
                    if (checks.contains(Const.TOKEN)) {
                        validateToken(request, response);
                    }
                    break;
                }
            }
            if (SKIP_PATHS.contains(path)) {
                filterChain.doFilter(requestWrapper, response);
                return;
            }
            filterChain.doFilter(requestWrapper, responseWrapper);
            String respStr = responseWrapper.getContent();
            if (!StringUtils.isEmpty(respStr) && respStr.contains("code") && respStr.contains("msg")) {
                commonResp = JSON.parseObject(respStr, CommonResp.class);
            } else {
                Object data = null;
                if (StringUtils.isEmpty(respStr)) {
                    data = null;
                } else if (JSON.isValidArray(respStr)) {
                    data = JSON.parseArray(respStr);
                } else if (JSON.isValidObject(respStr)) {
                    data = JSON.parseObject(respStr);
                } else {
                    data = respStr;
                }
                commonResp = CommonResp.success(data);
            }
            respStr = JSON.toJSONString(commonResp);
            logOutPut(path, requestId, respStr, beginTime);
            response.getOutputStream().write(respStr.getBytes(StandardCharsets.UTF_8));
        } catch (GLLException e) {
            commonResp = CommonResp.fail(e.getCode(), e.getMessage());
            logException(path, requestId, JSON.toJSONString(commonResp), e);
            GUtil.returnErrorMap(commonResp, requestWrapper, response);
        } catch (Exception e) {
            commonResp = CommonResp.fail(SysCode.SYS_ERROR);
            logException(path, requestId, JSON.toJSONString(commonResp), e);
            GUtil.returnErrorMap(commonResp, requestWrapper, response);
        }
        MDC.remove(Const.REQUEST_ID);
    }

    /**
     * 验证签名
     *
     * @param request
     */
    private void validateSign(HttpServletRequest request) {
        String reqNo = GUtil.getKeyFromRequest(request, Const.REQUEST_ID);
        String sign = GUtil.getKeyFromRequest(request, Const.SIGN);
        if (StringUtils.isEmpty(reqNo) || StringUtils.isEmpty(sign)) {
            throw new GLLException(SysCode.SIGN_ERROR);
        }
        if (!sign.equals(GUtil.getSignStr(reqNo))) {
            throw new GLLException(SysCode.SIGN_ERROR);
        }
    }

    /**
     * 验证token
     *
     * @param request
     * @param response
     */
    private void validateToken(HttpServletRequest request, HttpServletResponse response) {
        String token = GUtil.getKeyFromRequest(request, Const.TOKEN);
        log.warn("the requestURI[{}} token is [{}]", getRequestPath(request), token);
        if (StringUtils.isEmpty(token)) {
            throw new GLLException(SysCode.TOKEN_ERROR);
        }
        response.addHeader(Const.TOKEN, token);
        String cacheUser = redisUtil.get(RedisKey.TOKEN.key + token);
        UserVo userVo = JSON.parseObject(cacheUser, UserVo.class);
        if (StringUtils.isEmpty(cacheUser) || null == userVo) {
            throw new GLLException(SysCode.TOKEN_EXPIRE);
        }
        request.setAttribute(Const.CACHE_USER_KEY, userVo);

    }


    /**
     * 获取请求路径
     *
     * @param request
     * @return
     */
    public static String getRequestPath(HttpServletRequest request) {
        String url = request.getServletPath();
        if (request.getPathInfo() != null) {
            url += request.getPathInfo();
        }
        return url;
    }

    /**
     * 不是ant风格的url
     *
     * @param pattern
     * @return
     */
    private static boolean isNotAntPattern(String pattern) {
        boolean flag = pattern.endsWith(MATCH_ALL) && pattern.indexOf('?') == -1 && pattern.indexOf("*") == pattern.length() - 2;
        return flag;
    }


    /**
     * 打印入参
     *
     * @param path
     * @param requestId
     * @param reqStr
     */
    private static void logInput(String path, String requestId, String reqStr) {
        if (NO_LOG_IN_PATHS.contains(path)) return;
        log.info("--> [{}]接口的入参={}，requestId={}", path, reqStr, requestId);
    }

    /**
     * 打印出参
     *
     * @param path
     * @param requestId
     * @param reqStr
     * @param respStr
     * @param beginTime
     */
    private static void logOutPut(String path, String requestId, String respStr, Long beginTime) {
        if (NO_LOG_OUT_PATHS.contains(path)) return;
        log.info("<-- [{}]接口的出参={},耗时[{}]ms,requestId={}\n\n", path, respStr, System.currentTimeMillis() - beginTime, requestId);
    }

    /**
     * 打印异常
     *
     * @param path
     * @param requestId
     * @param respStr
     */
    private static void logException(String path, String requestId, String respStr, Throwable e) {
        log.error("<-- [{}]接口的出参={},requestId={}\n\n", path, respStr, requestId, e);
    }

}
