package com.zukxu.flowable.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xupu
 * 基础类
 * @since 2021-09-15 17:08
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 7590710377428269459L;
    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;

    //@TableField(exist = false)
    //private Map<String, Object> queryParams;
    //
    //public Map<String, Object> getQueryParams() {
    //    if (queryParams == null) {
    //        queryParams = new HashMap<>();
    //    }
    //    return queryParams;
    //}
    //
    //public void setQueryParams(Map<String, Object> queryParams) {
    //    this.queryParams = queryParams;
    //}
}

