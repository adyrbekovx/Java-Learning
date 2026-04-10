package q2;

public class BinarySearchLowerBound {
    public static void main(String[] args) {
        int[] myArray = {1,3,3,3,4,4,4,4,6,9,11};  // массив с одинаковыми элементами
        int target = 3;
        int result = BinarySearch(myArray,target);
        System.out.println(result);
    }

    public static int BinarySearch(int[] array, int target){
        int left = 0;
        int right = array.length - 1;
        int result = -1;

        while (left <= right){
            int mid = (left + right) / 2;

            if (array[mid] == target){
                result = mid;
                right = mid - 1;
            } else if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }
}
