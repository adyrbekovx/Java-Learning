package q1;

public class NonRecursiveFibonacci {
    public static void main(String[] args) {
        System.out.println(nonRecursiveFibo(4));
    }

    public static int nonRecursiveFibo(int n) {
        if (n <= 1) {
            return n;
        }

        int previous = 0;
        int current = 1;

        for (int i = 2; i < n; i++) {
            int sum = current + previous;
            previous = current;
            current = sum;
        }
        return current;
    }
}
