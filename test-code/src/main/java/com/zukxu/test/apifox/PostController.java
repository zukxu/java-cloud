package com.zukxu.test.apifox;

import com.zukxu.test.apifox.dto.UserDTO;
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
        map.put("url","/upload/" + file.getOriginalFilename());
        map.put("title","banner");
        map.put("filename",file.getOriginalFilename());
        return map;
    }
}
