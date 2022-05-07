package com.kplo.bms4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.sdk.user.UserApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class addvillage extends AppCompatActivity {

    TextView chieftxt;
    String TAG = "chief";
    ObjectMapper mapper = new ObjectMapper();
    Map<String, String> map;

    String id, username, Role;
    private RequestQueue mqueue;

    String vid;
    String Name;
    EditText nickname, address, villid;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.addvillage);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        Long id2 = Long.parseLong(id);
        Button send = findViewById(R.id.reg_button);

        villid = findViewById(R.id.villageid2);
        String url = " http://10.0.2.2:8080/api/users/" + id2 + "/villages";


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vid2 = villid.getText().toString();
                Long vid3 = Long.parseLong(vid2);
                addvill(id, vid3, url);

            }
        });

    }

    public void addvill(String id, Long vid, String url2) {

        String TAG = "aaa";
        Log.d(" ", "id " + id);
        Log.d(" ", " " + url2);


        Log.d(" ", " vid " + vid);

        JSONObject js = new JSONObject();

        try {

            js.put("villageId", vid);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Make request for JSONObject
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url2, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString() + " i am queen");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
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


        Volley.newRequestQueue(this).add(jsonObjReq);


    }


}