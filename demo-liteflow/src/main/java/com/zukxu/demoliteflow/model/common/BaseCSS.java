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
 * 工单接口公共实体
 * </p>
 *
 * @author xupu
 * @since 2022/8/29 15:31:36
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("工单接口公共实体")
public class BaseCSS {

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

    @ApiModelProperty(value = "UpdateTime", name = "更新时间")
    @JsonProperty(value = "UpdateTime")
    @JSONField(name = "UpdateTime")
    private String UpdateTime;

    @ApiModelProperty(value = "ParaList", name = "请求参数列表")
    @JsonProperty(value = "ParaList")
    @JSONField(name = "ParaList")
    private Para[] ParaList;

}
