package com.zukxu.demoliteflow.model.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 查询接口公共参数
 * </p>
 *
 * @author xupu
 * @since 2022/8/29 15:36:18
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("查询接口公共参数")
public class CQuery {

    @ApiModelProperty(value = "Identifier", name = "工单编号")
    @JsonProperty(value = "Identifier")
    @JSONField(name = "Identifier")
    private String Identifier;

    @ApiModelProperty(value = "LaunchCompany", name = "发起省专")
    @JsonProperty(value = "LaunchCompany")
    @JSONField(name = "LaunchCompany")
    private String LaunchCompany;

    @ApiModelProperty(value = "ForwardCompany", name = "转发省专")
    @JsonProperty(value = "ForwardCompany")
    @JSONField(name = "ForwardCompany")
    private String ForwardCompany;

}
