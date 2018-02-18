package code.gusturedetection.com.motiondetectorsdk;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import code.gusturedetection.com.motiondetectorsdk.sdk.SensorDataManager;
import code.gusturedetection.com.motiondetectorsdk.sdk.actioncycles.ActionCycleState;
import code.gusturedetection.com.motiondetectorsdk.sdk.alerts.SpeechNotification;
import code.gusturedetection.com.motiondetectorsdk.sdk.alerts.VibrationNotification;
import code.gusturedetection.com.motiondetectorsdk.sdk.motion.GestureMotionTemplate;

/**
 * Created by Preethumol on 17-02-2018.
 */

public class GestureDialog  extends Dialog implements android.view.View.OnClickListener ,DialogInterface.OnCancelListener,
        ActionCycleState.IActionCycleState, SensorDataManager.ISensorDataManager {

    private SensorDataManager sensorDataManager;
    private SpeechNotification.ISpeechManager iSpeechManager;
    private ActionCycleState actionCycleState;

    public GestureDialog(Context context, SpeechNotification.ISpeechManager iSpeechManager,
                            GestureMotionTemplate gestureMotion) {
        super(context);
        this.iSpeechManager = iSpeechManager;
        sensorDataManager = new SensorDataManager(gestureMotion, this);
        actionCycleState = new ActionCycleState(this);
        registerGestureMotionToSensorDataManager(gestureMotion);
    }

    /**
     * This method is called when the dialog is created
     * @return Nothing
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.gesture_dialog_layout);
        setOnCancelListener(this);
        getYesButton().setOnClickListener(this);
        getCountTextView ().setText("Count="+ actionCycleState.getCounter());
    }

    /**
     * This method is called when the dialog is closed
     * @return Nothing
     */
    @Override
    public void  onCancel(DialogInterface dialog)  {
        speakConclusionMessage ();
        unregisterAll ();
    }

    /**
     * This method is called to unregister all listeners
     * @return Nothing
     */
    private void unregisterAll () {
        actionCycleState.clearAllNotifications();
        sensorDataManager.unListenSensor();
    }

    /**
     * This method is called when a UI control is clicked
     * @param view indicates the clicked view
     * @return Nothing
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_yes) {
            speakConclusionMessage ();
            unregisterAll ();
            dismiss();
        }
    }

    /**
     * This method is called when half cycle is completed
     * @param cycleCount indicates the count of the cycle
     * @return Nothing
     */
    @Override
    public void onHalfCycleCompleted(int cycleCount) {

    }

    /**
     * This method is called when full cycle is completed
     * @param cycleCount indicates the count of the cycle
     * @return Nothing
     */
    @Override
    public void onFullCycleCompleted(int cycleCount) {
        iSpeechManager.speak(String.valueOf(cycleCount));
    }

    /**
     * This method is called when gesture motion is started
     * @return Nothing
     */
    @Override
    public void onGestureMotionStarted() {
        actionCycleState.cycleFullCompleted();
        getMessageTextView ().setText("Start point");
        getCountTextView ().setText("Count="+ actionCycleState.getCounter());
    }

    /**
     * This method is called when gesture motion is ended
     * @return Nothing
     */
    @Override
    public void onGestureMotionEnded() {
        actionCycleState.cycleHalfCompleted();
        getMessageTextView ().setText("End point");
    }

    /**
     * This method is called when area of gesture motion is invalid
     * @return Nothing
     */
    @Override
    public void onGestureMotionAreaInvalid() {
        getMessageTextView ().setText("Point not matching...");
    }

    /**
     * This method is called to register gesture motion in sensor data manager
     * @param gestureMotion indicates the gesture motion
     * @return Nothing
     */
    private void registerGestureMotionToSensorDataManager(GestureMotionTemplate gestureMotion) {
        sensorDataManager.unListenSensor();
        sensorDataManager.setAccGestureMotion(gestureMotion);
        sensorDataManager.listenSensor(getContext());

        actionCycleState.resetCounter();
        actionCycleState.addUserNotification(new VibrationNotification((Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE)),
                new SpeechNotification(iSpeechManager, gestureMotion));
    }

    /**
     * This method is called to speak conclusion message
     * @return Nothing
     */
    private void speakConclusionMessage () {
        if (actionCycleState.getCounter() != 0) {
            StringBuffer messageBuffer = new StringBuffer();
            messageBuffer.append("Completed ").append(actionCycleState.getCounter()).append(" cycles.");
            iSpeechManager.speak(messageBuffer.toString());
        }
    }

    private Button getYesButton () {
        return (Button)findViewById(R.id.btn_yes);
    }
    private TextView getMessageTextView () {
        return (TextView)findViewById(R.id.messageTextview);
    }
    private TextView getCountTextView () {
        return (TextView)findViewById(R.id.countTextview);
    }
}