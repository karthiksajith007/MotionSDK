package code.gusturedetection.com.motiondetectorsdk.sdk.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Preethumol on 11-02-2018.
 */

public class AccelerometerDataListBean {

    private List <Float> xData;
    private List <Float> yData;
    private List<Float> zData;

    public AccelerometerDataListBean() {
        xData = new ArrayList<>(50);
        yData = new ArrayList<>(50);
        zData = new ArrayList<>(50);
    }

    public List<Float> getxData() {
        return xData;
    }

    public void setxData(List<Float> xData) {
        this.xData = xData;
    }

    public List<Float> getyData() {
        return yData;
    }

    public void setyData(List<Float> yData) {
        this.yData = yData;
    }

    public List<Float> getzData() {
        return zData;
    }

    public void setzData(List<Float> zData) {
        this.zData = zData;
    }
}
