package com.kplo.bms4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.kplo.bms4.caregiverloginActivity;
import com.kplo.bms4.chiefloginActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();




        Button button2 = (Button)findViewById(R.id.forcaregiver);

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, caregiverloginActivity.class);
                startActivity(intent);
            }

        });



        Button button = (Button)findViewById(R.id.forcheif);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, chiefloginActivity.class);
                startActivity(intent);
            }

        });



    }
}