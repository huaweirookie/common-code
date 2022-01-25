package com.zhunongyun.toalibaba.commoncode.service;

import com.zhunongyun.toalibaba.commoncode.common.ResponseVO;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {
    public ResponseVO createToken();
    public ResponseVO checkToken(HttpServletRequest request);
}
