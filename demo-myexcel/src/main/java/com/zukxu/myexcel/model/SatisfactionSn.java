package com.zukxu.myexcel.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 省内满意度
 */
@ApiModel(value = "省内满意度")
@Data
@TableName(value = "satisfaction_sn")
public class SatisfactionSn implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private String id;

    @TableField(value = "`year`")
    @ApiModelProperty(value = "")
    private String year;

    /**
     * 季度（001 002 003 004）
     */
    @TableField(value = "period")
    @ApiModelProperty(value = "季度（001 002 003 004）")
    private String period;

    /**
     * 标识（phone 手机 5g 5G broadband 宽带）
     */
    @TableField(value = "org_type")
    @ApiModelProperty(value = "标识（phone 手机 5g 5G broadband 宽带）")
    private String orgType;

    /**
     * 地市编码
     */
    @TableField(value = "city_id")
    @ApiModelProperty(value = "地市编码")
    private String cityId;

    /**
     * 地市名称
     */
    @TableField(value = "city_name")
    @ApiModelProperty(value = "地市名称")
    private String cityName;

    /**
     * 指标名称
     */
    @TableField(value = "kpi_name")
    @ApiModelProperty(value = "指标名称")
    private String kpiName;

    /**
     * 满意度
     */
    @TableField(value = "yd_satisfaction")
    @ApiModelProperty(value = "满意度")
    private Double ydSatisfaction;

    @TableField(value = "lt_satisfaction")
    @ApiModelProperty(value = "")
    private Double ltSatisfaction;

    @TableField(value = "dx_satisfaction")
    @ApiModelProperty(value = "")
    private Double dxSatisfaction;

    /**
     * 排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value = "排序")
    private Integer sort;

    private static final long serialVersionUID = 1L;
}