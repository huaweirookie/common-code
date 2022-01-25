package com.zhunongyun.toalibaba.commoncode.utils;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.zhunongyun.toalibaba.commoncode.entity.User;
import com.zhunongyun.toalibaba.commoncode.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class RedisUtilsTest {

    @Resource
    RedisUtils redisUtils;

    @Test
    public void testAdd() {
        Long time = System.currentTimeMillis();
        String key = "addRedisKey" + time;
        boolean result = redisUtils.set(key, time, 10);
        Assert.isTrue(result, "redis保存数据失败");
        Long data = (Long) redisUtils.get("addRedisKey" + time);
        Assert.isTrue(time.equals(data), "redis查询数据匹配失败");
    }
}