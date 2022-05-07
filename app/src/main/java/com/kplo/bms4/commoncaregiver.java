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

public class commoncaregiver extends AppCompatActivity {
    TextView text;
    Button guard_data, notice, weather;
    ImageButton person_info2;
    ImageButton logout;
    Integer trigger = 0; // 주민(0)과 보호자(1) 구분 플래그
    String id, villname, vid, vname2,vname;
    ObjectMapper mapper = new ObjectMapper();
    Map<String, String> map;
    String Role, name, email;
    private RequestQueue mqueue, mqueue2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commoncaregiver);
        mqueue = Volley.newRequestQueue(this);
        mqueue2 = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        String TAG = "comon";

        id = intent.getStringExtra("id");

        Role = intent.getStringExtra("Role");
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");

        Log.d("common", " id" + id);

        Log.d("common", " Role" + Role);
        text = findViewById(R.id.toolbar_title);





        weather = findViewById(R.id.weatherbtn);

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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


                Intent intent2 = new Intent(commoncaregiver.this, MainActivity.class);
                startActivity(intent2);
                finish();


            }
        });



        String url2 = " http://10.0.2.2:8080/api/users/" + id + "/villages";
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.isEmpty()) {

                    return;
                } else {

                    try {
                        map = mapper.readValue(response, Map.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    Log.d("", "" + response);
                    vname = map.get("nickname");
                    vid = String.valueOf(map.get("id"));

                    vname2 = vname + "마을" + name + "님";

                    text.setText(vname2);

                    // Class class = new ObjectMapper().readValue(response, Class.class);


                    guard_data = findViewById(R.id.guard_data);


                    guard_data.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            ///////
                            Intent intent2 = new Intent(commoncaregiver.this, state_data.class);
                            intent2.putExtra("nickname", vname);
                            intent2.putExtra("name", name);
                            startActivity(intent2);

                        }
                    });



                    Button play = findViewById(R.id.play_button);
                    play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(commoncaregiver.this, broadcast_play.class);
                            intent.putExtra("vid", vid);
                            intent.putExtra("vname", vname);
                            startActivity(intent);
                        }
                    });



                    notice = findViewById(R.id.notice);
                    notice.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent2 = new Intent(commoncaregiver.this, broadcast_notice.class);
                            intent2.putExtra("vid", vid);
                            intent2.putExtra("vname", vname);
                            intent2.putExtra("name", name);

                            Log.d("common", "vname" + vname);

                            startActivity(intent2);
                        }
                    });





                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("commoncaregiver", "no village");




/*

                            Intent intent = new Intent(MainActivity.this, input_inform.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
*/


            }
        });

        stringRequest2.setTag(TAG);
        mqueue2.add(stringRequest2);


        person_info2 = findViewById(R.id.person_info);
        person_info2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                String url = " http://10.0.2.2:8080/api/users/" + email;

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "handleSignInResult:repsonse " + response);

                        if (response.isEmpty()) {


                            return;
                        } else {

                            try {
                                map = mapper.readValue(response, Map.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Log.d("TAG", "handleSignInResult:size2 " + map);
                            String Role;

                            Role = map.get("role");
                            name = map.get("username");
                            id = String.valueOf(map.get("id"));
                            Role = "ROLE_USER";
                            Log.d("TAG", "handleSignInResult:size22222 " + Role);

                            Intent intent2 = new Intent(commoncaregiver.this, Person_info_guardActivity.class);
                            intent2.putExtra("Role", Role);
                            intent2.putExtra("name", name);
                            intent2.putExtra("email", email);
                            intent2.putExtra("id", id);

                            startActivity(intent2);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });

                /*stringRequest.setTag(TAG);*/
                mqueue2.add(stringRequest);


            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}