package q1;

public class recursiveFibonacci {
    public static void main(String[] args) {
        System.out.println(Recursive(5));
    }

    public static int Recursive(int n) {
        if (n <= 1){
            return n;
        } else {
             return Recursive(n - 1) + Recursive(n - 2);
        }
    }
}
