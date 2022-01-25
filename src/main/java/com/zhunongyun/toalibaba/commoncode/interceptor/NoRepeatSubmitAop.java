package com.zhunongyun.toalibaba.commoncode.interceptor;

import com.zhunongyun.toalibaba.commoncode.annotation.ApiRepeatSubmit;
import com.zhunongyun.toalibaba.commoncode.annotation.ApiToken;
import com.zhunongyun.toalibaba.commoncode.common.ResponseVO;
import com.zhunongyun.toalibaba.commoncode.configuration.RedisTokenUtils;
import com.zhunongyun.toalibaba.commoncode.enums.ConstantUtils;
import com.zhunongyun.toalibaba.commoncode.enums.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 重复提交表单拦截器
 * @author oscar
 */
@Aspect
@Component
@Slf4j
public class NoRepeatSubmitAop {

    /**
     * 表单提交 token 字段名
     */
    private static final String FORM_TOKEN_NAME = "formToken";

    @Autowired
    private RedisTokenUtils redisTokenUtils;

    /**
     * 将token放入请求
     *
     * @param pjp
     * @param nrs
     */
    @Before("execution(* com.zhunongyun.toalibaba.commoncode.controller.*Controller.*(..)) && @annotation(nrs)")
    public void before(JoinPoint pjp, ApiToken nrs) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == attributes) {
            log.error("请求表单提交token,获取 attributes 为空");
            return;
        }
        HttpServletResponse response = attributes.getResponse();

        if (null == response) {
            log.error("请求表单提交token,获取 response 为空");
            return;
        }
        response.setHeader(FORM_TOKEN_NAME, redisTokenUtils.getToken());
    }


    /**
     * 拦截带有重复请求的注解的方法
     *
     * @param pjp
     * @param nrs
     */
    @Around("execution(* com.zhunongyun.toalibaba.commoncode.controller.*Controller.*(..)) && @annotation(nrs)")
    public Object arround(ProceedingJoinPoint pjp, ApiRepeatSubmit nrs) {

        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (null == attributes) {
                log.error("检查表单提交的 token,获取 attributes 为空");
                return ResponseVO.fail(ResponseCode.SERVICE_ERROR, "检查表单提交的 token,获取 attributes 为空");
            }
            HttpServletRequest request = attributes.getRequest();

            String token = null;
            if (nrs.value() == ConstantUtils.BOOD) {
                //从 参数 中取Token
                token = (String) request.getAttribute(FORM_TOKEN_NAME);
            } else if (nrs.value() == ConstantUtils.HEAD) {
                //从 请求头 中取Token
                token = request.getHeader(FORM_TOKEN_NAME);
            }

            if (StringUtils.isEmpty(token)) {
                log.error("获取token失败");
                return ResponseVO.fail(ResponseCode.REPETITIVE_OPERATION, "获取token失败");
            }
            if (!redisTokenUtils.findToken(token)) {
                log.error("重复提交");
                return ResponseVO.fail(ResponseCode.REPETITIVE_OPERATION, "重复提交");
            }
            log.error("正常提交表单");

            return pjp.proceed();
        } catch (Throwable e) {
            log.error("验证重复提交时出现未知异常:{}", e);
            return ResponseVO.fail(ResponseCode.SERVICE_ERROR, "验证重复提交时出现未知异常");
        }
    }
}