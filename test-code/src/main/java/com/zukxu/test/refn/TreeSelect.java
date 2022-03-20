package com.zukxu.test.refn;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
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
    private String value;


    /**
     * 子节点
     */
    private List<TreeSelect> children;

    public TreeSelect() {}

    public TreeSelect(ReFnEntity rf) {
        this.id = rf.getId();
        this.value = rf.getTitle();
        if(CollectionUtil.isNotEmpty(rf.getChildren())) {
            this.children = rf.getChildren()
                              .stream()
                              .filter(ObjectUtil::isNotNull)
                              .map(TreeSelect::new)
                              .collect(Collectors.toList());
        } else {
            this.children = CollectionUtil.newArrayList();
        }
    }

}
