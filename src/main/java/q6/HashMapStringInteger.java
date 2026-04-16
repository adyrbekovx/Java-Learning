package q6;

import java.util.Objects;

public class HashMapStringInteger {

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
    private final Node[] buckets;

    public HashMapStringInteger() {
        buckets = new Node[7];
    }

    public void put(String key, Integer value) {
        // 0) Предусловия: buckets.size = 7
        // 1) "myKey" -> hash, hash = 10
        // 1.1) bucketIndex = hash % buckets.size, bucketIndex = 3
        // 2) buckets[bucketIndex].appendLast(-113) -> no key duplicates
        if (key == null) {
            throw new IllegalArgumentException("Ключ не может быть пустым");
        }

        int hash = key.hashCode();
        /*int bucketIndex = hash % buckets.length;*/
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

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        boolean isFirst = true;

        for (int i = 0; i < buckets.length; i++) {
            Node current = buckets[i];

            while (current != null) {
                if (!isFirst) {
                    result.append(", ");
                }

                result.append(current.key).append("=").append(current.value);

                isFirst = false;
                current = current.next;
            }
        }
        return result.toString();
    }
}
