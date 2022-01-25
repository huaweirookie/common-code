package com.zhunongyun.toalibaba.commoncode.controller;

import com.zhunongyun.toalibaba.commoncode.common.ResponseVO;
import com.zhunongyun.toalibaba.commoncode.service.TokenService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("token")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("create")
    public ResponseVO token(){
        return tokenService.createToken();
    }

    @PostMapping("checkToken")
    public ResponseVO checkToken(HttpServletRequest request){
        return tokenService.checkToken(request);
    }

    @RequestMapping(value = "test", method = RequestMethod.PATCH)
    public ResponseVO test(HttpServletRequest request){
        return tokenService.checkToken(request);
    }
}