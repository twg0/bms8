/*
package com.kplo.bms4;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import static android.speech.tts.TextToSpeech.ERROR;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class listenActivity extends AppCompatActivity  {

    private TextToSpeech tts;
    private Button speak_out;
    private EditText input_text;
    private String test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listen);

        speak_out = findViewById(R.id.startbutton);

        test="아이";

        speak_out.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) // LOLLIPOP이상 버전에서만 실행 가능
            @Override
            public void onClick(View v){
                speak(test);
            }
        });


        Button button =findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(listenActivity.this,caregiverActivity.class);
            startActivity(intent);
            }
        });
    }

    private void speak(String text) {
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != ERROR){
                    int result = tts.setLanguage(Locale.KOREA); // 언어 선택
                    if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                        Log.e("TTS", "This Language is not supported");
                    }else{
                        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                }else{
                    Log.e("TTS", "Initialization Failed!");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(tts!=null){ // 사용한 TTS객체 제거
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}*/
