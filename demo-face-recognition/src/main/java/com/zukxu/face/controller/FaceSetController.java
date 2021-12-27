package com.zukxu.face.controller;

import cn.hutool.core.util.StrUtil;
import com.zukxu.face.common.FaceConstant;
import com.zukxu.face.model.FaceSetModel;
import com.zukxu.face.util.FaceSetUtil;
import org.springframework.stereotype.Controller;

/**
 * face detect controller
 *
 * @author zukxu
 * CreateTime: 2021/5/19 0019 22:11
 */
@Controller
@RequestMapping("/faceSet")
public class FaceSetController {

    @PostMapping("/create")
    public boolean createFaceSet(FaceSetModel setModel) {
        setModel.setApi_key(FaceConstant.API_KEY);
        setModel.setApi_secret(FaceConstant.API_SECRET);
        return FaceSetUtil.createFaceSet(setModel);
    }

    @DeleteMapping("/delete")
    public boolean deleteFaceSet(String faceSetToken, Integer checkEmpty) {
        FaceSetModel setModel = new FaceSetModel();
        if (StrUtil.isNotEmpty(faceSetToken)) {
            setModel.setFaceset_token(faceSetToken);
        }
        setModel.setCheck_empty(checkEmpty);
        return FaceSetUtil.delFaceSet(setModel);
    }

    @GetMapping("/detail")
    public FaceSetModel getFaceSetDetail(String faceSetToken, Integer start) {
        FaceSetModel setModel = new FaceSetModel();
        if (StrUtil.isNotEmpty(faceSetToken)) {
            setModel.setFaceset_token(faceSetToken);
        }
        if (start != null) {
            setModel.setStart(start);
        }
        return FaceSetUtil.getFaceSetDetail(setModel);
    }

    @PutMapping("/update")
    public boolean updateFaceSet(String faceSetToken, FaceSetModel setModel) {
        if (StrUtil.isNotEmpty(faceSetToken)) {
            setModel.setFaceset_token(faceSetToken);
        }
        return FaceSetUtil.updateFaceSet(setModel);
    }

    @PostMapping("/addFace")
    public boolean addFace(String faceSetToken, String faceTokens) {
        FaceSetModel setModel = new FaceSetModel();
        if (StrUtil.isNotEmpty(faceSetToken)) {
            setModel.setFaceset_token(faceSetToken);
        }
        setModel.setFace_tokens(faceTokens);
        return FaceSetUtil.updateFaceSet(setModel);
    }

    @PutMapping("/removeFace")
    public boolean removeFace(String faceSetToken, String faceTokens) {
        FaceSetModel setModel = new FaceSetModel();
        if (StrUtil.isNotEmpty(faceSetToken)) {
            setModel.setFaceset_token(faceSetToken);
        }
        setModel.setFace_tokens(faceTokens);
        return FaceSetUtil.updateFaceSet(setModel);
    }
}
