package com.java.algorithm;

/**
 * 怎么查询一个单向链表的倒数第3个节点
 */
public class LastKNode {

    public static void main(String[] args) {
        Node head = getSingleList();
        printList(head);
        int k = 3;
        head = new LastKNode().getLastKNode(head, k);
        System.out.println(head.data);

    }

    public Node getLastKNode(Node head, int k) {
        Node node = head;
        while (node.next != null && k > 0) {
            node = node.next;
            k--;
        }
        while (node != null) {
            node = node.next;
            head = head.next;
        }
        return head;
    }

    public static Node getSingleList() {
        Node head = new Node(3);
        Node node1 = new Node(6);
        Node node2 = new Node(8);
        Node node3 = new Node(6);
        Node node4 = new Node(2);
        head.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = null;
        return head;
    }

    public static void printList(Node node) {
        System.out.print("List:");
        while (node != null) {
            System.out.print(node.data + "-->");
            node = node.next;
        }
        System.out.println();
    }

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }
}
