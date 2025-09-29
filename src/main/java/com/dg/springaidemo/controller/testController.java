package com.dg.springaidemo.controller;

import com.dg.springaidemo.common.BaseResponse;
import com.dg.springaidemo.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class testController {

    /**
     * 健康检查
     */
    @GetMapping("/health")
    public String health() {
        return "hekko";
    }

}

