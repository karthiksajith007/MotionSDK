package code.gusturedetection.com.motiondetectorsdk.sdk.alerts;

import android.os.Vibrator;
import android.speech.tts.TextToSpeech;

/**
 * Created by Preethumol on 13-02-2018.
 */

public class SpeechNotification implements IUserNotification {

    public interface ISpeechNotification {
        String getRoundCompletionText ();
        String getRoundHalfCompletionText ();
    }
    public interface ISpeechManager {
        void speak (String speakText);
    }

    private ISpeechManager iSpeechManager;
    private ISpeechNotification iSpeechNotification;

    public SpeechNotification (ISpeechManager iSpeechManager, ISpeechNotification iSpeechNotification) {
        this.iSpeechNotification = iSpeechNotification;
        this.iSpeechManager = iSpeechManager;
    }

    @Override
    public void onShortNotification() {
        if (iSpeechManager !=null && iSpeechNotification!=null) {
            iSpeechManager.speak(iSpeechNotification.getRoundHalfCompletionText());
        }
    }
    @Override
    public void onLongNotification() {
        if (iSpeechManager !=null && iSpeechNotification!=null) {
            iSpeechManager.speak(iSpeechNotification.getRoundCompletionText());
        }
    }
}
