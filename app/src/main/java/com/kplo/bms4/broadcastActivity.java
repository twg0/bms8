package com.kplo.bms4;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class broadcastActivity extends AppCompatActivity {
    Intent intent;
    SpeechRecognizer mRecognizer;
    Button sttBtn,record,stop;
    TextView textView;
    final int PERMISSION = 1;
    private static int microphone_permission_code=200;
    EditText contents;



    MediaRecorder recorder;
    MediaPlayer mediaPlayer;
    String filename;



    @Override protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);
        String[] permissions = {Manifest.permission.RECEIVE_SMS};

        if(ismicrophonepresent()){
            getmicrophonepermission();
        }




        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);
        contents=findViewById(R.id.edittext);


        record =findViewById(R.id.recordbutton);
    record.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            recordAudio();

        }
    });


        Button play =findViewById(R.id.playbutton);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playpressed();

            }
        });


        stop=findViewById(R.id.stopbutton);

    stop.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            stopRecording();

        }

    });

    String txt;
    txt=getrecordingfilepath().toString();
    Log.d( "hi",txt);

    }



    private boolean ismicrophonepresent(){
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            return true;
        }
        else{
            return false;
        }
    }

    private void    getmicrophonepermission(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO)
                ==PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.RECORD_AUDIO }, microphone_permission_code);
        }
    }



    public void recordAudio(){

        try {
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setOutputFile(getrecordingfilepath());

            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            recorder.prepare();
            recorder.start();
            Toast.makeText(this,"start",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void stopRecording(){
            recorder.stop();
            recorder.release();
            recorder = null;
        Toast.makeText(this,"stop",Toast.LENGTH_LONG).show();



    }

    private String getrecordingfilepath(){
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File musicdirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(musicdirectory,"test"+".mp3");
        return file.getPath();
    }


    public void playpressed(){
        try{

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(getrecordingfilepath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}
