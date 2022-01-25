package com.zhunongyun.toalibaba.commoncode.annotation;


import com.zhunongyun.toalibaba.commoncode.enums.ConstantUtils;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防止重复提交注解
 * @author oscar
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiRepeatSubmit {
    ConstantUtils value();
}