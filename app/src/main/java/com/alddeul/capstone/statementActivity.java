package com.alddeul.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class statementActivity extends AppCompatActivity {

ImageButton img;

TextView gas,earth,weird;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_data);

        gas=findViewById(R.id.gas);
        earth=findViewById(R.id.earth_quake);
        weird=findViewById(R.id.strange_act);






        Intent intent = getIntent();

        img = findViewById(R.id.logout);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        return null;
                    }
                });

                Intent intent = new Intent(statementActivity.this, MainActivity.class);
                startActivity(intent);


                finish();


            }
        });





    }
}
