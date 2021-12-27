package com.zukxu.map.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 页面控制器
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/4/14 0014 17:49
 */
@Controller
@RequestMapping
public class StaticController {
    @GetMapping(path = {"/", "index"})
    public String index() {
        return "index";
    }

    @GetMapping(path = {"/china"})
    public String china() {
        return "china";
    }

    @GetMapping(path = {"/world"})
    public String world() {
        return "world";
    }
}
