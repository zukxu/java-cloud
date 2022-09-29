package com.zukxu.demoliteflow.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zukxu.demoliteflow.model.common.CDisPatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * 工单信息表
 * </p>
 *
 * @author xupu
 * @since 2022-09-26 11:12:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "work_flow")
public class WorkFlowF extends CDisPatch implements Serializable {

    @TableField(exist = false)
    @JsonProperty(value = "IdentyDetailName")
    @JSONField(name = "IdentyDetailName")
    private String IdentyDetailName;


    /**
     * 工单状态
     * {@link com.zukxu.demoliteflow.enums.State}
     */
    @TableField(value = "work_flow_status")
    @JsonProperty(value = "WorkFlowStatus")
    @JSONField(name = "WorkFlowStatus")
    private String WorkFlowStatus;

    @TableField(exist = false)
    @JsonProperty(value = "OriginUnitName")
    @JSONField(name = "OriginUnitName")
    private String OriginUnitName;

    @TableField(exist = false)
    @JsonProperty(value = "ReceiverUnitName")
    @JSONField(name = "ReceiverUnitName")
    private String ReceiverUnitName;

    /**
     * 1-发起方是集团，2-发起方是省分
     */
    @TableField(value = "unit")
    @JsonProperty(value = "Unit")
    @JSONField(name = "Unit")
    private String Unit;

    /**
     * 流程id
     */
    @TableField(value = "process_id")
    @JsonProperty(value = "ProcessId")
    @JSONField(name = "ProcessId")
    private String ProcessId;

    /**
     * 工单归档意见00满意01一般02不满意
     */
    @TableField(value = "filing_opinion")
    @JsonProperty(value = "FilingOpinion")
    @JSONField(name = "FilingOpinion")
    private String FilingOpinion;

    /**
     * 撤单时间
     */
    @TableField(value = "withdraw_time")
    @JsonProperty(value = "WithdrawTime")
    @JSONField(name = "WithdrawTime")
    private String WithdrawTime;

    /**
     * 撤单原因
     */
    @TableField(value = "withdraw_reason")
    @JsonProperty(value = "WithdrawReason")
    @JSONField(name = "WithdrawReason")
    private String WithdrawReason;

    /**
     * 催办时间
     */
    @TableField(value = "urge_time")
    @JsonProperty(value = "UrgeTime")
    @JSONField(name = "UrgeTime")
    private String UrgeTime;

    /**
     * 催办原因
     */
    @TableField(value = "urge_reason")
    @JsonProperty(value = "UrgeReason")
    @JSONField(name = "UrgeReason")
    private String UrgeReason;


    /**
     * 回复时上传的附件,多个用|分开
     */
    @TableField(value = "reply_attach_list")
    @JsonProperty(value = "ReplyAttachList")
    @JSONField(name = "ReplyAttachList")
    private String ReplyAttachList;

    @TableField(exist = false)
    @JsonProperty(value = "IsDispatchNow")
    @JSONField(name = "IsDispatchNow")
    private Boolean IsDispatchNow;


    @TableField(exist = false)
    private Map<String, Object> variables;


    private static final long serialVersionUID = 1L;

}