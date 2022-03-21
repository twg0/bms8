package com.kplo.bms4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class caregiverloginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Intent intent = getIntent();


        Button button2 = (Button)findViewById(R.id.loginbutton);

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(caregiverloginActivity.this, caregiverActivity.class);
                startActivity(intent);
            }

        });


        Button button = (Button)findViewById(R.id.joinbutton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(caregiverloginActivity.this, caregiverjoinActivity.class);
                startActivity(intent);
            }

        });



    }
}