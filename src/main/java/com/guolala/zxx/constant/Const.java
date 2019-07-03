package com.guolala.zxx.constant;

/**
 * @Author: pei.nie
 * @Date:2019/4/10
 * @Description:
 */
public interface Const {

    String TOKEN = "token";
    String SIGN = "sign";
    String REQUEST_ID = "requestId";
    String TIMESTAMP = "timestamp";

    String DEFAULT_CHARSET = "UTF-8";
    String CACHE_USER_KEY = "CACHE_USER";

    int ZERO = 0;
    int ONE = 1;

    String YES = "Y";
    String NO = "N";

    int SECOND_TIME = 1;
    int MINUTE_TIME = 60 * SECOND_TIME;
    int HOUR_TIME = 60 * MINUTE_TIME;
    int DAY_TIME = 24 * HOUR_TIME;
}
