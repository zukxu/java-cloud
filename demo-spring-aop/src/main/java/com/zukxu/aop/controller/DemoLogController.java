package com.zukxu.aop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-04-08 15:16
 */
@RequestMapping("/aop")
@RestController
public class DemoLogController {

    @GetMapping("/get")
    public String getLog(@RequestParam String content){
        return "输入参数：" + content;
    }
}
