package com.zukxu.test.web;

import com.alibaba.fastjson.JSON;
import com.zukxu.test.dto.UserDTO;
import com.zukxu.test.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author xupu
 * @description
 * @date 2021/9/25 22:44:41
 */
@RestController
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
    public String form(String username, String password, String code) {
        return "表单数据: " + username + " | " + password + " | " + code;
    }

    @PostMapping("/json")
    public Map json(@RequestBody UserDTO user) {
        String jsonString = "{\n" + "    \"cityid\": \"101120101\",\n" + "    \"city\": \"济南\",\n" + "    " +
                            "\"update_time\": \"2020-04-17 10:50\",\n" + "    \"wea\": \"晴\",\n" + "    \"wea_img\": "
                            + "\"qing\",\n" + "    \"tem\": \"16\",\n" + "    \"tem_day\": \"20\",\n" + "    " +
                            "\"tem_night\": \"9\",\n" + "    \"win\": \"东北风\",\n" + "    \"win_speed\": \"3级\",\n" +
                            "    \"win_meter\": \"小于12km/h\",\n" + "    \"air\": \"113\"\n" + "}";
        Map maps = (Map) JSON.parse(jsonString);
        return maps;
    }

    @PostMapping("/file")
    public String fileUpload(MultipartFile file) {
        return "文件上传:" + file.getOriginalFilename();
    }
}
