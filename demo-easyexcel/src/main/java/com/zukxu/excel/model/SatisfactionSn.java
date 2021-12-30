package com.zukxu.excel.model;

import lombok.Builder;
import lombok.experimental.Accessors;

@Builder
@Accessors(chain = true)
public class SatisfactionSn {
    /**
     *
     */
    private String id;

    /**
     *
     */
    private String year;

    /**
     * 季度（001 002 003 004）
     */
    private String period;

    /**
     * 标识（phone 手机 5g 5G broadband 宽带）
     */
    private String orgType;

    /**
     * 地市编码
     */
    private String cityId;

    /**
     * 地市名称
     */
    private String cityName;

    /**
     * 指标名称
     */
    private String kpiName;

    /**
     * 满意度
     */
    private Double ydSatisfaction;

    /**
     *
     */
    private Double ltSatisfaction;

    /**
     *
     */
    private Double dxSatisfaction;

    /**
     * 排序
     */
    private Integer sort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getKpiName() {
        return kpiName;
    }

    public void setKpiName(String kpiName) {
        this.kpiName = kpiName;
    }

    public Double getYdSatisfaction() {
        return ydSatisfaction;
    }

    public void setYdSatisfaction(Double ydSatisfaction) {
        this.ydSatisfaction = ydSatisfaction;
    }

    public Double getLtSatisfaction() {
        return ltSatisfaction;
    }

    public void setLtSatisfaction(Double ltSatisfaction) {
        this.ltSatisfaction = ltSatisfaction;
    }

    public Double getDxSatisfaction() {
        return dxSatisfaction;
    }

    public void setDxSatisfaction(Double dxSatisfaction) {
        this.dxSatisfaction = dxSatisfaction;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}

