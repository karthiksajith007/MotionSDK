package code.gusturedetection.com.motiondetectorsdk;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

import code.gusturedetection.com.motiondetectorsdk.sdk.alerts.SpeechNotification;
import code.gusturedetection.com.motiondetectorsdk.sdk.motion.usecases.BicepCurlGestureTemplate;
import code.gusturedetection.com.motiondetectorsdk.sdk.motion.usecases.StarJumpGestureTemplate;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextToSpeech.OnInitListener, SpeechNotification.ISpeechManager {
    private TextToSpeech textToSpeech;

    private BicepCurlGestureTemplate bicepCurl;
    private StarJumpGestureTemplate starjumpGesture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getBicepCurlButton ().setOnClickListener(this);
        getStarJumpButton ().setOnClickListener(this);
        textToSpeech = new TextToSpeech(this, this);
        bicepCurl = new BicepCurlGestureTemplate(this);
        starjumpGesture = new StarJumpGestureTemplate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bicepCurlButton) {
            new GestureDialog(this, this, bicepCurl).show();
        } else if (view.getId() == R.id.starJumpButton) {
            new GestureDialog(this, this, starjumpGesture).show();
        }
    }
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.setLanguage(Locale.UK);
            textToSpeech.setSpeechRate(2f);
        }
    }

    /**
     * This method is called when speech operation is to be performed
     * @param speakText indicates the text of the speech
     * @return Nothing
     */
    @Override
    public void speak(String speakText) {
        if (textToSpeech !=null) {
            textToSpeech.speak(speakText, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
    private Button getBicepCurlButton () {
        return (Button)findViewById(R.id.bicepCurlButton);
    }
    private Button getStarJumpButton () {
        return (Button)findViewById(R.id.starJumpButton);
    }
}