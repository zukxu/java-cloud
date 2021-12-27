package com.zukxu.algorithm.tree;

/**
 * @author xupu
 * @Description 树的遍历算法
 * @Date 2021-09-16 17:25
 */
public class BTreeTraversal {

    public static void main(String[] args) {
        Node head = Node.initBTreeNode();
        //前序遍历
        preOrderRecur(head);
        System.out.println();
        //中序遍历
        inOrderRecur(head);
        System.out.println();
        //后序遍历
        backOrderRecur(head);
        System.out.println();
    }

    public static void preOrderRecur(Node head) {
        if (head == null) {
            return;
        }
        System.out.print(head.value + " ");
        preOrderRecur(head.left);
        preOrderRecur(head.right);
    }

    public static void inOrderRecur(Node head) {
        if (head == null) {
            return;
        }
        inOrderRecur(head.left);
        System.out.print(head.value + " ");
        inOrderRecur(head.right);
    }

    public static void backOrderRecur(Node head) {
        if (head == null) {
            return;
        }
        backOrderRecur(head.left);
        backOrderRecur(head.right);
        System.out.print(head.value + " ");
    }
}
