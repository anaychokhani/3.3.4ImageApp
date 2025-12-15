public class UnitTest {

    public static void main(String[] args){
        // Unit test 1: basic matrix multiplication test
        int[][] arr1 = { {0,-1},{1,0}};
        Matrix mat1 = new Matrix(arr1);
        Matrix mat2 = new Matrix(arr1);

        Matrix matrix3 = Matrix.matrixMultiply(mat1, mat2);
        System.out.println("Matrix Multiplication test result:");
        matrix3.print();

        // Unit test 2:vector and matrix multiplication test
        int[] arr2 = {0,90};
        Vector testVect = new Vector(arr2);
        System.out.println("Vector Matrix Multiplication test result:");
        Vector resultVect = Matrix.matrixMultiply(testVect, mat1);
        resultVect.print();

        // unit test 3: dot product test
        int[] arr3 = {1,2,3};
        int[] arr4 = {4,5,6};
        Vector vect1 = new Vector(arr3);
        Vector vect2 = new Vector(arr4);
        int dotProduct = Vector.dot(vect1, vect2);
        System.out.println("Dot Product test result:");
        System.out.println(dotProduct);

        //unit test 4: edge case dot product test
        int[] arr5 = {1,2};
        int[] arr6 = {4,5,6};
        Vector vect3 = new Vector(arr5);
        Vector vect4 = new Vector(arr6);    
        try {
            int dotProductEdge = Vector.dot(vect3, vect4);
            System.out.println("Edge Case Dot Product test result:");
            System.out.println(dotProductEdge);
        } catch (IllegalArgumentException e) {
            System.out.println("Edge Case Dot Product test result: " + e.getMessage());
        }

        //unit test 5: edge case matrix multiplication test
        int[][] arr7 = { {1,2,3},{4,5,6},{7,8,9} };
        Matrix mat3 = new Matrix(arr7);
        try {
            Matrix matrixEdge = Matrix.matrixMultiply(mat1, mat3);
            System.out.println("Edge Case Matrix Multiplication test result:");
            matrixEdge.print();
        } catch (IllegalArgumentException e) {
            System.out.println("Edge Case Matrix Multiplication test result: " + e.getMessage());
        }

        //unit test 6: edge case vector-matrix multiplication test
        try {
            Vector vectorEdge = Matrix.matrixMultiply(testVect, mat3);
            System.out.println("Edge Case Vector-Matrix Multiplication test result:");
            vectorEdge.print();
        } catch (IllegalArgumentException e) {
            System.out.println("Edge Case Vector-Matrix Multiplication test result: " + e.getMessage());
        }

        //unit test 7: getters setters Matrix test
        System.out.println("Matrix Getter/Setter test result:");
        System.out.println("Original value at (0,1): " + mat3.get(0)[1]);
        mat3.set(0, 1, 99);
        System.out.println("New value at (0,1): " + mat3.get(0)[1]);

    
    }
}
