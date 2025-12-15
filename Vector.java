import java.util.Arrays;
public class Vector {
    private int[] vector = new int[2];

    public Vector(int [] array){
        vector = array;
    }


    public int get(int index){
        return vector[index];
    }
    public void set(int index, int value){
        vector[index] = value;
    }

    public int len(){
        return vector.length;
    }

    public void print(){
        System.out.println(Arrays.toString(vector));
    }

    public static int dot(Vector vect1, Vector vect2 ){
        int result = -1;
        if (vect1.len() == vect2.len()){
            result = 0;
            for (int i = 0; i < vect1.len(); i++){
                result += vect1.get(i) * vect2.get(i);
            }
        }
        return result;
    }
}
