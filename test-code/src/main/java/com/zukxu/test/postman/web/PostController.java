package com.zukxu.test.postman.web;

import com.zukxu.test.postman.dto.UserDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 各种Post请求方式测试
 * </p>
 *
 * @author xupu
 * @since 2022/3/18 17:04
 */
@RestController
@RequestMapping("/post")
public class PostController {

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
     * post form请求
     *
     * @param username
     * @param password
     * @param code
     * @return
     */
    @PostMapping("/form")
    public String form(String username, @RequestParam(value = "password", required = false) String password, @RequestParam(value = "code", required = false) String code) {
        System.out.println(username + password + code);
        return "表单数据: " + username + " | " + password + " | " + code;
    }

    /**
     * post json请求
     *
     * @param user
     * @return
     */
    @PostMapping("/json")
    public String json(@RequestBody UserDTO user) {
        return "json数据：" + user.toString();
    }

    /**
     * post file请求
     *
     * @param file
     * @return
     */
    @PostMapping("/file")
    public Map<String, Object> fileUpload(MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        map.put("url",
                "/upload/" + file.getOriginalFilename());
        map.put("title",
                "banner");
        map.put("filename",
                file.getOriginalFilename());
        return map;
    }
}
