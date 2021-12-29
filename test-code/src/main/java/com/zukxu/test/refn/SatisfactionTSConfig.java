package com.zukxu.test.refn;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@Builder
@Accessors(chain = true)
public class SatisfactionTSConfig {
    /**
     *
     */
    @ApiModelProperty
    private String id;

    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id")
    private String tenantId;

    /**
     * 报表类型
     */
    @ApiModelProperty(value = "报表类型")
    private String orgType;

    /**
     * 数据表头
     */
    @ApiModelProperty(value = "数据表头")
    private String tableHead;

    /**
     * 父id
     */
    @ApiModelProperty(value = "父id")
    private String parentId;

    /**
     * 排序，对应excel列
     */
    @ApiModelProperty(value = "排序，对应excel列")
    private Integer sort;

    @ApiModelProperty(value = "子集")
    private List<SatisfactionTSConfig> children;
}
