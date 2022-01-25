package com.zhunongyun.toalibaba.commoncode.service.impl;

import com.zhunongyun.toalibaba.commoncode.common.ResponseVO;
import com.zhunongyun.toalibaba.commoncode.enums.ResponseCode;
import com.zhunongyun.toalibaba.commoncode.common.ServiceException;
import com.zhunongyun.toalibaba.commoncode.service.TokenService;
import com.zhunongyun.toalibaba.commoncode.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public ResponseVO createToken() {
        //生成uuid当作token
        String token = UUID.randomUUID().toString().replace("-", "");
        //将生成的token存入redis中
        redisUtils.set(token, token);
        //返回正确的结果信
        return ResponseVO.success();
    }

    @Override
    public ResponseVO checkToken(HttpServletRequest request) {
        //从请求头中获取token
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            throw new ServiceException(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getMsg());
        }
        //如果redis中不包含该token，说明token已经被删除了，抛出请求重复异常
        if (!redisUtils.hashKey(token)) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getCode(), ResponseCode.REPETITIVE_OPERATION.getMsg());
        }
        //删除token
        boolean del = redisUtils.del(token);
        //如果删除不成功（已经被其他请求删除），抛出请求重复异常
        if (!del) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getCode(), ResponseCode.REPETITIVE_OPERATION.getMsg());
        }
        return ResponseVO.success();
    }
}
