package com.zhunongyun.toalibaba.commoncode.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 */
@Component
public class RedisTokenUtils {

    /**
     * api token 过期时间
     */
    private static final Long API_TOKEN_TIMEOUT = 2L;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取Token 并将Token保存至redis
     *
     * @return
     */
    public String getToken() {
        String token = "token_" + UUID.randomUUID();
        redisTemplate.opsForValue().set(token, token, API_TOKEN_TIMEOUT, TimeUnit.MINUTES);
        return token;
    }

    /**
     * 判断Token是否存在 并且删除Token
     *
     * @param tokenKey
     * @return
     */
    public boolean findToken(String tokenKey) {
        String token = (String) redisTemplate.opsForValue().get(tokenKey);
        if (StringUtils.isBlank(token)) {
            return false;
        }
        // token 获取成功后 删除对应token
        redisTemplate.delete(tokenKey);
        return true;
    }
}