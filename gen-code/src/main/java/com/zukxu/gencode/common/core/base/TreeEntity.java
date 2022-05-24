package com.zukxu.gencode.common.core.base;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 树形基类
 * </p>
 *
 * @author xupu
 * @since 2021/12/2 16:26
 */
public class TreeEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 父ID
     */
    private Long parentId;
    /**
     * 父名称
     */
    private String parentName;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 祖级
     */
    private String ancestors;

    /**
     * 子级
     */
    private List<?> children = new ArrayList<>();

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getAncestors() {
        return ancestors;
    }

    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    public List<?> getChildren() {
        return children;
    }

    public void setChildren(List<?> children) {
        this.children = children;
    }
}
