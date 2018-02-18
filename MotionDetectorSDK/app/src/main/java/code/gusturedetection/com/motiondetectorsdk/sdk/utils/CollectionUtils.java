package code.gusturedetection.com.motiondetectorsdk.sdk.utils;

import java.util.List;

/**
 * Created by Preethumol on 11-02-2018.
 */

public class CollectionUtils {

    public static float[] convertFromList (List<Float> floatList) {
        float[] floatArray = new float[floatList.size()];
        for (int index=0 ; index<floatList.size() ; index++) {
            floatArray [index] = floatList.get(index);
        }
        return floatArray;
    }
}
