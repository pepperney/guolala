package com.guolala.zxx.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Jedis;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @Author: pei.nie
 * @Date:2019/4/10
 * @Description:
 */
@Component
public class RedisUtil {


    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * get value by key
     *
     * @param key
     * @return
     */
    public String get(String key) {
        if (!StringUtils.isEmpty(key)) {
            return stringRedisTemplate.opsForValue().get(key);
        }
        return null;
    }

    /**
     * set with an expire time
     *
     * @param key
     * @param expireTime
     * @param value
     */
    public void setex(String key, long expireTime, String value) {
        stringRedisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
    }

    /**
     * set key and value
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * Returns the remaining time to live of the key that has a timeout.
     *
     * @param key
     * @return
     */
    public long ttl(String key) {
        return stringRedisTemplate.getExpire(key) > 0 ? stringRedisTemplate.getExpire(key) : 0;
    }

    /**
     * del the key
     *
     * @param key
     */
    public void del(String key) {
        stringRedisTemplate.delete(key);

    }

    /**
     * set when the key isn't exist
     *
     * @param key
     * @param expireTime
     * @param value
     */
    public void setnx(String key, long expireTime, String value) {
        stringRedisTemplate.opsForValue().setIfAbsent(key, value);

    }

    /**
     * TODO
     * add lock
     *
     * @param jedis      Redis客户端
     * @param key        锁
     * @param requestId  请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     * @see http://mp.weixin.qq.com/s/qJK61ew0kCExvXrqb7-RSg
     */
    public boolean lock(Jedis jedis, String key, String requestId, int expireTime) {
        return false;
    }

    /**
     * TODO
     * release lock
     *
     * @param jedis     Redis客户端
     * @param key       锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean unLock(Jedis jedis, String key, String requestId) {

        return false;
    }

    public Long increment(String key) {
        return stringRedisTemplate.opsForValue().increment(key, 1);
    }

    public Long increment(String key, Long step) {
        return stringRedisTemplate.opsForValue().increment(key, step);
    }

    /**
     * let the key expire in exipreTime seconds
     *
     * @param key
     * @param exipreTime
     * @return
     */
    public Boolean expire(String key, int exipreTime) {
        return stringRedisTemplate.expire(key, exipreTime, TimeUnit.SECONDS);
    }
}
