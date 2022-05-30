package com.alddeul.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kakao.sdk.user.UserApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class oldjoin extends AppCompatActivity {
    TextView text;
    Button guard_data, notice, weather;
    ImageButton person_info2;
    ImageButton logout;
    Integer trigger = 0; // 주민(0)과 보호자(1) 구분 플래그
    String id, villname, vid, vname2, vname, main;
    ObjectMapper mapper = new ObjectMapper();
    ObjectMapper mapper2 = new ObjectMapper();
    String city, token;
    MainActivity m = new MainActivity();
    EditText oldid, olddevice;
    Map<String, String> map, map2;
    String Role, name, email, message, title, lat, lon;
    private RequestQueue mqueue, mqueue2, queue;
    TextView weather2;
    Button send;
String TAG="oldjoin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oldjoin);
        mqueue = Volley.newRequestQueue(this);
        mqueue2 = Volley.newRequestQueue(this);
        queue = Volley.newRequestQueue(this);
        send = findViewById(R.id.sendbutton);
        oldid = findViewById(R.id.userid);
        olddevice = findViewById(R.id.deviceID);

        String TAG = "oldjoin";


        Log.d(TAG, " id" + id);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();

            }
        });


    }

    public void send() {

        String oldid2=oldid.getText().toString();
        String olddevice2=olddevice.getText().toString();

        String url3 = m.serverip + "api/devices/" + olddevice2+ "/users?userId="+oldid2 ;
        JSONObject js2 = new JSONObject();

        try {

            js2.put("userId", oldid2);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq2 = new JsonObjectRequest(
                Request.Method.POST, url3, js2,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString() + " i am queen");
                        Log.d(TAG, "register success");


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "email register failed: " + error.getMessage());

                /*loginapi();*/
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }


        };


        Volley.newRequestQueue(this).add(jsonObjReq2);
    }


}