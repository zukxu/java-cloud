package com.zukxu.tree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 树形结构模型类
 *
 * @author zukxu
 * @since 2022-1-2-18:09:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeNode<T> {

    /**
     * 节点根数据
     */
    private T rootNode;

    /**
     * 节点内容
     */
    private List<TreeNode<T>> childrenNode = new ArrayList<>();

    /**
     * 获取子节点
     *
     * @param dataList 数据集合
     * @param idName   id字段名
     * @param pidName  pid字段名
     * @return 子节点集合
     */
    public List<TreeNode<T>> childrenNode(List<T> dataList, String idName, String pidName) {
        ConvertTree<T> convertTree = new ConvertTree<>();
        String idValue = convertTree.getFieldValue(rootNode, idName);
        List<T> collect = dataList.stream()
                .filter(t -> idValue.equals(convertTree.getFieldValue(rootNode, pidName))).collect(Collectors.toList());
        dataList.removeAll(collect);
        collect.forEach(t -> {
            TreeNode<T> treeNode = new TreeNode<>();
            treeNode.setRootNode(t);
            childrenNode.add(treeNode);
        });
        return childrenNode;
    }


}
