package com.kplo.bms4;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;


public class textActivity extends AppCompatActivity {

    MediaPlayer player;
    int position = 0;
    VideoView videoView;
    Button button;
    String url;
    MediaController controller;
    String id, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.text);

        Intent intent = getIntent();

        Button button=findViewById(R.id.back);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(textActivity.this,caregiverboardActivity.class);
                        startActivity(intent1);
                    }
                });


    }
}