package code.gusturedetection.com.motiondetectorsdk.sdk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import code.gusturedetection.com.motiondetectorsdk.sdk.beans.AccelerometerDataListBean;

/**
 * Created by Preethumol on 11-02-2018.
 */

public class SensorDataCollector {

    private static SensorDataCollector sensorDataCollector;

    private AccelerometerDataListBean accelarometerDataBean;

    private SensorDataCollector () {
        accelarometerDataBean = new AccelerometerDataListBean();
    }


    public static SensorDataCollector getInstance () {
        if (sensorDataCollector == null) {
            sensorDataCollector = new SensorDataCollector();
        }
        return sensorDataCollector;
    }

    public void resetDataInstances () {
        accelarometerDataBean = new AccelerometerDataListBean();
    }

    public JSONObject getJSonOfAccelarometerData () {
        JSONObject acceJsonObject = new JSONObject();
        try {
            acceJsonObject.put("x", convertListToJson (accelarometerDataBean.getxData()));
            acceJsonObject.put("y", convertListToJson (accelarometerDataBean.getyData()));
            acceJsonObject.put("z", convertListToJson (accelarometerDataBean.getzData()));
        } catch (JSONException e) {
            acceJsonObject = null;
            e.printStackTrace();
        }
        return acceJsonObject;
    }
    public void addAccelarometerData (Float xData, Float yData, Float zData) {
        accelarometerDataBean.getxData().add(xData);
        accelarometerDataBean.getyData().add(yData);
        accelarometerDataBean.getzData().add(zData);
    }

    public AccelerometerDataListBean getAccelarometerDataBean() {
        return accelarometerDataBean;
    }

    private JSONArray convertListToJson (List<Float> arrayList) {
        JSONArray arrayJSon = new JSONArray();
            for (Float floatElement : arrayList) {
                arrayJSon.put(floatElement);
            }
        return arrayJSon;
    }
}