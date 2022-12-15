package com.zukxu.algorithm.tree;

/**
 * @author xupu
 * @Description 树的遍历算法
 * @Date 2021-09-16 17:25
 */
public class BTreeTraversal {

    public static void main(String[] args) {
        Node head = initBTreeNode();
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

    private static Node initBTreeNode() {
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

    public static void preOrderRecur(Node head) {
        if(head == null) {
            return;
        }
        System.out.print(head.value + " ");
        preOrderRecur(head.left);
        preOrderRecur(head.right);
    }

    public static void inOrderRecur(Node head) {
        if(head == null) {
            return;
        }
        inOrderRecur(head.left);
        System.out.print(head.value + " ");
        inOrderRecur(head.right);
    }

    public static void backOrderRecur(Node head) {
        if(head == null) {
            return;
        }
        backOrderRecur(head.left);
        backOrderRecur(head.right);
        System.out.print(head.value + " ");
    }

}
