package com.kplo.bms4;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.sdk.user.UserApiClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class master extends AppCompatActivity {

    ImageButton img, logout2;
    String name2, vname2, email, role, id, vid2;
    TextView toolbar;
    private RequestQueue mqueue, mqueue2;
    ObjectMapper mapper = new ObjectMapper();
    Map<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        Intent intent = getIntent();

        name2 = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        role = intent.getStringExtra("Role");
        id = intent.getStringExtra("id");

        toolbar = findViewById(R.id.toolbar_title);
        toolbar.setText( "00 마을이장" + name2 + "님");

        Log.d("im master", "im master");


        String url2 = " http://10.0.2.2:8080/api/users/" + email;
        String TAG = "master";

        mqueue = Volley.newRequestQueue(this);
        mqueue2 = Volley.newRequestQueue(this);

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
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


                    String Role;

                    Role = map.get("role");

                    if (Role.equals("ROLE_ADMIN")) {


                    } else {
                        Log.d("", "youare not chief");

                        UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                return null;
                            }
                        });

                        Intent intent = new Intent(master.this, MainActivity.class);
                        startActivity(intent);
                    }
                    Log.d(TAG, "role " + Role);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }){
            @Override //response를 UTF8로 변경해주는 소스코드
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, "UTF-8");
                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    // log error
                    return Response.error(new ParseError(e));
                } catch (Exception e) {
                    // log error
                    return Response.error(new ParseError(e));
                }
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

        };


        stringRequest2.setTag(TAG);
        mqueue2.add(stringRequest2);


/////////////////


        String url = " http://10.0.2.2:8080/api/admins/" + id + "/villages";


        mqueue = Volley.newRequestQueue(this);
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


                    vid2 = String.valueOf(map.get("id"));
                    vname2 = map.get("nickname");

                    toolbar.setText(vname2 + "마을이장" + name2 + "님");

                    Button manage = findViewById(R.id.manage_person_button);

                    manage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent2 = new Intent(master.this, manage_person.class);
                            intent2.putExtra("vid", vid2);
                            intent2.putExtra("vname", vname2);
                            intent2.putExtra("name", name2);
                            startActivity(intent2);

                        }
                    });


                    Button notice2 = findViewById(R.id.notice);
                    notice2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            Intent intent2 = new Intent(master.this, broadcast_notice_master.class);
                            intent2.putExtra("vid", vid2);
                            intent2.putExtra("vname", vname2);
                            intent2.putExtra("name", name2);
                            startActivity(intent2);
                        }
                    });

                    Button broad = findViewById(R.id.broadcast);
                    broad.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(master.this, broadcastActivity.class);
                            intent.putExtra("vid", vid2);
                            intent.putExtra("id", id);
                            intent.putExtra("vname", vname2);
                            intent.putExtra("name", name2);


                            startActivity(intent);
                        }
                    });


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }){
            @Override //response를 UTF8로 변경해주는 소스코드
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, "UTF-8");
                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    // log error
                    return Response.error(new ParseError(e));
                } catch (Exception e) {
                    // log error
                    return Response.error(new ParseError(e));
                }
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

        };

        stringRequest.setTag(TAG);
        mqueue.add(stringRequest);

        //////

        img = findViewById(R.id.person_info);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(master.this, Person_infomasterActivity.class);
                intent.putExtra("name", name2);
                intent.putExtra("email", email);
                intent.putExtra("Role", role);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });


        logout2 = findViewById(R.id.logout);
        logout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        return null;
                    }
                });


                Intent intent2 = new Intent(master.this, MainActivity.class);
                startActivity(intent2);
                finish();

            }
        });



        Log.d("im master", "im master2");

    }



}
