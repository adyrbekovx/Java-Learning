public class BinarySearch2DMatrix {
    public static void main(String[] args) {
        int[][] matrix = {{1,2,4,8},{10,11,12,13},{14,20,30,40}};
        int target = 10;

        BinarySearch2DMatrix binarySearch2DMatrix = new BinarySearch2DMatrix();
        boolean result = binarySearch2DMatrix.searchMatrix(matrix,target);

        System.out.println(result);
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int left = 0;
        int right = rows * cols - 1 ;

        while(left <= right) {
            int mid = (left + right) / 2;

            int row = mid / cols;
            int col = mid % cols;

            if(matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return false;
    }
}

