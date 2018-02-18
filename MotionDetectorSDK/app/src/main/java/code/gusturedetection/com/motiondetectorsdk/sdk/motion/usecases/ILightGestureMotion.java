package code.gusturedetection.com.motiondetectorsdk.sdk.motion.usecases;

import code.gusturedetection.com.motiondetectorsdk.sdk.beans.LightSensorListBean;

/**
 * Created by Preethumol on 17-02-2018.
 */

public interface ILightGestureMotion extends IGestureMotion {

    boolean isStartPointValid (LightSensorListBean lightSensorListBean);

    boolean isEndPointValid (LightSensorListBean lightSensorListBean);
}
