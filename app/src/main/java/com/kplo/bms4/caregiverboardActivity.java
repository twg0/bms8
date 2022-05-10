/*
package com.kplo.bms4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class caregiverboardActivity extends AppCompatActivity {

private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);

        Intent intent2 = getIntent();

        button=findViewById(R.id.soundbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(caregiverboardActivity.this,listenActivity.class);
                startActivity(intent);
            }
        });




        Button button2=findViewById(R.id.textbutton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3=new Intent(caregiverboardActivity.this,textActivity.class);
                startActivity(intent3);
            }
        });

    }
}

*/
