package com.zukxu.file.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-03-18 16:37
 */
@Data
@ApiModel(value = "DKAttachment")
@TableName(value = "dk_attachment")
@Accessors(chain = true)
public class DKAttachment implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键id")
    private Long id;
    /**
     * business
     */
    @TableField(value = "business_key")
    @ApiModelProperty(value = "关联业务id")
    private String businessKey;
    /**
     * 访问路径
     */
    @TableField(value = "url")
    @ApiModelProperty(value = "访问路径")
    private String url;
    /**
     * 原始文件名
     */
    @TableField(value = "origin_name")
    @ApiModelProperty(value = "原始文件名")
    private String originName;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**
     * 文件上传方式（0-本地上传，1-oss，2-minio）
     */
    @TableField(value = "method")
    @ApiModelProperty(value = "文件上传方式（0-本地上传，1-oss，2-minio）")
    private Integer method;
    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;
}