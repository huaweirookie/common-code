package com.zhunongyun.toalibaba.commoncode.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyControllerAdvice {

    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public ResponseVO serviceExceptionHandler(ServiceException exception) {
        ResponseVO response = ResponseVO.fail(exception.getCode(), exception.getMsg());
        return response;
    }
}
