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
 * 催办接口公共参数
 * </p>
 *
 * @author xupu
 * @since 2022/8/29 15:40:44
 */
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("催办接口公共参数")
public class CUrge extends BaseCSS {

    @ApiModelProperty(value = "UrgeTime", name = "催办时间 YYYYMMDDHHMMSS")
    @JsonProperty("UrgeTime")
    @JSONField(name = "UrgeTime")
    private String UrgeTime;

    @ApiModelProperty(value = "UrgeReason", name = "催办原因")
    @JsonProperty("UrgeReason")
    @JSONField(name = "UrgeReason")
    private String UrgeReason;

}
