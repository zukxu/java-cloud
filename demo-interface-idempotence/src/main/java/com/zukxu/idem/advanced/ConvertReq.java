package com.zukxu.idem.advanced;

import com.alibaba.fastjson.JSON;
import lombok.experimental.UtilityClass;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * <p>
 * 转换请求头工具类
 * </p>
 *
 * @author xupu
 * @since 2023/12/1 14:16:35
 */
@UtilityClass
public class ConvertReq {
    /**
     * post请求处理：获取 Body 参数，转换为SortedMap
     *
     * @param request HttpServletRequest
     */
    @SuppressWarnings("unchecked")
    public SortedMap<String, String> getBodyParams(final HttpServletRequest request) throws IOException {
        byte[] requestBody = StreamUtils.copyToByteArray(request.getInputStream());
        String body = new String(requestBody);
        return JSON.parseObject(body, SortedMap.class);
    }

    /**
     * get请求处理：将URL请求参数转换成SortedMap
     */
    public static SortedMap<String, String> getUrlParams(HttpServletRequest request) {
        String param = "";
        SortedMap<String, String> result = new TreeMap<>();

        if (ObjectUtils.isEmpty(request.getQueryString())) return result;

        try {
            param = URLDecoder.decode(request.getQueryString(), "utf-8");
        } catch (UnsupportedEncodingException ignored) {}

        String[] params = param.split("&");
        for (String s : params) {
            String[] array = s.split("=");
            result.put(array[0], array[1]);
        }
        return result;
    }
}
