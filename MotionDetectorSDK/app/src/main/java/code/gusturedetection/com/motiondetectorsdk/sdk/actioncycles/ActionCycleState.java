package code.gusturedetection.com.motiondetectorsdk.sdk.actioncycles;

import java.util.ArrayList;
import java.util.List;

import code.gusturedetection.com.motiondetectorsdk.sdk.alerts.IUserNotification;

/**
 * Created by Preethumol on 12-02-2018.
 */

public class ActionCycleState {

    public interface IActionCycleState {
        void onHalfCycleCompleted (int cycleCount);
        void onFullCycleCompleted (int cycleCount);
    }

    public enum CycleStage {CycleNotStarted, CycleHalf, CycleFull};

    private CycleStage cycleStage;
    private IActionCycleState iActionCycleState;
    private List<IUserNotification> iUserNotificationList;
    private int counter;


    public ActionCycleState(IActionCycleState iActionCycleState) {
        this.iActionCycleState = iActionCycleState;
        cycleStage = CycleStage.CycleNotStarted;
        iUserNotificationList = new ArrayList<IUserNotification>(5);
        counter = 0;
    }

    /**
     * This method added N number of user notification implementations.
     * @param iUserNotifications the variable length argument for N number of IUserNotification implementations
     * @return Nothing
     */
    public void addUserNotification (IUserNotification... iUserNotifications) {
        for (IUserNotification iUserNotification : iUserNotifications) {
            iUserNotificationList.add(iUserNotification);
        }
    }

    /**
     * clear all existing notification listeners.
     * @return Nothing
     */
    public void clearAllNotifications () {
        iUserNotificationList.clear();
    }

    /**
     * This method is called for indicating User that half of the Gesture cycle is completed.
     * @return Nothing
     */
    public void cycleHalfCompleted () {
        if (cycleStage == CycleStage.CycleNotStarted) {
            iActionCycleState.onHalfCycleCompleted(counter);
            cycleStage = CycleStage.CycleHalf;
            notifyAllNotificationListeners (false);
        }
    }

    /**
     * This method is called for indicating User that full of the Gesture cycle is completed.
     * @return Nothing
     */
    public void cycleFullCompleted () {
        if (cycleStage == CycleStage.CycleHalf) {
            incrementCounter();
            if (counter != 0) {
                iActionCycleState.onFullCycleCompleted(counter);
                notifyAllNotificationListeners (true);
            }
            cycleStage = CycleStage.CycleNotStarted;
        }
    }

    /**
     * This method is called to notify all notification listeners
     * @param isLong indicates if the notification is short or long
     * @return Nothing
     */
    private void notifyAllNotificationListeners (boolean isLong) {
        for (IUserNotification iUserNotification : iUserNotificationList) {
            if (iUserNotification != null) {
                if (isLong) {
                    iUserNotification.onLongNotification();
                } else {
                    iUserNotification.onShortNotification();
                }
            }
        }
    }

    /**
     * This method is called to reset the counter and clear all notification listeners
     * @return Nothing
     */
    synchronized public void resetCounter () {
        counter = 0;
        clearAllNotifications();
    }

    /**
     * This method is called to increment the counter
     * @return Nothing
     */
    synchronized private void incrementCounter () {
        counter++;
    }

    public int getCounter () {
        return counter;
    }
}