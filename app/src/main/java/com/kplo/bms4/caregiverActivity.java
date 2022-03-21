package com.kplo.bms4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class caregiverActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.caregiver);

        Intent intent = getIntent();

        Button logout_button = (Button) findViewById(R.id.logoutbutton);

        logout_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(caregiverActivity.this, MainActivity.class);
                startActivity(intent);
            }

        });



        Button button = (Button) findViewById(R.id.boardbutton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(caregiverActivity.this, caregiverboardActivity.class);
                startActivity(intent);
            }

        });


        Button weather_button = (Button) findViewById(R.id.weatherbutton);

        weather_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(caregiverActivity.this, weatherActivity.class);
                startActivity(intent);
            }

        });


    }
}