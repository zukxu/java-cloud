package com.zukxu.demoliteflow.model.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 回复接口公共参数
 * </p>
 *
 * @author xupu
 * @since 2022/8/29 15:36:18
 */
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("回复接口公共参数")
public class CReply extends BaseCSS {

    @ApiModelProperty(value = "HandlingOpinion", name = "工单处理意见")
    @JsonProperty("HandlingOpinion")
    @JSONField(name = "HandlingOpinion")
    private String HandlingOpinion;

    @ApiModelProperty(value = "Handler", name = "工单处理人")
    @JsonProperty("Handler")
    @JSONField(name = "Handler")
    private String Handler;

    @ApiModelProperty(value = "HandlerInfor", name = "处理人联系方式")
    @JsonProperty("HandlerInfor")
    @JSONField(name = "HandlerInfor")
    private String HandlerInfor;

    @ApiModelProperty(value = "HandingTime", name = "工单处理时间")
    @JsonProperty("HandingTime")
    @JSONField(name = "HandingTime")
    private String HandingTime;

    @ApiModelProperty(value = "HandlerRank", name = "处理人职级")
    @JsonProperty("HandlerRank")
    @JSONField(name = "HandlerRank")
    private String HandlerRank;

    @ApiModelProperty(value = "HandingDepartment", name = "处理人员部门")
    @JsonProperty("HandingDepartment")
    @JSONField(name = "HandingDepartment")
    private String HandingDepartment;

    @ApiModelProperty(value = "AttachList", name = "附件")
    @JsonProperty("AttachList")
    @JSONField(name = "AttachList")
    private String AttachList;

}
