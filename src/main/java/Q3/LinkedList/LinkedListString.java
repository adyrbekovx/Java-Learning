package Q3.LinkedList;

public class LinkedListString {
    public static class Node {
        String element;
        Node next;
        Node prev;

        Node(String element){
            this.element = element;
            this.next = null;
            this.prev = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public LinkedListString() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    public void add(String element) {
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
    public String get(int index) {
        LinkedListString.Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.element;
    }
    public String remove(int index) {
        LinkedListString.Node toDelete = head;
        for (int i = 0; i < index; i++) {
            toDelete = toDelete.next;
        }

        String element = toDelete.element;

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
