package com.zukxu.face.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.zukxu.face.common.FaceConstant;
import com.zukxu.face.model.FaceSetModel;

/**
 * face_set util
 *
 * @author zukxu
 * CreateTime: 2021/5/19 0019 22:36
 */
public class FaceSetUtil {

    /**
     * 获取face_set
     *
     * @return 是否存在
     */
    public static boolean getFaceSets(FaceSetModel setModel) {
        JSONObject object = JSONObject.parseObject(HttpUtil.post(FaceConstant.FACE_SET_GET, setModel.toString()));
        //存储face_set相关返回的数据
        System.out.println(object);
        return StrUtil.isEmpty(String.valueOf(object.getJSONObject("error_message")));
    }

    /**
     * 删除face_set
     *
     * @return 是否成功
     */
    public static boolean delFaceSet(FaceSetModel setModel) {
        JSONObject object = JSONObject.parseObject(HttpUtil.post(FaceConstant.FACE_SET_DELETE, setModel.toString()));
        //存储face_set相关返回的数据
        System.out.println(object);
        return StrUtil.isEmpty(String.valueOf(object.getJSONObject("error_message")));
    }

    /**
     * 获取face_set详情
     *
     * @return 是否成功
     */
    public static FaceSetModel getFaceSetDetail(FaceSetModel setModel) {
        FaceSetModel object = JSONObject.parseObject(HttpUtil.post(FaceConstant.FACE_SET_DETAIL, setModel.toString()), FaceSetModel.class);
        //存储face_set相关返回的数据
        System.out.println(object);
        return object;
    }

    /**
     * 更新face_set
     *
     * @return 是否成功
     */
    public static boolean updateFaceSet(FaceSetModel setModel) {
        JSONObject object = JSONObject.parseObject(HttpUtil.post(FaceConstant.FACE_SET_UPDATE, setModel.toString()));
        //存储face_set相关返回的数据
        System.out.println(object);
        return StrUtil.isEmpty(String.valueOf(object.getJSONObject("error_message")));
    }

    /**
     * 移除内部face_token
     *
     * @return 是否成功
     */
    public static boolean removeFace(FaceSetModel setModel) {
        JSONObject object = JSONObject.parseObject(HttpUtil.post(FaceConstant.FACE_SET_REMOVE_FACE, setModel.toString()));
        //存储face_set相关返回的数据
        System.out.println(object);
        return StrUtil.isEmpty(String.valueOf(object.getJSONObject("error_message")));
    }

    /**
     * 将faceToken添加到face_set
     *
     * @return 是否添加成功
     */
    public static boolean addFace(FaceSetModel setModel) {

        JSONObject object = JSONObject.parseObject(HttpUtil.post(FaceConstant.FACE_SET_ADD_FACE, setModel.toString()));
        //存储face_set相关返回的数据
        System.out.println(object);

        return StrUtil.isEmpty(String.valueOf(object.getJSONObject("error_message")));
    }

    /**
     * 创建face_set
     *
     * @return 是否创建成功
     */
    public static boolean createFaceSet(FaceSetModel faceSet) {

        JSONObject object = JSONObject.parseObject(HttpUtil.post(FaceConstant.FACE_SET_CREATE, faceSet.toString()));
        //存储face_set相关返回的数据
        System.out.println(object);
        return StrUtil.isEmpty(String.valueOf(object.getJSONObject("error_message")));
    }

}
