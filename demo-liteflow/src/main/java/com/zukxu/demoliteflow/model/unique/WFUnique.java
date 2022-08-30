package com.zukxu.demoliteflow.model.unique;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("工单独有参数实体")
public class WFUnique {

    @ApiModelProperty(value = "Identifier", name = "工单编号")
    @JsonProperty(value = "Identifier")
    @JSONField(name = "Identifier")
    private String Identifier;

    @ApiModelProperty(value = "dispatcherJson", name = "派发独有参数")
    private String dispatcherJson;

    @ApiModelProperty(value = "replyJson", name = "回复独有参数")
    private String replyJson;

    @ApiModelProperty(value = "statementJson", name = "归档独有参数")
    private String statementJson;

    @ApiModelProperty(value = "currentJson", name = "通用接口独有参数")
    private String currentJson;

    @ApiModelProperty(value = "syncDataJson", name = "同步独有参数")
    private String syncDataJson;

}

