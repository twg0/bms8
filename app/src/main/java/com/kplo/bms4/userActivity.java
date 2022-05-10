package com.kplo.bms4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.kakao.sdk.user.UserApiClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class userActivity extends AppCompatActivity {

    TextView usertxt,test;
    String TAG = "userActivity";
    ObjectMapper mapper = new ObjectMapper();
    Map<String,String> map;


    String Name;
    String email;
    String id,username;
    String  age,phonenumber,Role,responseget;

    private RequestQueue mqueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);


        Intent intent = getIntent();
        id=intent.getStringExtra("id");
        email=intent.getStringExtra("email");

        Log.d(TAG, "handleSignInResult:personName2 " + id);


        usertxt = findViewById(R.id.usertext);
        usertxt.setText(email);



        Button logout_button = (Button) findViewById(R.id.logoutbutton);

        logout_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        return null;
                    }
                });


                Intent intent = new Intent(userActivity.this, userloginActivity.class);
                startActivity(intent);
                finish();
            }

        });

        mqueue = Volley.newRequestQueue(this);
        String url = " http://10.0.2.2:8080/user/" + email;


        Log.d(TAG, "handleSignInResult:personEmail " + email);
        Log.d(TAG, "handleSignInResult:personurl " + url);



        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "handleSignInResult:repsonse " + response);

                if (response.isEmpty()) {
                    Log.d(TAG, "handleSignInResult:repsonse getout" );

                    return;
                } else {

                    try {
                        map = mapper.readValue(response, Map.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    Log.d(TAG, "handleSignInResult:size2 " + map.get("role"));


                    Role = map.get("role");

                    if (Role.equals("chief") || Role.equals("caregiver")) {
                        Log.d(TAG, "you are not user ");

                        UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                return null;
                            }
                        });


                        Intent intent2 = new Intent(userActivity.this, userloginActivity.class);
                        startActivity(intent2);
                        finish();

                    }

                    Log.d(TAG, "handleSignInResult:Role " + Role);

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





        Button weather_button = (Button) findViewById(R.id.weatherbutton);

        weather_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(userActivity.this, weatherActivity.class);
                startActivity(intent);
            }

        });






    }



    @Override
    protected void onStop() {
        super.onStop();
        if (mqueue != null) {
            mqueue.cancelAll(TAG);
        }
    }

}
