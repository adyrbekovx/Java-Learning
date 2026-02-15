package Q3.LinkedList;

public class LinkedListInteger {
    public static class Node {
        Integer element;
        Node next;
        Node prev;

        Node(Integer element){
            this.element = element;
            this.next = null;
            this.prev = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public LinkedListInteger() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    public void add(Integer element) {
        Node node = new Node(element);

        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    public Integer get(int index) {
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.element;
    }

    public Integer remove(int index) {
        Node toDelete = head;
        for (int i = 0; i < index; i++) {
            toDelete = toDelete.next;
        }

        Integer element = toDelete.element;

        if (toDelete.prev != null) {
            toDelete.prev.next = toDelete.next;
        } else {
            head = toDelete.next;
        }

        if (toDelete.next != null) {
            toDelete.next.prev = toDelete.prev;
        } else {
            tail = toDelete.prev;
        }

        size--;
        return element;
    }

    public int size(){
        return size;
    }

    public int getCapacity(){
        return Integer.MAX_VALUE;
    }
}
