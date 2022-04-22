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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class broadcastActivity extends AppCompatActivity {
    Intent intent;
    SpeechRecognizer mRecognizer;
    Button sttBtn,record,stop;
    TextView textView;
    final int PERMISSION = 1;
    private static int microphone_permission_code=200;
    EditText contents;
    private RequestQueue queue;
    private EditText villageids,zipcodes,citys,streets,fileid;


    String TAG="Activity";
    String url = " http://10.0.2.2:8080/user/post/";

    MediaRecorder recorder;
    MediaPlayer mediaPlayer;
    String filename;



    @Override protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);

        fileid=findViewById(R.id.fileid);

        queue = Volley.newRequestQueue(this);


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
                Log.d("d","start");
                recordAudio();

            }
        });


        Button send =findViewById(R.id.sendbtn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

////////////////////////
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        /*tv.setText(response);*/
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error",error.getMessage());
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
               /*
                params.put("guard_user_id", guard_user_id);
*/
                        params.put("fileid",fileid.getText().toString());

                        params.put("zipcode",zipcodes.getText().toString());
                        params.put("street",streets.getText().toString());
                        params.put("city",citys.getText().toString());
                        return params;
                    }
                };


                stringRequest.setTag(TAG);
                queue.add(stringRequest);





                Intent intent =new Intent(broadcastActivity.this,chiefActivity.class);
                startActivity(intent);
                
            }
        });


        stop=findViewById(R.id.stopbutton);

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("d","stop");

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


    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }

}