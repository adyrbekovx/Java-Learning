package q6;

public class Test {
    public static void main(String[] args) {
        HashMapStringInteger hashmap = new HashMapStringInteger();

        hashmap.put("Nurel",1);
        hashmap.put("Sanjar",2);
        hashmap.put("Pep",3);
        hashmap.put("Nurel",4);
        hashmap.remove("Pep");
        hashmap.put("Zero",1);
        hashmap.put("Two",5);



        System.out.println(hashmap.get("Nurel"));
        System.out.println(hashmap.get("Pep"));
        System.out.println(hashmap.get("zero"));
        System.out.println(hashmap.get("Zero"));
        System.out.println(hashmap.size());
    }
}
