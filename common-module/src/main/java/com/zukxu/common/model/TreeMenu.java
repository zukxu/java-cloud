package com.zukxu.common.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Tree 树形菜单数据结构
 *
 * @author zukxu
 */
@Data
@Accessors(chain = true)
public class TreeMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 父菜单名称 */
    private String parentName;

    /** 父菜单ID */
    private String parentId;

    /** 显示顺序 */
    private Integer orderNum;

    /** 祖级列表 */
    private String ancestors;

    /** 子级 */
    private List<?> children = new ArrayList<>();

}
