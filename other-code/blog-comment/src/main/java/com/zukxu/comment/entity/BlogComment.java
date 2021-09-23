package com.zukxu.comment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zukxu.comment.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description:
 *
 * @author zukxu
 * CreateTime: 2020/10/30 0030 11:49
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_comment")
@ApiModel(value = "BlogComment对象")
public class BlogComment extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "逻辑删除（1-是 0-否）")
    @TableField("enable")
    private Boolean enable;

    @ApiModelProperty(value = "用户ID")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "评论")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "博客信息ID")
    @TableField("blog_id")
    private String blogId;

    @ApiModelProperty(value = "审核状态（0待审核，1,审核通过，2审核不通过）")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "回复评论id")
    @TableField("parent_id")
    private String parentId;
}
