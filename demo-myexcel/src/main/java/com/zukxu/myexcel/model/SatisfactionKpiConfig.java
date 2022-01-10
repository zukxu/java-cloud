package com.zukxu.myexcel.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "satisfaction_kpi_config")
@Data
@TableName(value = "satisfaction_kpi_config")
public class SatisfactionKpiConfig implements Serializable {
    @TableField(value = "id")
    @ApiModelProperty(value = "")
    private String id;

    /**
     * KPI名称
     */
    @TableField(value = "kpi_name")
    @ApiModelProperty(value = "KPI名称")
    private String kpiName;

    /**
     * 权重
     */
    @TableField(value = "weight")
    @ApiModelProperty(value = "权重")
    private String weight;

    /**
     * 类型（001 手机 002 5G 003 宽带）
     */
    @TableField(value = "`type`")
    @ApiModelProperty(value = "类型（001 手机 002 5G 003 宽带）")
    private String type;

    /**
     * 年度
     */
    @TableField(value = "`year`")
    @ApiModelProperty(value = "年度")
    private String year;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    private static final long serialVersionUID = 1L;
}