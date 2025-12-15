import java.util.Arrays;

public class Matrix {

    private int[][] matrix;

    public Matrix(int[][] matrix){
        this.matrix = matrix;
    }
    
    public int[] get(int index){
        return matrix[index];
    }

    public void set(int row, int col, int value){
        matrix[row][col] = value;
    }

    public int len(){
        return matrix.length;
    }

    public void print(){
        System.out.println(Arrays.deepToString(matrix));
    }

    //assumes uniform matrix 
    public static Matrix matrixMultiply(Matrix matrix1, Matrix matrix2){
        int[][] newMatrix = new int[matrix1.len()][matrix2.get(0).length];
        int row = 0;
        int col = 0;
        //iterate over evey row of the first matrix 
        for (int i = 0; i<matrix1.len(); i++){
            // this is our first vector for dot product
            Vector vect1 = new Vector(matrix1.get(i));
            //then we iterate over each column of the second index
            for (int j=0; j< matrix2.get(0).length; j++){
                int[] tempArray = new int[matrix2.len()];
                //we iterate over each row to create that second vector
                for (int k = 0; k< matrix2.len(); k++){
                    tempArray[k] = matrix2.get(k)[j];
                }
                Vector vect2 = new Vector(tempArray);
                newMatrix[row][col] = Vector.dot(vect1, vect2);
                col++;
            }
            col = 0;
            row++;
        }
        return new Matrix(newMatrix);
    }

    public static Vector matrixMultiply(Vector vector1, Matrix matrix2){
        int[] newVector = new int[matrix2.get(0).length];
        int col = 0;
        for (int j=0; j< matrix2.get(0).length; j++){
            int[] tempArray = new int[matrix2.len()];
            //we iterate over each row to create that second vector
            for (int k = 0; k< matrix2.len(); k++){
                tempArray[k] = matrix2.get(k)[j];
            }
            Vector vect2 = new Vector(tempArray);
            newVector[col] = Vector.dot(vector1, vect2);
            col++;
        }
        return new Vector(newVector);
    }
}
