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

    public static Node initBTreeNode() {
        Node head = new Node(5);
        head.left = new Node(3);
        head.right = new Node(8);
        head.left.left = new Node(2);
        head.left.right = new Node(4);
        head.left.left.left = new Node(1);
        head.right.left = new Node(7);
        head.right.left.left = new Node(6);
        head.right.right = new Node(10);
        head.right.right.left = new Node(9);
        head.right.right.right = new Node(11);
        return head;
    }
}