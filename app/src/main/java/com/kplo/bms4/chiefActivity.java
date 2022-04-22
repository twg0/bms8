package com.kplo.bms4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.kakao.sdk.user.UserApiClient;

import java.io.IOException;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class chiefActivity extends AppCompatActivity {

    TextView chieftxt;
    String TAG = "chief";
    ObjectMapper mapper = new ObjectMapper();
    Map<String,String> map;

    String id,username,Role;
    private RequestQueue mqueue;


    String Name;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chief);

        chieftxt=findViewById(R.id.chief);


        mqueue = Volley.newRequestQueue(this);


        Intent intent = getIntent();
        id=intent.getStringExtra("id");
        email=intent.getStringExtra("email");
        String url = " http://10.0.2.2:8080/user/" + email;

        String url2 = " http://10.0.2.2:8080/user/all";
        chieftxt.setText(email);


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


                Intent intent = new Intent(chiefActivity.this, MainActivity.class);


                startActivity(intent);
                finish();

            }

        });


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "handleSignInResult:repsonse " + response);


                if (response.isEmpty()) {
                    Log.d(TAG, "handleSignInResult:repsonse getout");

                    return;
                } else {

                    try {
                        map = mapper.readValue(response, Map.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    Log.d(TAG, "handleSignInResult:size2 " + map);


                    Role = map.get("role");

                    if (Role.equals("caregiver")) {
                        Log.d(TAG, "you are not chief ");

                        UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                return null;
                            }
                        });


                        Intent intent2 = new Intent(chiefActivity.this, MainActivity.class);
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
        });



        stringRequest.setTag(TAG);
        mqueue.add(stringRequest);


        Button broadcast_button = (Button) findViewById(R.id.broadcast);

        broadcast_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chiefActivity.this, broadcastActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);

            }

        });




        Button button = (Button) findViewById(R.id.board);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chiefActivity.this, chiefboard_activity.class);

                startActivity(intent);
            }

        });


        Button buttonstatement = (Button) findViewById(R.id.statement);

        buttonstatement.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chiefActivity.this, statementActivity.class);
                startActivity(intent);
            }

        });

        Button buttonmodify = (Button) findViewById(R.id.chiefmodify);

        buttonmodify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chiefActivity.this, chiefjoinActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("email",email);

                startActivity(intent);
            }

        });


        Button buttonregister = (Button) findViewById(R.id.register);

        buttonregister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chiefActivity.this, oldjoinActivity.class);


                startActivity(intent);
            }

        });


        Button vadd = (Button) findViewById(R.id.addvillage);

        vadd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chiefActivity.this, villageaddActivity.class);


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