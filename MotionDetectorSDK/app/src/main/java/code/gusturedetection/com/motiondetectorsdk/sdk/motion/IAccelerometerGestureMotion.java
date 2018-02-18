package code.gusturedetection.com.motiondetectorsdk.sdk.motion;

import code.gusturedetection.com.motiondetectorsdk.sdk.beans.AccelerometerDataBean;
import code.gusturedetection.com.motiondetectorsdk.sdk.motion.usecases.IGestureMotion;

/**
 * Created by Preethumol on 12-02-2018.
 */

public interface IAccelerometerGestureMotion extends IGestureMotion {

    boolean isStartPointValid (AccelerometerDataBean accelerometerDataBean);

    boolean isEndPointValid (AccelerometerDataBean accelerometerDataBean);
}