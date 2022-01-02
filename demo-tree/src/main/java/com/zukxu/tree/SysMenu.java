package com.zukxu.tree;

import com.zukxu.tree.annotations.TreeId;
import com.zukxu.tree.annotations.TreePid;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SysMenu {

    /**
     * id
     */
    @TreeId
    private String id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 父id
     */
    @TreePid
    private String pid;

}

