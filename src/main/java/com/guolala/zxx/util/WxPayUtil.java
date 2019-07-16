package com.guolala.zxx.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class WxPayUtil {

    /**
     * 生成签名
     *
     * @param text
     * @param key
     * @return
     */
    public static String sign(String text, String key) {
        text = text + "&key=" + key;
        byte[] bytes = null;
        try {
            bytes = text.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return DigestUtils.md5Hex(bytes);
    }


    /**
     * 验证签名
     *
     * @param text 需要签名的字符串
     * @param sign 签名结果
     * @param key  密钥
     * @return
     */
    public static boolean verifySign(String text, String sign, String key) {
        text = text + key;
        byte[] bytes = null;
        try {
            bytes = text.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String signature = DigestUtils.md5Hex(bytes);
        return signature.equals(sign);
    }


    /**
     * 把对象中的所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> String beanToSortString(T bean) {
        Map<String, String> params = BeanUtil.beanToMap(bean);
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }


    /**
     * 除去数组中的空值和签名参数
     *
     * @param bean
     * @param <T>
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static <T> Map<String, String> filtParam(T bean) {
        Map<String, String> params = BeanUtil.beanToMap(bean);
        Map<String, String> map = new HashMap<>();
        if (params == null || params.size() <= 0) {
            return map;
        }
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (value == null
                    || value.equals("")
                    || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            map.put(key, value);
        }
        return map;
    }
}
