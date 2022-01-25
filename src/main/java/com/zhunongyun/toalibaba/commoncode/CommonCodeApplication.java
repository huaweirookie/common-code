package com.zhunongyun.toalibaba.commoncode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhunongyun.toalibaba.commoncode.mapper")
public class CommonCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonCodeApplication.class, args);
    }

}
