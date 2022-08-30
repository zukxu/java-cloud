package com.zukxu.demoliteflow.model.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 派发接口公共参数
 * </p>
 *
 * @author xupu
 * @since 2022/8/29 15:19:29
 */
@ApiModel("派发接口公共参数")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CDisPatch {

    @ApiModelProperty(value = "Identifier", name = "工单编号")
    @JsonProperty(value = "Identifier")
    @JSONField(name = "Identifier")
    private String Identifier;

    @ApiModelProperty(value = "IdentyType", name = "工单类型")
    @JsonProperty(value = "IdentyType")
    @JSONField(name = "IdentyType")
    private String IdentyType;

    @ApiModelProperty(value = "IdentySubtype", name = "工单子类型")
    @JsonProperty(value = "IdentySubtype")
    @JSONField(name = "IdentySubtype")
    private String IdentySubtype;

    @ApiModelProperty(value = "IdentyDetail", name = "工单细类")
    @JsonProperty(value = "IdentyDetail")
    @JSONField(name = "IdentyDetail")
    private String IdentyDetail;

    @ApiModelProperty(value = "Title", name = "工单标题")
    @JsonProperty(value = "Title")
    @JSONField(name = "Title")
    private String Title;

    @ApiModelProperty(value = "Content", name = "工单内容")
    @JsonProperty(value = "Content")
    @JSONField(name = "Content")
    private String Content;

    @ApiModelProperty(value = "OriginUnit", name = "工单发起方")
    @JsonProperty(value = "OriginUnit")
    @JSONField(name = "OriginUnit")
    private String OriginUnit;

    @ApiModelProperty(value = "ReceiverUnit", name = "工单接收方")
    @JsonProperty(value = "ReceiverUnit")
    @JSONField(name = "ReceiverUnit")
    private String ReceiverUnit;

    @ApiModelProperty(value = "CreatTime", name = "工单创建时间")
    @JsonProperty(value = "CreatTime")
    @JSONField(name = "CreatTime")
    private String CreatTime;

    @ApiModelProperty(value = "ProcessTime", name = "工单要求处理时间")
    @JsonProperty(value = "ProcessTime")
    @JSONField(name = "ProcessTime")
    private String ProcessTime;

    @ApiModelProperty(value = "Creator", name = "工单创建人")
    @JsonProperty(value = "Creator")
    @JSONField(name = "Creator")
    private String Creator;

    @ApiModelProperty(value = "CreatorContactInfo", name = "创建人联系方式")
    @JsonProperty(value = "CreatorContactInfo")
    @JSONField(name = "CreatorContactInfo")
    private String CreatorContactInfo;

    @ApiModelProperty(value = "AttachList", name = "附件")
    @JsonProperty(value = "AttachList")
    @JSONField(name = "AttachList")
    private String AttachList;

    @ApiModelProperty(value = "ParaList", name = "请求参数列表")
    @JsonProperty(value = "ParaList")
    @JSONField(name = "ParaList")
    private com.bonc.flowable.model.extend.Para[] ParaList;

}
