package com.zukxu.face.util;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.zukxu.face.common.FaceConstant;

/**
 * face util
 *
 * @author zukxu
 * CreateTime: 2021/5/19 0019 22:36
 */
public class FaceUtil {

    /**
     * 人脸检测
     * <p>
     * // * @param file 图片
     *
     * @return face_token
     */
    // public static String detect(File file) {
    public static String detect(String base64) {
        FaceConstant.paramsMap.put("image_base64", base64);

        String res = HttpUtil.post(FaceConstant.FACE_DETECT_API, String.valueOf(FaceConstant.paramsMap));
        JSONObject jsonObject = JSONObject.parseObject(res);
        System.out.println(res);
        return res;
    }

    /**
     * 图片比较
     *
     * @param faceToken1 第一张图片token
     * @param base64_2   第二张图片
     * @return 对比结果
     */
    public static String compare(String faceToken1, String base64_2) {
        FaceConstant.paramsMap.put("face_token1", faceToken1);
        FaceConstant.paramsMap.put("image_base64_2", base64_2);

        String res = HttpUtil.post(FaceConstant.FACE_COMPARE_API, String.valueOf(FaceConstant.paramsMap));
        JSONObject jsonObject = JSONObject.parseObject(res);
        System.out.println(res);
        return res;
    }

    /**
     * 图片搜索
     *
     * @param faceToken 搜素faceToken
     * @return
     */
    public static String search(String faceToken) {
        FaceConstant.paramsMap.put("face_token", faceToken);

        String res = HttpUtil.post(FaceConstant.FACE_SEARCH_API, String.valueOf(FaceConstant.paramsMap));
        JSONObject jsonObject = JSONObject.parseObject(res);
        System.out.println(res);
        return res;
    }

}
