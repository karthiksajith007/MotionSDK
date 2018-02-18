package code.gusturedetection.com.motiondetectorsdk.sdk;

/**
 * Created by Preethumol on 11-02-2018.
 */

public class EditDistanceMatrix {

    /*private float [][] matrix;

    public EditDistanceMatrix (int size) {
        matrix = new float[size+1][size+1];
    }

    public float getEditDistance (float []idealData, float []realtimeData) {
        float distance = 0;
        for (int index1=0 ; index1<idealData.length ; index1++) {
            for (int index2=0 ; index2<idealData.length ; index2++) {

            }
        }
        return distance;
    }*/

    private float min(float x,float y,float z)
    {
        if (x<=y && x<=z) return x;
        if (y<=x && y<=z) return y;
        else return z;
    }

    public float editDist(float []str1 , float []str2 , int m, int n) {
        // If first string is empty, the only option is to
        // insert all characters of second string into first
        if (m == 0) return n;

        // If second string is empty, the only option is to
        // remove all characters of first string
        if (n == 0) return m;

        // If last characters of two strings are same, nothing
        // much to do. Ignore last characters and get count for
        // remaining strings.
        if (str1[m-1] == str2[n-1])
            return editDist(str1, str2, m-1, n-1);

        // If last characters are not same, consider all three
        // operations on last character of first string, recursively
        // compute minimum cost for all three operations and take
        // minimum of three values.
        return 1 + min ( editDist(str1,  str2, m, n-1),    // Insert
                editDist(str1,  str2, m-1, n),   // Remove
                editDist(str1,  str2, m-1, n-1) // Replace
        );
    }
}
