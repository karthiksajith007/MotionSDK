package code.gusturedetection.com.motiondetectorsdk.sdk.beans;

/**
 * Created by Preethumol on 15-02-2018.
 */

public class LightSensorListBean {
    private int minLight;
    private int maxMinLight;

    public LightSensorListBean(int minLight, int maxMinLight) {
        this.minLight = minLight;
        this.maxMinLight = maxMinLight;
    }

    public int getMinLight() {
        return minLight;
    }

    public void setMinLight(int minLight) {
        this.minLight = minLight;
    }

    public int getMaxMinLight() {
        return maxMinLight;
    }

    public void setMaxMinLight(int maxMinLight) {
        this.maxMinLight = maxMinLight;
    }
}
