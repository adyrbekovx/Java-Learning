package Q2;

public class BinarySearch {

    public static void main(String[] args) {
        // int[] myArray = {};  // пустой массив.
        // int[] myArray = {1}; // массив с одним элементом.
        // int[] myArray = {1,4,9};   // массив с тремя элементами.
        int[] myArray = {1,2,4,5,7,9,11,14,17,20,23}; // четное/нечетное число элементов.
        int target = 14;

        int result = binarySearch(myArray,target);

        System.out.println(result);
    }

    public static int binarySearch(int[] array, int target){
        int left = 0;
        int right = array.length - 1;

        while (left <= right){
            int mid =  (left + right) / 2;

            if (array[mid] == target){
                return mid;
            } else if (array[mid] < target){
                left = mid + 1;
            } else {
                 right = mid - 1;
            }
        }
        return -1;
    }
}
