package com.zhunongyun.toalibaba.commoncode.controller;

import com.zhunongyun.toalibaba.commoncode.annotation.ApiRepeatSubmit;
import com.zhunongyun.toalibaba.commoncode.annotation.ApiToken;
import com.zhunongyun.toalibaba.commoncode.common.ResponseVO;
import com.zhunongyun.toalibaba.commoncode.enums.ConstantUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 解决表单重复提交
 * @author oscar
 */
@RestController
@RequestMapping("form")
public class FormRepeatSubmissionController {


    /**
     * 进入页面
     * @return
     */
    @ApiToken
    @GetMapping("index")
    public ResponseVO index(){
        return ResponseVO.success();
    }

    /**
     * 提交表单
     * @param data
     * @return
     */
    @ApiRepeatSubmit(ConstantUtils.HEAD)
    @PostMapping("add")
    public ResponseVO saveData(@RequestBody String data) {
        return ResponseVO.success(data);
    }
}