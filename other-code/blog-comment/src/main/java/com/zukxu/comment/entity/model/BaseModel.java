package com.zukxu.comment.entity.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Description:
 *
 * @author zukxu
 * CreateTime: 2020/10/30 0030 11:54
 */
@Data
@ToString(callSuper = true)
public abstract class BaseModel {

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建时间")
	protected LocalDateTime createTime;

	/**
	 * 创建人 ID
	 */
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建者ID")
	protected String createBy;

	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.UPDATE)
	@ApiModelProperty("更新时间")
	protected LocalDateTime updateTime;

	/**
	 * 更新人 ID
	 */
	@TableField(fill = FieldFill.UPDATE)
	@ApiModelProperty("更新者ID")
	protected String updateBy;
}

