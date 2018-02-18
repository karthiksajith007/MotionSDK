package code.gusturedetection.com.motiondetectorsdk.sdk.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Preethumol on 11-02-2018.
 */

public class AccelerometerDataBean {

    private Float xData;
    private Float yData;
    private Float zData;

    public AccelerometerDataBean() {}

    public Float getxData() {
        return xData;
    }

    public void setxData(Float xData) {
        this.xData = xData;
    }

    public Float getyData() {
        return yData;
    }

    public void setyData(Float yData) {
        this.yData = yData;
    }

    public Float getzData() {
        return zData;
    }

    public void setzData(Float zData) {
        this.zData = zData;
    }
}
