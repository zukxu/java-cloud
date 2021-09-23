package com.zukxu.face.controller;

import com.zukxu.face.util.FaceUtil;
import com.zukxu.face.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 * face detect controller
 *
 * @author zukxu
 * CreateTime: 2021/5/19 0019 22:11
 */
@Controller
@RequestMapping("/face")
public class FaceController {

	@PostMapping("/detect")
	public String detectFace(MultipartFile image) {
		String res = FaceUtil.detect(FileUtil.getImageBase64(image));

		return res;
	}

	@PostMapping("/search")
	public String searchFace(String faceToken) {
		String res = FaceUtil.search(faceToken);

		return res;
	}

	@PostMapping("/compare")
	public String compareFace(String faceToken,MultipartFile image) {
		String res = FaceUtil.compare(faceToken,FileUtil.getImageBase64(image));

		return res;
	}
}
