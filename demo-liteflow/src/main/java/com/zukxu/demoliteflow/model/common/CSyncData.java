package com.zukxu.demoliteflow.model.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zukxu.demoliteflow.model.extend.Para;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 同步接口公共参数
 * </p>
 *
 * @author xupu
 * @since 2022/8/29 15:41:31
 */
@ApiModel("同步接口公共参数")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CSyncData {

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

    @ApiModelProperty(value = "LaunchCompany", name = "发起省专")
    @JsonProperty(value = "LaunchCompany")
    @JSONField(name = "LaunchCompany")
    private String LaunchCompany;

    @ApiModelProperty(value = "ReceiverUnit", name = "工单接收方")
    @JsonProperty(value = "ReceiverUnit")
    @JSONField(name = "ReceiverUnit")
    private String ReceiverUnit;

    @ApiModelProperty(value = "ParaList", name = "请求参数列表")
    @JsonProperty(value = "ParaList")
    @JSONField(name = "ParaList")
    private Para[] ParaList;

}
