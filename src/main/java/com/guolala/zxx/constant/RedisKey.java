package com.guolala.zxx.constant;

/**
 * @Author: pei.nie
 * @Date:2019/4/15
 * @Description:RedisKey的枚举，有效期的时间单位为秒
 */
public enum  RedisKey {

    TOKEN                       (RedisKey.PREFIX + "token_",  24 * Const.HOUR_TIME),

    USER                        (RedisKey.PREFIX + "user_", 5 * Const.MINUTE_TIME),

    USER_SHOP_CART              (RedisKey.PREFIX + "shopcart_", 30 * Const.MINUTE_TIME),

    ORDER_NO                    (RedisKey.PREFIX + "orderNo_", 10 * Const.MINUTE_TIME),

    USER_ADDRESS                (RedisKey.PREFIX + "user_address_", 7 * Const.DAY_TIME),

    WX_SESSION                  (RedisKey.PREFIX + "wx_session_", 24 * Const.HOUR_TIME),
    ;

    /** global redis key prefix */
    public static final String PREFIX = "gll_";

    public String key;

    public Integer expireTime;

    RedisKey(String key, Integer expireTime) {
        this.key = key;
        this.expireTime = expireTime;
    }


}
