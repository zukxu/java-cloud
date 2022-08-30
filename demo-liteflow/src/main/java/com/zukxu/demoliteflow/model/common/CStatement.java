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
 * 归档接口公共参数
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
@ApiModel("归档接口公共参数")
public class CStatement extends BaseCSS {

    @ApiModelProperty(value = "FilingOpinion", name = "归档意见")
    @JsonProperty("FilingOpinion")
    @JSONField(name = "FilingOpinion")
    private String FilingOpinion;

}
