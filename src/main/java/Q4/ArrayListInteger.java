package Q4;

public class ArrayListInteger {
    private Integer[] array;
    private int size;
    private int capacity;

    public ArrayListInteger(int capacity) {
        this.array = new Integer[capacity];
        this.size = 0;
        this.capacity = capacity;
    }

    public void add(int element){
        if (size == capacity) {
            capacity = capacity*2;
            Integer[] newArray = new Integer[capacity];

            for (int i = 0; i < array.length; i++) {
                newArray[i] = array[i];
            }
            this.array = newArray;
        }

        array[size] = element;
        size++;
    }

    public Integer get(int index){
        return array[index];
    }

    public Integer remove(int index){
        Integer removedElement = array[index];

        for (int i = index; i < size -1; i++) {
            array[i] = array[i+1];
        }
        array[size - 1] = null;
        size--;

        if (capacity/4 > size) {
            int newCapacity = capacity/2;
            Integer[] newArray = new Integer[newCapacity];
            for (int i = 0; i < size; i++) {
                newArray[i] = array[i];
            }
            this.array = newArray;
            this.capacity = newCapacity;
        }
        return removedElement;
    }

    public void trimSize(int size) {
        // уменьшить емкость до количества элементов в массиве
        Integer[] newArray = new Integer[size];

        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }
        this.array = newArray;
        this.capacity = size;
    }

    public int size(){
        return size;
    }

    public int getCapacity(){
        return capacity;
    }

    public void list() {
        for (int i = 0; i < size; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

}