package com.kplo.bms4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.sdk.user.UserApiClient;

import java.io.IOException;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class common extends AppCompatActivity {
    TextView text;
    Button guard_data, notice,weather;
    ImageButton person_info2;
    ImageButton logout;
    Integer trigger = 0; // 주민(0)과 보호자(1) 구분 플래그
    String id,villname;
    ObjectMapper mapper = new ObjectMapper();
    Map<String, String> map;
    String Role,name,email;
    private RequestQueue mqueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        mqueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        trigger = intent.getIntExtra("data",1);

        id = intent.getStringExtra("id");

        Role = intent.getStringExtra("Role");
        name= intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        villname = intent.getStringExtra("vname");
        Log.d("ddd2333333"," "+villname);
        Log.d("ddd2"," "+id);

Log.d("ddd"," "+Role);
        text = findViewById(R.id.toolbar_title);
        String vname;

        vname = villname+"마을" + name+"님";
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



        weather=findViewById(R.id.weatherbtn);

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = " http://10.0.2.2:8080/api/users/" + id+"/villages";
                String TAG = "main";


                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "handleSignInResult:repsonse " + response);

                        if (response.isEmpty()) {


                            return;
                        } else {

                            try {
                                map = mapper.readValue(response, Map.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Log.d(TAG, "handleSignInResult:size2 " + map);
                            String Role;

                            Role = map.get("role");
                            name=map.get("username");

                            Role="ROLE_USER";
                            Log.d(TAG, "handleSignInResult:size22222 " + Role);

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {



                    }
                });

                stringRequest.setTag(TAG);
                mqueue.add(stringRequest);


            }
        });




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