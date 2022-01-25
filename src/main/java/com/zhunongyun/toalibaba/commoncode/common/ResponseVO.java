package com.zhunongyun.toalibaba.commoncode.common;

import com.zhunongyun.toalibaba.commoncode.enums.ResponseCode;
import lombok.Data;

@Data
public class ResponseVO {
    private String code;
    private String msg;
    private Object data;

    private ResponseVO() {

    }

    private ResponseVO(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ResponseVO(String code, String msg) {
        new ResponseVO(code, msg, null);
    }

    public static ResponseVO success() {
        return new ResponseVO(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg());
    }

    public static ResponseVO success(Object data) {
        return new ResponseVO(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), data);
    }

    public static ResponseVO fail(ResponseCode responseCode, Object data) {
        return new ResponseVO(responseCode.getCode(), responseCode.getMsg(), data);
    }

    public static ResponseVO fail(String code, String msg, Object data) {
        return new ResponseVO(code, msg, data);
    }

    public static ResponseVO fail(String code, String msg) {
        return fail(code, msg, null);
    }
}