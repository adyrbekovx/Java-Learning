package Q3.LinkedList;

public class Main {
    public static void main(String[] args) {
        LinkedListInteger list = new LinkedListInteger();

         list.add(2);
         list.add(4);
         list.add(5);
         list.add(7);
         list.add(9);
         list.add(3);

//         list.add("Nurel");
//         list.add("Volve");
//         list.add("Mancini");
//         list.add("Pep");

//        list.add(2.4);
//        list.add(14.6);
//        list.add(135.7);
//        list.add(0.5252);

        System.out.println("Size: " + list.size());

        System.out.println(list.remove(3));

        System.out.println("Size: " + list.size());

        System.out.println("Get: " + list.get(3));

        System.out.println(list.getCapacity());

    }
}
