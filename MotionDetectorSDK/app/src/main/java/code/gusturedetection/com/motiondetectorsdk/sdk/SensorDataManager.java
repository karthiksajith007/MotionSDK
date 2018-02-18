package code.gusturedetection.com.motiondetectorsdk.sdk;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import org.json.JSONException;
import org.json.JSONObject;

import code.gusturedetection.com.motiondetectorsdk.sdk.beans.AccelerometerDataBean;
import code.gusturedetection.com.motiondetectorsdk.sdk.beans.LightSensorBean;
import code.gusturedetection.com.motiondetectorsdk.sdk.motion.IAccelerometerGestureMotion;
import code.gusturedetection.com.motiondetectorsdk.sdk.motion.usecases.ILightGestureMotion;

/**
 * Created by Preethumol on 11-02-2018.
 */

public class SensorDataManager implements SensorEventListener {

    public interface ISensorDataManager {
        void onGestureMotionStarted ();
        void onGestureMotionEnded ();
        void onGestureMotionAreaInvalid();
    }

    private AccelerometerDataBean accelerometerDataBean;
    private LightSensorBean lightSensorBean;

    private IAccelerometerGestureMotion accGestureMotion;
    private ILightGestureMotion lightGestureMotion;

    private ISensorDataManager iSensorDataManager;

    private SensorManager mSensorManager;

    public SensorDataManager (IAccelerometerGestureMotion gestureMotion, ISensorDataManager iSensorDataManager) {
        this.accGestureMotion = gestureMotion;
        this.iSensorDataManager = iSensorDataManager;
        accelerometerDataBean = new AccelerometerDataBean();
        lightSensorBean = new LightSensorBean();
    }

    /**
     * This method is used to register listeners of SensorManager.
     * @param context This is the activity context
     * @return Nothing
     */
    public void listenSensor (Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        for (int sensorID : accGestureMotion.requiredSensors()) {
            Sensor mSensor = mSensorManager.getDefaultSensor(sensorID/*Sensor.TYPE_ACCELEROMETER*/);
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }
    }


    /**
     * This method is used to unregister all sensor listeners
     * @return Nothing
     */
    public void unListenSensor () {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //if (sensorEvent.accuracy == SensorManager.SENSOR_STATUS_ACCURACY_HIGH) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accelerometerDataBean.setxData(sensorEvent.values[0]);
                accelerometerDataBean.setyData(sensorEvent.values[1]);
                accelerometerDataBean.setzData(sensorEvent.values[2]);

                if (accGestureMotion.isStartPointValid(accelerometerDataBean)) {
                    iSensorDataManager.onGestureMotionStarted();
                } else if (accGestureMotion.isEndPointValid(accelerometerDataBean)) {
                    iSensorDataManager.onGestureMotionEnded();
                } else {
                    iSensorDataManager.onGestureMotionAreaInvalid();
                }
            } else if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
                lightSensorBean.setLightValue((int)sensorEvent.values[0]);
            }
        //}
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

    /**
     * This Setter method for IAccelerometerGestureMotion type.
     * @param accGestureMotion should be client implementation of IAccelerometerGestureMotion
     * @return Nothing
     */
    public void setAccGestureMotion(IAccelerometerGestureMotion accGestureMotion) {
        this.accGestureMotion = accGestureMotion;
    }

    /**
     * this method creates the json object of current sensor data available.
     * @return created jsonObject
     */
    public JSONObject createJSonOfCurrentDataBean() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("x", accelerometerDataBean.getxData());
            jSONObject.put("y", accelerometerDataBean.getyData());
            jSONObject.put("z", accelerometerDataBean.getzData());
        } catch (JSONException e) {
            jSONObject = null;
        }
        return jSONObject;
    }
}
