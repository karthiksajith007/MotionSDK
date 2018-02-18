package code.gusturedetection.com.motiondetectorsdk.sdk.alerts;

import android.os.Vibrator;

/**
 * Created by Preethumol on 13-02-2018.
 */

public class VibrationNotification implements IUserNotification {

    private Vibrator vibrator;
    private long[] shortPattern = {0, 100, 1000};
    private long[] longPattern = {0, 1000};

    public VibrationNotification (Vibrator vibrator) {
        this.vibrator = vibrator;
    }
    @Override
    public void onShortNotification() {
        vibrator.vibrate(/*shortPattern, */200);
    }
    @Override
    public void onLongNotification() {
        vibrator.vibrate(/*longPattern, */500);
    }
}
