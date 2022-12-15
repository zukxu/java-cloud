package com.zukxu.algorithm.tree;

/**
 * @author xupu
 * @Description 树结点结构
 * @Date 2021-09-16 17:28
 */
public class Node {
    public Object value;
    public Node left;
    public Node right;

    public Node(int data) {
        this.value = data;
    }

}