package com.zukxu.demoliteflow.utils;

import com.alibaba.fastjson.JSON;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-04-26 17:58
 */
public class SignUtil {

    public static String sign(String queryJson) throws Exception {
        String appSecret = "appsecret";
        Map maps = JSON.parseObject(queryJson, Map.class);
        String requestSignString = getRequestRelatedStrings(maps);
        String originalString = appSecret + requestSignString + appSecret;
        return checkMD5(originalString);
    }

    static String checkMD5(String originalString) throws UnsupportedEncodingException, NoSuchAlgorithmException, IllegalArgumentException {
        byte[] signedOriginalStringBytes = MessageDigest.getInstance("MD5").digest(originalString.getBytes("UTF-8"));
        String signedOriginalString = new HexBinaryAdapter().marshal(signedOriginalStringBytes);
        return signedOriginalString;
    }

    protected static String getRequestRelatedStrings(Map<String, String> parameterMap) {
        Set<String> parameterMapKeySet = parameterMap.keySet();
        String[] parameterMapKeySetArray = parameterMapKeySet.toArray(new String[parameterMapKeySet.size()]);
        Arrays.sort(parameterMapKeySetArray);
        StringBuilder requestSignStringBuilder = new StringBuilder();
        for (int i = 0; i < parameterMapKeySetArray.length; i++) {
            String key = parameterMapKeySetArray[i];
            String value = String.valueOf(parameterMap.get(key));
            if (!"sign".equals(key))
                requestSignStringBuilder.append(key).append(value);
        }
        return requestSignStringBuilder.toString();
    }
}