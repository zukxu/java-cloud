package com.zukxu.face.model;

import com.zukxu.face.common.FaceConstant;
import lombok.Data;

import java.io.Serializable;

/**
 * base model
 *
 * @author zukxu
 * CreateTime: 2021/5/20 0020 10:20
 */
@Data
public class BaseModel implements Serializable {
	private String api_key= FaceConstant.API_KEY;
	private String api_secret=FaceConstant.API_SECRET;
}
