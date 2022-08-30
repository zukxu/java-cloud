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
 * 撤单接口公共参数
 * </p>
 *
 * @author xupu
 * @since 2022/8/29 15:40:44
 */
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("撤单接口公共参数")
public class CWithdraw extends BaseCSS {

    @ApiModelProperty(value = "WithdrawTime", name = "工单撤单时间 YYYYMMDDHHMMSS")
    @JsonProperty("WithdrawTime")
    @JSONField(name = "WithdrawTime")
    private String WithdrawTime;

    @ApiModelProperty(value = "WithdrawReason", name = "撤单原因")
    @JsonProperty("WithdrawReason")
    @JSONField(name = "WithdrawReason")
    private String WithdrawReason;

}
