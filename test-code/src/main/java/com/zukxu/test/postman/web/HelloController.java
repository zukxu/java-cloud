package com.zukxu.test.postman.web;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.zukxu.test.postman.dto.UserDTO;
import com.zukxu.test.postman.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xupu
 * @description
 * @date 2021/9/25 22:44:41
 */
@RestController
@RequestMapping("/postman")
public class HelloController {
    @Autowired
    HelloService helloService;

    @GetMapping("/hello")
    public String hello() {
        return helloService.hello();
    }

    @GetMapping("/params")
    public String params(String text) {
        return "参数查询: " + "传递的参数为: " + text;
    }

    @GetMapping("/test")
    public Map params(int num) {
        String jsonString = "{\n" + "    \"cityid\": \"101120101\",\n" + "    \"city\": \"济南\",\n" + "    " +
                "\"update_time\": \"2020-04-17 10:50\",\n" + "    \"wea\": \"晴\",\n" + "    \"wea_img\": "
                + "\"qing\",\n" + "    \"tem\": \"16\",\n" + "    \"tem_day\": \"20\",\n" + "    " +
                "\"tem_night\": \"9\",\n" + "    \"win\": \"东北风\",\n" + "    \"win_speed\": \"3级\",\n" +
                "    \"win_meter\": \"小于12km/h\",\n" + "    \"air\": \"113\"\n" + "}";
        Map maps = (Map) JSON.parse(jsonString);
        return maps;
    }

    @PostMapping("/form")
    public String form(String username, @RequestParam(value = "password", required = false) String password, @RequestParam(value = "code", required = false) String code) {
        System.out.println(username + password + code);
        return "表单数据: " + username + " | " + password + " | " + code;
    }

    @PostMapping("/json")
    public String json(@RequestBody UserDTO user) {
        return "json数据：" + user.toString();
    }

    @PostMapping("/file")
    public Map<String, Object> fileUpload(MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        map.put("url",
                "/upload/head_pic/" + file.getOriginalFilename());
        map.put("title",
                "banner");
        map.put("filename",
                file.getOriginalFilename());
        return map;
    }

    @GetMapping("/preview")
    public String preview(String url) {
        return "文件预览:" + url;
    }

    @GetMapping("/captcha")
    public String captcha() {
        //Math.random()*(m-n)+n;
        //由于Math.random() 函数返回一个浮点,  伪随机数在范围[0，1)，所以Math.random()*(m-n)+n返回的结果为[n，m)。

        return String.valueOf(
                (int) (Math.random() * 9000) + 1000);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO user) {
        System.out.println(
                "登录数据: " + user.toString());
        return UUID.fastUUID()
                .toString();
    }
}
