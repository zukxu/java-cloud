package com.zukxu.aop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zukxu.aop.aspect.Perms;
import org.springframework.web.bind.annotation.*;

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
public class DemoAspectController {

    @GetMapping("/get")
    public String getLog(@RequestParam String content){
        return "输入参数：" + content;
    }

    @Perms
    @PostMapping("/permCheck")
    public JSONObject getLog(@RequestBody JSONObject request){
        return JSON.parseObject("{\"message\":\"SUCCESS\",\"code\":200}");
    }
}
