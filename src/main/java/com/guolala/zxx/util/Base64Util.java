package com.guolala.zxx.util;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * @Author: pei.nie
 * @Date:2019/7/3
 * @Description:
 */
public class Base64Util {
    private static final Logger logger = LoggerFactory.getLogger(Base64Util.class);
    private static Base64.Encoder encoder = Base64.getEncoder();
    private static Base64.Decoder decoder = Base64.getDecoder();

    public static String encode(byte[] bytes) {
        return encoder.encodeToString(bytes);
    }

    public static byte[] decode(String str) {
        str = str.replaceAll("\r\n", "");
        return decoder.decode(str);
    }

    public static String fileToBase64Str(InputStream is) {
        try {
            byte[] bytes = IOUtils.toByteArray(is);
            return encode(bytes);
        } catch (IOException e) {
            logger.warn("IOException occured");
            return null;
        }
    }
}
