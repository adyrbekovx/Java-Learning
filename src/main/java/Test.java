import java.util.NoSuchElementException;

public class Test {
    private Integer[] array;
    private int size;
    private int head;
    private int tail;

    public Test(int capacity) {
        this.array = new Integer[capacity];
        this.size = 0;
        this.head = 0;
        this.tail = 0;
    }

    public void add(Integer element) {
        if (size == array.length) {
            Integer[] newArray = new Integer[array.length * 2];

            for (int i = 0; i < size; i++) {
                newArray[i] = array[(head + i) % array.length];
            }

            this.array = newArray;
            this.head = 0;
            this.tail = size;
        }

        array[tail] = element;
        // Вот тут должно быть что-то
        //
         //
        size++;
    }

    public int remove() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        int removedElement = array[head];

        // Здесь что-то
        //
         //
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