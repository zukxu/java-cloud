package com.zukxu.flowable.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zukxu.flowable.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * <p>
 * 流程定义DTO
 * </p>
 *
 * @author xupu
 * @since 2021/12/22 15:44
 */
@ApiModel
public class ProcessDefinitionDTO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty(name = "流程名称")
    private String name;

    @ApiModelProperty(name = "流程KEY")
    private String key;

    @ApiModelProperty(name = "流程版本")
    private int version;

    @ApiModelProperty(name = "所属分类")
    private String category;

    @ApiModelProperty(name = "流程描述")
    private String description;

    private String deploymentId;

    @ApiModelProperty(name = "部署时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deploymentTime;

    @ApiModelProperty(name = "流程图")
    private String diagramResourceName;

    @ApiModelProperty(name = "流程定义")
    private String resourceName;

    /**
     * 流程实例状态 1 激活 2 挂起
     */
    private String suspendState;

    private String suspendStateName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public Date getDeploymentTime() {
        return deploymentTime;
    }

    public void setDeploymentTime(Date deploymentTime) {
        this.deploymentTime = deploymentTime;
    }

    public String getDiagramResourceName() {
        return diagramResourceName;
    }

    public void setDiagramResourceName(String diagramResourceName) {
        this.diagramResourceName = diagramResourceName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getSuspendState() {
        return suspendState;
    }

    public void setSuspendState(String suspendState) {
        this.suspendState = suspendState;
    }

    public String getSuspendStateName() {
        return suspendStateName;
    }

    public void setSuspendStateName(String suspendStateName) {
        this.suspendStateName = suspendStateName;
    }
}
