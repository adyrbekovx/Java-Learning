package q7;

import java.util.Objects;

public class CustomHashMap {

    private static class Node {
        Node next;
        String key;
        Integer value;
        int hash;

        Node(int hash, String key, Integer value, Node next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
    private int size;
    private Node[] buckets;

    public CustomHashMap() {
        buckets = new Node[16];
    }

    public void put(String key, Integer value) {

        if (key == null) {
            throw new IllegalArgumentException("Ключ не может быть пустым");
        }

        if (size > 0.75 * buckets.length) {
            Node[] newBuckets = new Node[buckets.length * 2];

            for (int i = 0; i < buckets.length; i++) {
                Node current = buckets[i];
                while (current != null) {
                    Node nextNode = current.next;

                    int bucketIndex = Math.abs(current.hash % newBuckets.length);

                    current.next = newBuckets[bucketIndex];
                    newBuckets[bucketIndex] = current;

                    current = nextNode;
                }
            }
            buckets = newBuckets;
        }

        int hash = key.hashCode();
        int bucketIndex = Math.abs(hash % buckets.length);

        Node current = buckets[bucketIndex];

        if (current == null) {
            buckets[bucketIndex] = new Node(hash,key,value,null);
            size++;
            return;
        }

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                current.value = value;
                return;
            }
            if (current.next == null) {
                current.next = new Node(hash,key,value,null);
                size++;
                return;
            }

            current = current.next;
        }
    }

    public Integer get(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ не может быть пустым");
        }

        int hash = key.hashCode();
        int bucketIndex = Math.abs(hash % buckets.length);
        Node current = buckets[bucketIndex];

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public Integer remove(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ не может быть пустым");
        }

        int hash = key.hashCode();
        int bucketIndex = Math.abs(hash % buckets.length);

        Node current = buckets[bucketIndex];
        Node prev = null;

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                Integer removedVal = current.value;

                if (prev == null) {
                    buckets[bucketIndex] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return removedVal;
            }

            prev = current;
            current = current.next;
        }
        return null;
    }

    public int size(){
        return size;
    }
}
