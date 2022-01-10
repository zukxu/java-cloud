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
@TableName(value = "satisfaction_jt_zq")
public class SatisfactionJtZq implements Serializable {
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
     * 标识（jcr决策人 lxr联系人 zx专线 qk企宽 gyy公有云）
     */
    @TableField(value = "org_type")
    @ApiModelProperty(value = "标识（jcr决策人 lxr联系人 zx专线 qk企宽 gyy公有云）")
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
     * 上级指标名称
     */
    @TableField(value = "parent_kpi_name")
    @ApiModelProperty(value = "上级指标名称")
    private String parentKpiName;

    /**
     * 满意度
     */
    @TableField(value = "satisfaction")
    @ApiModelProperty(value = "满意度")
    private Double satisfaction;

    /**
     * 排名（全国省排名）
     */
    @TableField(value = "`rank`")
    @ApiModelProperty(value = "排名（全国省排名）")
    private Integer rank;

    /**
     * 全网最高值（全国最高值）
     */
    @TableField(value = "max_value")
    @ApiModelProperty(value = "全网最高值（全国最高值）")
    private String maxValue;

    /**
     * 排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value = "排序")
    private Integer sort;

    private static final long serialVersionUID = 1L;
}