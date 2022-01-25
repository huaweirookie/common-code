package com.zhunongyun.toalibaba.commoncode.mapper;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.zhunongyun.toalibaba.commoncode.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.isTrue(5 == userList.size(), "查询数据失败");
        userList.forEach(System.out::println);
    }

}