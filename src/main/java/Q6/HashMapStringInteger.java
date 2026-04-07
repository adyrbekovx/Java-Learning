package Q6;

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
    private Node[] buckets;

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
        int bucketIndex = Math.abs(hash % buckets.length);

        Node current = buckets[bucketIndex];

        if (current == null) {
            buckets[bucketIndex] = new Node(hash,key,value,null);
            size++;
            return;
        }

        while (current != null) {
            if (current.key == key) {
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
            if (current.key == key) {
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
            if (current.key == key) {
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
