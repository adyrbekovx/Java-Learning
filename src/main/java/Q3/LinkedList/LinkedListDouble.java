package Q3.LinkedList;

public class LinkedListDouble {
    public static class Node {
        Double element;
        Node next;
        Node prev;

        Node(Double element){
            this.element = element;
            this.next = null;
            this.prev = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public LinkedListDouble() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    public void add(Double element) {
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
    public Double get(int index) {
        LinkedListDouble.Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.element;
    }
    public Double remove(int index) {
        LinkedListDouble.Node toDelete = head;
        for (int i = 0; i < index; i++) {
            toDelete = toDelete.next;
        }

        Double element = toDelete.element;

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
