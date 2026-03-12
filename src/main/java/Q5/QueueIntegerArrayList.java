package Q5;

import java.util.NoSuchElementException;

public class QueueIntegerArrayList {
    private Integer[] array;
    private int size;

    public QueueIntegerArrayList(int capacity) {
        this.array = new Integer[capacity];
        this.size = 0;
    }

    public void add(Integer element){
        if (size == array.length) {
            Integer[] newArray = new Integer[array.length * 2];
            for (int i = 0; i < size; i++) {
                newArray[i] = array[i];
            }
            array = newArray;
        }

        array[size] = element;
        size++;
    }

    public int remove() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }

        int firstElement = array[0];

        for (int i = 1; i < size; i++) {
            array[i - 1] = array[i];    // ЭТО НЕПРАВИЛЬНО!!!!!
        }
        size--;

        return firstElement;
    }

    public int peek() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        return array[0];
    }

    public int getSize(){
        return size;
    }
}
