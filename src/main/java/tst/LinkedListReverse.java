package tst;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class LinkedListReverse {

    static class Node {
        public Node(int val, Node next, Node prev) {
            this.val = val;
            this.next = next;
            this.prev = prev;
        }

        int val;
        Node next;
        Node prev;
    }

    public <T> void reverse(List<T> list) {
        Stack<T> stack = new Stack<>();
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            stack.push(iterator.next());
            iterator.remove();
        }
        while (!stack.empty()) {
            list.add(stack.pop());
        }
    }

    public Node reverseNode(Node firstNode) {
        assert firstNode != null;
        if (firstNode.next == null) return firstNode;

        Node current = firstNode;
        current.prev = current.next;
        current.next = null;
        current = current.prev;

        Node oldNext;
        while (current.next != null) {
            oldNext = current.next;
            current.next = current.prev;
            current.prev = oldNext;
            current = oldNext;
        }

        current.next = current.prev;
        current.prev = null;
        return current;
    }


    public static void main(String[] args) {

        /*LinkedList<Integer> integers = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            integers.add(i);
        }
        System.out.println(integers);
        new LinkedListReverse().reverse(integers);
        System.out.println(integers);*/

        Node node = new Node(1, null, null);
        Node current = node;
        for (int i = 2; i < 33; i++) {
            current.next = new Node(i, null, current);
            current = current.next;
        }
        current = node;
        while (current != null) {
            System.out.println(current.val);
            current = current.next;
        }

        System.out.println("======================");

        current = new LinkedListReverse().reverseNode(node);
        while (current != null) {
            System.out.println(current.val);
            current = current.next;
        }
    }
}
