package com.zukxu.face.model;

import com.zukxu.face.common.FaceConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * FaceSett model
 *
 * @author zukxu
 * CreateTime: 2021/5/20 0020 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FaceSetModel extends BaseModel {
	private String display_name = FaceConstant.DISPLAY_NAME;
	private String faceset_token;
	private String outer_id = FaceConstant.OUTER_ID;

	//更新时使用
	private String new_outer_id;

	//FaceSet 自定义标签组成的字符串，用来对 FaceSet 分组。最长255个字符，多个 tag 用逗号分隔
	private String tags;

	//人脸标识 face_token，可以是一个或者多个，用逗号分隔。最多不超过5个 face_token
	private String face_tokens;

	//自定义用户信息
	private String user_data;

	//在传入 outer_id 的情况下，如果 outer_id 已经存在，是否将 face_token 加入已经存在的 FaceSet 中
	// 0：不将 face_tokens 加入已存在的 FaceSet 中，直接返回 FACESET_EXIST 错误
	// 1：将 face_tokens 加入已存在的 FaceSet 中
	// 默认值为0
	private Integer force_merge;

	//通过传入数字 n，可以控制本 API 从第 n 个 faceset_token 开始返回 默认1
	private Integer start;

	//删除时是否检查FaceSet中是否存在face_token，默认值为1
	// 0：不检查
	// 1：检查
	// 如果设置为1，当FaceSet中存在face_token则不能删
	private Integer check_empty;

	//用于进行下一次请求。返回值表示排在此次返回的所有 face_token 之后的下一个 face_token 的序号。
	// 如果返回此字段，则说明未返回完此 FaceSet 下的所有 face_token。可以将此字段的返回值，在下一次调用时传入 start 字段中，获取接下来的 face_token。
	// 如果没有返回该字段，则说明已经返回此 FaceSet 下的所有 face_token。
	private String next;

	//错误消息
	private String error_message;
}
