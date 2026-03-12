package Q5;

import java.util.NoSuchElementException;

public class QueueIntegerLinkedList {

    private static class Node {
        Integer element;
        Node next;

        Node(Integer value) {
            this.element = value;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public void add(Integer element) {
        Node node = new Node(element);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public Integer remove() {
        if (head == null) throw new NoSuchElementException("Queue is empty");

        Integer value = head.element;
        head = head.next;
        size--;

        if (head == null) tail = null;
        return value;
    }

    public Integer peek() {
        if (head == null) throw new NoSuchElementException("Queue is empty");
        return head.element;
    }

    public int getSize() {
        return size;
    }
}