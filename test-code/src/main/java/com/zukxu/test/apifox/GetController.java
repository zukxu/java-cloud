package com.zukxu.test.apifox;

import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 各种Get请求方式测试
 * </p>
 *
 * @author xupu
 * @since 2022/3/18 17:04
 */
@RestController
@RequestMapping("/get")
public class GetController {

    /**
     * 普通Get请求
     *
     * @return
     */
    @GetMapping("/hello")
    public String hello() {
        return "普通Get请求";
    }

    /**
     * get query请求
     *
     * @param url
     * @return
     */
    @GetMapping("/query/{url}")
    public String preview(@PathVariable String url) {
        return "get query请求:" + url;
    }

    /**
     * get params请求
     * 参数可以为空
     *
     * @param text
     * @return
     */
    @GetMapping("/params")
    public String params(String text) {
        return "get params请求: " + "传递的参数为: " + text;
    }

    /**
     * 参数必须填
     * 将传递的name参数赋给变量text
     *
     * @param text
     * @return
     */
    @GetMapping("/requiredParams")
    public String requiredParams(@RequestParam(value = "name") String text) {
        return "get params请求: " + "传递的参数为: " + text;
    }

}
