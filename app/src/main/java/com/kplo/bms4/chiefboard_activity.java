package com.kplo.bms4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.sdk.user.UserApiClient;
import com.kplo.bms4.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class chiefboard_activity extends AppCompatActivity {

    private Button btn;
    private RequestQueue mqueue;
    String TAG = "ch";
    ObjectMapper mapper = new ObjectMapper();
    Map<String, String> map[];
    String[] fileid, contents;
    Integer cnt = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);
        mqueue = Volley.newRequestQueue(this);

        String url = " http://10.0.2.2:8080/files/all";
        Log.d(TAG, "handle ");


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "handleSignInResult:repsonse11 " + response);


                if (response.isEmpty()) {
                    Log.d(TAG, "empty");

                    return;
                }

                else {

                    try {

                        while(cnt<2) {
                            map[cnt] = mapper.readValue(response, Map.class);
                            cnt++;
                        }

                    }

                    catch (IOException e) {
                        e.printStackTrace();
                    }


                    Log.d(TAG, "handleSignInResult:size2 " + map);

                    cnt=0;

        fileid = new String[2];
        contents = new String[2];

                    while(cnt<2) {


                        fileid[cnt] = map[cnt].get("fileid");
                        contents[cnt] = map[cnt].get("cotents");
                        cnt++;
                    }







                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });




            stringRequest.setTag(TAG);
            mqueue.add(stringRequest);




        //////////


        ListView listView = findViewById(R.id.listView);

        List<String> list = new ArrayList<>();
        Integer i = 0;

        while(i<cnt) {
            list.add(fileid[i]);
        }

/*
        fileid[0] = "hi";
        contents[0] = "by";
        list.add(fileid[0]);*/


        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position >= 0) {

                    Intent intent = new Intent(chiefboard_activity.this, positionlistActivity.class);
                    intent.putExtra("file_id", fileid[position]);
                    intent.putExtra("contents", contents[position]);

                    startActivity(intent);

                }


            }
        });


        btn = findViewById(R.id.reg_button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chiefboard_activity.this, broadcastActivity.class);
                startActivity(intent);
            }
        });


        Intent intent2 = getIntent();


    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mqueue != null) {
            mqueue.cancelAll(TAG);
        }
    }


}

