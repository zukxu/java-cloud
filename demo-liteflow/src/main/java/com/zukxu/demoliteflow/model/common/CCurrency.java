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
 * 通用接口公共参数
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
@ApiModel("通用接口公共参数")
public class CCurrency extends BaseCSS {

    /**
     * {@link com.zukxu.demoliteflow.enums.InterfaceTypeEnum}
     */
    @ApiModelProperty(value = "Currency", name = "接口类型")
    @JsonProperty("InterfaceType")
    @JSONField(name = "InterfaceType")
    private String InterfaceType;

}
