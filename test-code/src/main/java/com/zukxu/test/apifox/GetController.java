package com.zukxu.test.apifox;

import com.zukxu.test.apifox.dto.PhoneDTO;
import com.zukxu.test.apifox.dto.UserDTO;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

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
     *
     * @return
     */
    @GetMapping("/query/{url}")
    public String preview(@PathVariable String url) {
        return "get 【PathVariable】请求:" + url;
    }

    /**
     * get params请求
     * 参数可以为空
     *
     * @param text
     *
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
     *
     * @return
     */
    @GetMapping("/requiredParams")
    public String requiredParams(@RequestParam(value = "name") String text) {
        return "get params请求: " + "传递的参数为: " + text;
    }

    /**
     * get Map接收参数
     *
     * @param params
     *
     * @return
     */
    @GetMapping("/map")
    public String map(@RequestParam Map<String, Object> params) {
        return "get map接收参数: " + "传递的参数为: " + params.get("name") + params.get("age");
    }

    /**
     * Get 请求接受一个数组
     *
     * @param name
     *
     * @return
     */
    @GetMapping("/array")
    public String array(@RequestParam String[] name) {
        return "get Array接收参数: " + "传递的参数为: " + Arrays.toString(name);
    }

    /**
     * Get 请求 对象接收参数
     *
     * @param dto
     *
     * @return
     */
    @GetMapping("/object")
    public String object(UserDTO dto) {
        return "get 对象接收参数: " + "传递的参数为: " + dto.toString();
    }

    /**
     * Get 请求 对象接收参数 自定义前缀
     *
     * @param dto
     *
     * @return
     */
    @GetMapping("/object/prefix")
    public String objectPrefix(@ModelAttribute("u") UserDTO dto) {
        return "get 对象自定义前缀接收参数: " + "传递的参数为: " + dto.toString();
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("u.");
    }
    /**
     * Get 多个对象传参
     *
     * @param dto
     *
     * @return
     */
    @GetMapping("/object/multi")
    public String objectMulti(UserDTO dto, PhoneDTO phoneDTO) {
        return "get 多个对象接收参数: " + "传递的参数为: " + dto.toString()+phoneDTO.toString();
    }
}
