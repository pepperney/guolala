package com.guolala.zxx.util;


import com.alibaba.fastjson.JSON;
import com.guolala.zxx.entity.CommonResp;
import com.guolala.zxx.support.GFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.Map.Entry;

/**
 * @Author: pei.nie
 * @Date:2019/4/15
 * @Description:
 */
@Slf4j
public class GUtil {

    private static final String SPLITER = "-";
    private static final String EMP_STR = "";
    private static final String TOKEN_PREFIX = "GLL";
    private static final String SIGN_PREFIX = "GLL";
    private static final String PASSWORD_SALT = "%npZXX~";
    private static final String DEFAULT_PASSWORD = "~zxxNP%";
    private static final String SOURCE_NUMBER = "0123456789";
    private static final String SIMPLE_DATE_PATTERN = "yyyyMMddHHmmSSS";
    private static final MappingJackson2JsonView JSONVIEW = new MappingJackson2JsonView();

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll(SPLITER, EMP_STR);
    }

    /**
     * 生成token
     *
     * @param userId
     * @return
     */
    public static String getTokenStr(Integer userId) {
        String token = TOKEN_PREFIX + userId + getUUID();
        return token;
    }

    /**
     * 生成sign
     *
     * @param reqNo
     * @return
     */
    public static String getSignStr(String reqNo) {
        char[] chars = reqNo.toCharArray();
        Arrays.sort(chars);
        reqNo = new String(chars);
        return MD5Util.md5(SIGN_PREFIX + reqNo);
    }

    /**
     * 生成密码
     *
     * @param password
     * @return
     */
    public static String getPassWord(String password) {
        return MD5Util.md5(password + PASSWORD_SALT);
    }

    /**
     * 生成订单号
     *
     * @return
     */
    public static String getOrderNo() {
        return DateUtil.getFormatDate(new Date(), SIMPLE_DATE_PATTERN) + getRandomNumberStr(5);
    }


    /**
     * 从请求中获取指定参数
     *
     * @param request
     * @param key
     * @return
     */
    public static String getKeyFromRequest(HttpServletRequest request, String key) {
        if (request.getParameterMap().containsKey(key)) {
            return request.getParameter(key);
        } else {
            return request.getHeader(key);
        }
    }


    /**
     * 异常返回
     *
     * @param commonResp
     * @param request
     * @param response
     */
    public static void returnErrorMap(CommonResp commonResp, HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> model = new HashMap<>();
            model.put("code", commonResp.getCode());
            model.put("msg", commonResp.getMsg());
            JSONVIEW.render(model, request, response);
        } catch (Exception e) {
            log.warn("jackson to json error", e);
        }
    }


    public static String getParamMapStr(Map<String, String[]> map) {
        Map<String, String> paramMap = new HashMap<>();
        for (Entry<String, String[]> param : map.entrySet()) {
            StringBuffer paramVal = new StringBuffer();
            if (param.getValue() != null && param.getValue().length == 1) {
                paramVal.append(param.getValue()[0]);
            } else if (param.getValue() != null && param.getValue().length > 1) {
                for (String paramItem : param.getValue()) {
                    paramVal.append(paramItem).append(",");
                }
                paramVal.delete(paramVal.length() - 1, paramVal.length() - 1);
            }
            paramMap.put(param.getKey(), paramVal.toString());
        }
        return JSON.toJSONString(paramMap);
    }


    /**
     * 获取由0-9构成的随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomNumberStr(int length) {
        char[] chars = SOURCE_NUMBER.toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(SOURCE_NUMBER.charAt(random.nextInt(SOURCE_NUMBER.length())));
        }
        return sb.toString();
    }

    /**
     * 使用默认密钥通过aes算法加密字符串
     *
     * @param str
     * @return
     */
    public static String encrytStr(String str) {
        return AESUtil.encryptData(str, DEFAULT_PASSWORD);
    }
}
