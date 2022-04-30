package com.kplo.bms4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class common extends AppCompatActivity {
    TextView text;
    Button guard_data, notice;
    ImageButton person_info2;
    ImageButton logout;
    int trigger = 0; // 주민(0)과 보호자(1) 구분 플래그
String Role,name,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        Intent intent = getIntent();
        trigger = intent.getIntExtra("data",0);

        Role = intent.getStringExtra("Role");
        name= intent.getStringExtra("name");
        email = intent.getStringExtra("email");

        text = findViewById(R.id.toolbar_title);
        String vname;
        vname = "0마을" + "0님";
        text.setText(vname);

        guard_data = findViewById(R.id.guard_data);

        if (  trigger == 1)
            guard_data.setVisibility(View.VISIBLE); // 보호자일 때
        else
            guard_data.setVisibility(View.INVISIBLE); // 주민일 때

       /* if(view == logout) {

        }
        else if(view == notice ){
            intent = new Intent(this,broadcast_notice.class);
            startActivity(intent);
        } else if (view == person_info) {
            intent = new Intent(this,Person_infoActivity.class);
            intent.putExtra("data",trigger);
            startActivity(intent);
        }*/
        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        return null;
                    }
                });


                Intent intent2 = new Intent(common.this, MainActivity.class);
                startActivity(intent2);
                finish();


            }
        });


        notice = findViewById(R.id.notice);
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(common.this, broadcast_notice.class);
                startActivity(intent2);
            }
        });


        Button play=findViewById(R.id.playbtn);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        person_info2 = findViewById(R.id.person_info);
        person_info2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(common.this, Person_infoActivity.class);
                intent2.putExtra("Role",Role);
                intent2.putExtra("name",name);
                intent2.putExtra("email",email);
                startActivity(intent2);
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}