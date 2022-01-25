package com.zhunongyun.toalibaba.commoncode.enums;

/**
 * 响应码枚举类
 * @author oscar
 */
public enum ResponseCode {
    SUCCESS("000000", "成功"),
    // 通用模块 1xxxx
    ILLEGAL_ARGUMENT("100000", "参数不合法"),
    REPETITIVE_OPERATION("100001", "请勿重复操作"),
    SERVICE_ERROR("200000", "服务器发生异常"),
    ;

    private String code;
    private String msg;

    ResponseCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}