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
 * 再处理接口公共参数
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
@ApiModel("再处理接口公共参数")
public class CReprocess extends BaseCSS {

    @ApiModelProperty(value = "Creator", name = "工单审核人")
    @JsonProperty("Creator")
    @JSONField(name = "Creator")
    private String Creator;

    @ApiModelProperty(value = "CreatorTel", name = "审核人联系方式")
    @JsonProperty("CreatorTel")
    @JSONField(name = "CreatorTel")
    private String CreatorTel;

    @ApiModelProperty(value = "ReprocessingOpinion", name = "再处理意见")
    @JsonProperty("ReprocessingOpinion")
    @JSONField(name = "ReprocessingOpinion")
    private String ReprocessingOpinion;

}
