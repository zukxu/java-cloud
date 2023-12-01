package com.zukxu.idem.advanced;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.util.SortedMap;

/**
 * <p>
 * 签名校验工具类
 * </p>
 *
 * @author xupu
 * @since 2023/12/1 14:42:08
 */
@Slf4j
@UtilityClass
public class SignUtil {
    /**
     * 验证签名
     * 验证算法：把timestamp + JsonUtil.object2Json(SortedMap)合成字符串，然后MD5
     */
    @SneakyThrows
    public boolean verifySign(SortedMap<String, String> map, RequestHeader requestHeader) {
        String params = requestHeader.getNonce() + requestHeader.getTimestamp() + JSON.toJSONString(map);
        return verifySign(params, requestHeader);
    }

    /**
     * 验证签名
     */
    public boolean verifySign(String params, RequestHeader requestHeader) {
        log.debug("客户端签名: {}", requestHeader.getSign());
        if (ObjectUtils.isEmpty(params)) {
            return false;
        }
        log.info("客户端上传内容: {}", params);
        String paramsSign = DigestUtils.md5DigestAsHex(params.getBytes()).toUpperCase();
        log.info("客户端上传内容加密后的签名结果: {}", paramsSign);
        return requestHeader.getSign().equals(paramsSign);
    }
}
