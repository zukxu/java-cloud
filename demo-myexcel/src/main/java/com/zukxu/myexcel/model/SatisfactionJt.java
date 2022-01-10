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
@TableName(value = "satisfaction_jt")
public class SatisfactionJt implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private String id;

    /**
     * 年度
     */
    @TableField(value = "`year`")
    @ApiModelProperty(value = "年度")
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
     * 指标名称
     */
    @TableField(value = "kpi_name")
    @ApiModelProperty(value = "指标名称")
    private String kpiName;

    /**
     * 上级指标名称
     */
    @TableField(value = "parent_kpi_name")
    @ApiModelProperty(value = "上级指标名称")
    private String parentKpiName;

    /**
     * 移动满意度
     */
    @TableField(value = "yd_satisfaction")
    @ApiModelProperty(value = "移动满意度")
    private Double ydSatisfaction;

    /**
     * 移动排名（全国省排名）
     */
    @TableField(value = "yd_rank")
    @ApiModelProperty(value = "移动排名（全国省排名）")
    private Integer ydRank;

    /**
     * 联通满意度
     */
    @TableField(value = "lt_satisfaction")
    @ApiModelProperty(value = "联通满意度")
    private Double ltSatisfaction;

    /**
     * 联通排名（全国省排名）
     */
    @TableField(value = "lt_rank")
    @ApiModelProperty(value = "联通排名（全国省排名）")
    private Integer ltRank;

    /**
     * 移动全网最高值（全国最高值）
     */
    @TableField(value = "yd_max_value")
    @ApiModelProperty(value = "移动全网最高值（全国最高值）")
    private Double ydMaxValue;

    /**
     * 领先值（领先电信）
     */
    @TableField(value = "lead_value")
    @ApiModelProperty(value = "领先值（领先电信）")
    private Double leadValue;

    /**
     * 领先排名
     */
    @TableField(value = "lead_rank")
    @ApiModelProperty(value = "领先排名")
    private Integer leadRank;

    /**
     * 领先值全网最高
     */
    @TableField(value = "lead_max_value")
    @ApiModelProperty(value = "领先值全网最高")
    private Double leadMaxValue;

    /**
     * 排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value = "排序")
    private Integer sort;

    private static final long serialVersionUID = 1L;
}