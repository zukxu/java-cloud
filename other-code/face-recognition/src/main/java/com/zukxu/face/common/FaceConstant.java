package com.zukxu.face.common;

import cn.hutool.core.map.MapUtil;

import java.util.Map;

/**
 * constant
 *
 * @author zukxu
 * CreateTime: 2021/5/19 0019 22:13
 */
public class FaceConstant {
    //CONFIG
    public static final String APP_NAME = "faceLogin";
    public static final String API_KEY = "YiLRkNPRVrR_0GRbvXhzFyWopkzsrEAH";
    public static final String API_SECRET = "4oR_DBYtOTOJiOvzWclSwhzwWHBPAbvy";
    public static final String DISPLAY_NAME = "faceLoginSet";
    public static final String OUTER_ID = "faceLoginFaceSet";
    //API
    public static final String FACE_DETECT_API = "https://api-cn.faceplusplus.com/facepp/v3/detect";
    public static final String FACE_COMPARE_API = "https://api-cn.faceplusplus.com/facepp/v3/compare";
    public static final String FACE_SEARCH_API = "https://api-cn.faceplusplus.com/facepp/v3/search";
    //	FACE_SET
    public static final String FACE_SET_CREATE = "https://api-cn.faceplusplus.com/facepp/v3/faceset/create";
    public static final String FACE_SET_GET = "https://api-cn.faceplusplus.com/facepp/v3/faceset/getfacesets";
    public static final String FACE_SET_DELETE = "https://api-cn.faceplusplus.com/facepp/v3/faceset/delete";
    public static final String FACE_SET_DETAIL = "https://api-cn.faceplusplus.com/facepp/v3/faceset/getdetail";
    public static final String FACE_SET_UPDATE = "https://api-cn.faceplusplus.com/facepp/v3/faceset/update";
    public static final String FACE_SET_ADD_FACE = "https://api-cn.faceplusplus.com/facepp/v3/faceset/addface";
    public static final String FACE_SET_ADD_FACE_ASYNC = "https://api-cn.faceplusplus.com/facepp/v3/faceset/async/addface";
    public static final String FACE_SET_REMOVE_FACE = " https://api-cn.faceplusplus.com/facepp/v3/faceset/removeface";
    public static final String FACE_SET_REMOVE_FACE_ASYNC = "https://api-cn.faceplusplus.com/facepp/v3/faceset/async/removeface";

    //PARAMS
    public static Map<Object, Object> paramsMap = MapUtil.builder()
            .put("app_name", APP_NAME)
            .put("api_key", API_KEY)
            .put("api_secret", API_SECRET)
            .put("display_name", DISPLAY_NAME)
            .put("outer_id", OUTER_ID)
            .build();
}
