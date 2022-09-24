package com.zukxu.demoliteflow.model.extend;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 各环节流转信息日志
 * </p>
 *
 * @author xupu
 * @since 2022-04-26 15:45
 */
@Data
@Accessors(chain = true)
public class ExtIdentylogList {

    //处理时间
    @JsonProperty(value = "HandingTime")
    @JSONField(name = "HandingTime")
    private String HandingTime;

    //当前环节处理部门 YYYYMMDDHHMMSS 例如：20210803152700
    @JsonProperty(value = "HandingDepartment")
    @JSONField(name = "HandingDepartment")
    private String HandingDepartment;

    //工单处理人    必填：
    @JsonProperty(value = "Handler")
    @JSONField(name = "Handler")
    private String Handler;

    //工单处理人联系方式    xx部/xx中心/xx组
    @JsonProperty(value = "HandlerContactInfor")
    @JSONField(name = "HandlerContactInfor")
    private String HandlerContactInfor;

    //工单处理意见    必填：
    @JsonProperty(value = "HandingOpinions")
    @JSONField(name = "HandingOpinions")
    private String HandingOpinions;

    //处理环节名称
    @JsonProperty(value = "ProcessingName")
    @JSONField(name = "ProcessingName")
    private String ProcessingName;

    //环节类型 枚举值：
    //0：处理环节
    //1：审批环节
    @JsonProperty(value = "PhaseType")
    @JSONField(name = "PhaseType")
    private String PhaseType;

    //审核结果 枚举值：
    //1：通过:2：驳回；3：提交下一审批
    //注：如果是审批环节，则审批结果为必填
    @JsonProperty(value = "ApprovalResults")
    @JSONField(name = "ApprovalResults")
    private String ApprovalResults;

}
