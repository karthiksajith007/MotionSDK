package code.gusturedetection.com.motiondetectorsdk.sdk.motion.usecases;

import android.content.Context;
import android.hardware.Sensor;

import code.gusturedetection.com.motiondetectorsdk.sdk.beans.AccelerometerDataBean;
import code.gusturedetection.com.motiondetectorsdk.sdk.beans.AccelerometerDataListBean;
import code.gusturedetection.com.motiondetectorsdk.sdk.beans.LightSensorListBean;
import code.gusturedetection.com.motiondetectorsdk.sdk.motion.GestureMotionTemplate;

/**
 * Created by Preethumol on 12-02-2018.
 */

public class BicepCurlGestureTemplate extends GestureMotionTemplate {

    private AccelerometerDataListBean idealCaseAccelarometerDataBean;
    private int []sensorArray = new int[] {Sensor.TYPE_ACCELEROMETER};
    final private float TrackStartThreshold = 3f;//2.0f

    public BicepCurlGestureTemplate(Context context) {
        String jsonString = readAssestsFile ("BicepCurl.json", context);
        if (jsonString != null) {
            idealCaseAccelarometerDataBean = getAccelerometerDataBeanFromJSon(jsonString);
        }
    }

    @Override
    public boolean isStartPointValid(AccelerometerDataBean accelerometerDataBean) {
        boolean isValid = false;
        if (Math.abs(idealCaseAccelarometerDataBean.getxData().get(0)- accelerometerDataBean.getxData())
                <= TrackStartThreshold &&
                Math.abs(idealCaseAccelarometerDataBean.getyData().get(0)- accelerometerDataBean.getyData())
                        <= TrackStartThreshold &&
                Math.abs(idealCaseAccelarometerDataBean.getzData().get(0)- accelerometerDataBean.getzData())
                        <= TrackStartThreshold) {
            isValid = true;
        }
        return isValid;
    }

    @Override
    public boolean isEndPointValid(AccelerometerDataBean accelerometerDataBean) {
        boolean isValid = false;
        int size = idealCaseAccelarometerDataBean.getxData().size();
        if (Math.abs(idealCaseAccelarometerDataBean.getxData().get(size-1)- accelerometerDataBean.getxData())
                <= TrackStartThreshold &&
                Math.abs(idealCaseAccelarometerDataBean.getyData().get(size-1)- accelerometerDataBean.getyData())
                        <= TrackStartThreshold &&
                Math.abs(idealCaseAccelarometerDataBean.getzData().get(size-1)- accelerometerDataBean.getzData())
                        <= TrackStartThreshold) {
            isValid = true;
        }
        return isValid;
    }

    @Override
    public boolean isStartPointValid(LightSensorListBean lightSensorListBean) {
        return false;
    }

    @Override
    public boolean isEndPointValid(LightSensorListBean lightSensorListBean) {
        return false;
    }

    @Override
    public int[] requiredSensors() {
        return sensorArray;
    }

    @Override
    public String getRoundCompletionText() {
        return "Curl up now.";
    }

    @Override
    public String getRoundHalfCompletionText() {
        return "Curl down now.";
    }
}
