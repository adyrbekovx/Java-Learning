package Q6;

public class HashMapStringDouble {

    private static class Node {
        Node next;
        String key;
        Double value;
        int hash;

        Node(int hash, String key, Double value, Node next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
    private int size;
    private Node[] buckets;

    public HashMapStringDouble() {
        buckets = new Node[5];
    }

    public void put(String key, Double value) {
        // 0) Предусловия: buckets.size = 7
        // 1) "myKey" -> hash, hash = 10
        // 1.1) bucketIndex = hash % buckets.size, bucketIndex = 3
        // 2) buckets[bucketIndex].appendLast(-113) -> no key duplicates
        if (key == null) {
            throw new IllegalArgumentException("Ключ не может быть пустым");
        }

        int hash = key.hashCode();
        int bucketIndex = hash % buckets.length;

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

    public Double get(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ не может быть пустым");
        }

        int hash = key.hashCode();
        int bucketIndex = hash % buckets.length;
        Node current = buckets[bucketIndex];

        while (current != null) {
            if (current.key == key) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public Double remove(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ не может быть пустым");
        }

        int hash = key.hashCode();
        int bucketIndex = hash % buckets.length;

        Node current = buckets[bucketIndex];
        Node prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                Double removedVal = current.value;

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
