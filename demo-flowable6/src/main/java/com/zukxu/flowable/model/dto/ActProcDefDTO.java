package com.zukxu.flowable.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 流程定义DTO实体 ACT_PROC_DEF 数据表相关实体
 * </p>
 *
 * @author xupu
 * @since 2021/12/15 20:18
 */
@Data
@ApiModel
@Accessors(chain = true)
public class ActProcDefDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("流程id")
    private String id;

    @ApiModelProperty("流程名称")
    private String name;

    @ApiModelProperty("流程key")
    private String key;

    @ApiModelProperty("流程分类")
    private String category;

    @ApiModelProperty("配置表单id")
    private String formId;

    @ApiModelProperty("配置表单名称")
    private String formName;

    @ApiModelProperty("版本")
    private int version;

    @ApiModelProperty("部署ID")
    private String deploymentId;

    @ApiModelProperty("流程定义状态: 1:激活 , 2:挂起")
    private int suspensionState;

    @ApiModelProperty("部署时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deploymentTime;

    public ActProcDefDTO() {
    }

    public ActProcDefDTO(String id,
                         String name,
                         String key,
                         String category,
                         String formId,
                         String formName,
                         int version,
                         String deploymentId,
                         int suspensionState,
                         Date deploymentTime
                        ) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.category = category;
        this.formId = formId;
        this.formName = formName;
        this.version = version;
        this.deploymentId = deploymentId;
        this.suspensionState = suspensionState;
        this.deploymentTime = deploymentTime;
    }
}
