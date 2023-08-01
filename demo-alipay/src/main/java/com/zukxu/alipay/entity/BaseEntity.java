package com.zukxu.alipay.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 基础实体类
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/4/18 0018 11:25
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -4323699851302350690L;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    /**
     * 创建人 ID
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建者ID")
    private String createBy;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
    /**
     * 更新人 ID
     */
    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty("更新者ID")
    private String updateBy;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;//唯一id
}
