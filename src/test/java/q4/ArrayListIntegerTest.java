package q4;

public class ArrayListIntegerTest {
    public static void main(String[] args) {
        ArrayListInteger array = new ArrayListInteger(3);

        array.add(3);
        array.add(2);
        array.add(6);
        array.add(3);
        array.add(9);
        array.add(12);
        array.add(67);
        array.add(89);


        System.out.println("Первый вызов. Емкость: " + array.getCapacity());
        array.trimToSize(9);
        System.out.println("Количество элементов: " + array.size());
        System.out.println("Второй вызов. Емкость: " + array.getCapacity());
        array.add(29);
        array.add(89);
        array.add(125);
        System.out.println("Количество элементов: " + array.size());
        System.out.println("Третий вызов. Емкость: " + array.getCapacity());
        System.out.println(array.remove(1)); // удалил элемент 2
        System.out.println(array.remove(4)); // удалил элемент 12
        array.list();
        array.trimToSize(10);
        System.out.println("Четвертый вызов. Емкость: " + array.getCapacity());


    }
}
