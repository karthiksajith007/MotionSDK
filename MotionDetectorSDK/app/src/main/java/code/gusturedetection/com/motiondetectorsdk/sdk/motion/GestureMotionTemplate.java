package code.gusturedetection.com.motiondetectorsdk.sdk.motion;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import code.gusturedetection.com.motiondetectorsdk.sdk.alerts.SpeechNotification;
import code.gusturedetection.com.motiondetectorsdk.sdk.beans.AccelerometerDataListBean;
import code.gusturedetection.com.motiondetectorsdk.sdk.beans.LightSensorListBean;
import code.gusturedetection.com.motiondetectorsdk.sdk.motion.usecases.ILightGestureMotion;

/**
 * Created by Preethumol on 12-02-2018.
 */

abstract public class GestureMotionTemplate implements ILightGestureMotion, IAccelerometerGestureMotion, SpeechNotification.ISpeechNotification {


    /**
     * This method is called to read the assets file
     * @param fileName indicates the name of the assets file
     * @param context indicates the context of the activity
     * @return contents of the file
     */
    protected String readAssestsFile (String fileName, Context context) {
        StringBuffer lineDataBuffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(fileName), "UTF-8"));
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                lineDataBuffer.append (mLine);
            }
        } catch (IOException e) {
            lineDataBuffer = null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {}
            }
        }
        return lineDataBuffer.toString();
    }

    /**
     * This method is called to parse the accelerometer data list bean from jSon string
     * @param jSonString indicates the jSon String
     * @return accelerometer data bean
     */
    protected AccelerometerDataListBean getAccelerometerDataBeanFromJSon(String jSonString) {
        AccelerometerDataListBean accelerometerDataBean;
        try {
            JSONObject jsonObject = new JSONObject(jSonString);
            accelerometerDataBean = new AccelerometerDataListBean();
            accelerometerDataBean.setxData(convertJSONArrayToList ((JSONArray)jsonObject.get("x")));
            accelerometerDataBean.setyData(convertJSONArrayToList ((JSONArray)jsonObject.get("y")));
            accelerometerDataBean.setzData(convertJSONArrayToList ((JSONArray)jsonObject.get("z")));
        } catch (JSONException e) {
            e.printStackTrace();
            accelerometerDataBean = null;
        }
        return accelerometerDataBean;
    }

    /**
     * This method is called to parse the light sensor bean from jSon string
     * @param jSonString indicates the jSon String
     * @return light sensor bean
     */
    protected LightSensorListBean getLightSensorBeanFromJson (String jSonString) {
        LightSensorListBean lightSensorListBean;
        try {
            JSONObject jsonObject = new JSONObject(jSonString);
            lightSensorListBean = new LightSensorListBean(jsonObject.getInt("minLight"), jsonObject.getInt("minMaxLight"));
        } catch (JSONException e) {
            e.printStackTrace();
            lightSensorListBean = null;
        }
        return lightSensorListBean;
    }

    /**
     * This method is called to convert the JSON array to list
     * @param jsonArray indicates the jSon Array
     * @return converted list
     */
    private ArrayList<Float> convertJSONArrayToList (JSONArray jsonArray) {
        ArrayList<Float> resultList;
        try {
            resultList = new ArrayList<Float>(100);
            for (int index = 0; index < jsonArray.length(); index++) {
                resultList.add(Float.valueOf((float)jsonArray.getDouble(index)));
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
            resultList = null;
        }
        return resultList;
    }
}
