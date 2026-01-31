package Q3.Arraylist;

public class ArrayListString {
    private final String[] array;
    private int size;


    public ArrayListString(int capacity) {
        this.array = new String[capacity];
        this.size = 0;
    }

    public void add(String element) {
        array[size] = element;
        size++;
    }

    public String get(int index) {
        return array[index];
    }

    public String remove(int index){
        String removedElements = array[index];

        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[size - 1] = null;
        size--;

        return removedElements;
    }
    public int size(){
        return size;
    }
    public int getCapacity(){
        return array.length;
    }
}
