package q5;

import java.util.NoSuchElementException;

public class CircleQueue {
    private Integer[] array;
    private int size;
    private int head;
    private int tail;
    private int capacity;

    public CircleQueue(int capacity) {
        this.array = new Integer[capacity];
        this.size = 0;
        this.head = 0;
        this.tail = 0;
        this.capacity = capacity;
    }

    public void add(Integer element) {
        if (size == capacity) {
            int newCapacity = capacity*2;
            Integer[] newArray = new Integer[newCapacity];

            for (int i = 0; i < size; i++) {
                newArray[i] = array[head];

            }
            this.array = newArray;
            this.tail = size;
            this.head = 0;
            this.capacity = newCapacity;

        }
        /*if head + 1 = capacity
            head = 0;
        else
            head = head + 1;
        */

        array[tail] = element;
        if (tail + 1 == capacity) {
            tail = 0;
        } else {
            tail = tail + 1;
        }
        size++;
    }

    public int remove() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        int removedElement = array[head];

        /*for (int i = 1; i < size; i++) {
            array[i - 1] = array[i];
        }


        if (head + 1 == capacity) {
            capacity = 0;
        } else {
            head = head + 1;
        }*/
        size--;

        return removedElement;
    }

    public int peek() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        return array[head];
    }

    public int getSize() {
        return size;
    }
}