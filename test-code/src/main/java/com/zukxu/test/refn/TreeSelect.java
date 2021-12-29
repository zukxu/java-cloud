package com.zukxu.test.refn;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Tree select树结构实体类
 *
 * @author zukxu
 */
@Data
@Accessors(chain = true)
public class TreeSelect implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 节点ID
     */
    private String id;

    /**
     * 节点名称
     */
    private String label;
    /**
     * 排序
     */
    private Integer sort;

    /**
     * 子节点
     */
    private List<TreeSelect> children;

    public TreeSelect() {}

    public TreeSelect(SatisfactionTSConfig ts) {
        this.id = ts.getId();
        this.label = ts.getTableHead();
        this.sort = ts.getSort();
        this.children = ts.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

}
