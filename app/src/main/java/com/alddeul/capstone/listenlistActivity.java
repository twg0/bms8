/*
package com.kplo.bms4;

import static android.speech.tts.TextToSpeech.ERROR;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class listenlistActivity extends AppCompatActivity  {

    private TextToSpeech tts;
    private Button speak_out;
    private EditText input_text;
    private String test;
    private TextView fir,sec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listenlist);


        fir=findViewById(R.id.first);
        fir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(listenlistActivity.this,listenActivity.class);
                startActivity(intent);
            }
        });

        sec=findViewById(R.id.second);
        sec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(listenlistActivity.this,listenActivity.class);
                startActivity(intent);
            }
        });

    }



}*/
