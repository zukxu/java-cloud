package com.zukxu.test.apifox;

import com.zukxu.common.config.aop.PostSingleParam;
import com.zukxu.test.apifox.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

    @PostMapping("/single")
    public String singleParam(@PostSingleParam String test) {
        System.out.println("接收参数：" + test);
        return "返回参数：" + test;
    }

    /**
     * 可变长参数
     *
     * @param test
     * @return
     */
    @PostMapping("/length")
    public String lengthParam(String... test) {
        System.out.println("接收参数：" + test[0]);
        return "返回参数：" + test[0];
    }

    /**
     * post form请求
     *
     * @param username
     * @param isShow
     * @param num
     * @param code
     * @return
     */
    @PostMapping("/form")
    public String form(@RequestParam(value = "username", required = false) String username,
                       @RequestParam(value = "isShow", required = false) boolean isShow,
                       @RequestParam(value = "num", required = false, defaultValue = "0") int num,
                       @RequestParam(value = "code", required = false, defaultValue = "0") double code
                      ) {
        System.out.println(username + isShow + num + code);
        return "表单数据: " + username + " | " + isShow + " | " + num + " | " + code;
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

    @PostMapping("/jsonList")
    public String json(@RequestBody List<UserDTO> users) {
        String result = "";
        for (UserDTO user : users) {
            result += user.getUsername() + " " + user.getAge() + "\n";
        }
        return result;
    }

    /**
     * post file请求
     *
     * @param file
     * @return
     */
    @PostMapping("/file")
    public Map<String, Object> file(MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        map.put("url", "/upload/" + file.getOriginalFilename());
        map.put("title", "banner");
        map.put("filename", file.getOriginalFilename());
        return map;
    }

    /**
     * post map接受参数
     *
     * @param map
     * @return
     */
    @PostMapping("/map")
    public String map(@RequestBody Map<String, Object> map) {
        return "map接收参数：" + map.get("username");
    }

    /**
     * post 数组接受参数
     *
     * @param array
     * @return
     */
    @PostMapping("/array")
    public String array(@RequestParam String[] array) {
        return "数组接收参数：" + Arrays.toString(array);
    }

    /**
     * 接收字符串文本
     *
     * @return
     */
    @PostMapping("/str")
    public String strText(HttpServletRequest request) {
        ServletInputStream is = null;
        try {
            is = request.getInputStream();
            StringBuilder sb = new StringBuilder();
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len));
            }
            System.out.println(sb);
            return "获取到的文本内容为：" + sb;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
