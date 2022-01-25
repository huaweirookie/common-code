package com.zhunongyun.toalibaba.commoncode.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceException extends RuntimeException{
    private String code;
    private String msg;
}
