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
        Node current;

        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.element;
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current.element;
    }
    public Integer remove(int index) {
        Node toDelete;
        if (index < size / 2) {
            toDelete = head;
            for (int i = 0; i < index; i++) {
                toDelete = toDelete.next;
            }
        } else {
            toDelete = tail;
            for (int i = size - 1; i > index; i--) {
                toDelete = toDelete.prev;
            }
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
