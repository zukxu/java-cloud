package com.zukxu.flowable.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工作流任务相关--请求参数
 * </p>
 *
 * @author xupu
 * @since 2021/12/15 9:47
 */
@Data
@ApiModel
public class FlowTaskVo {

    @ApiModelProperty("任务Id")
    private String taskId;

    @ApiModelProperty("用户Id")
    private String userId;

    @ApiModelProperty("任务意见")
    private String comment;

    @ApiModelProperty("流程实例Id")
    private String instanceId;

    @ApiModelProperty("节点")
    private String targetKey;

    @ApiModelProperty("流程变量信息")
    private Map<String, Object> values;

    @ApiModelProperty("审批人")
    private String assignee;

    @ApiModelProperty("候选人")
    private List<String> candidateUsers;

    @ApiModelProperty("审批组")
    private List<String> candidateGroups;
}
