package com.alddeul.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.sdk.user.UserApiClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class manage_person extends AppCompatActivity /*implements AdapterView.OnItemClickListener*/ {

    ArrayList<HashMap<String, String>> ti_con;
    ArrayList<String> title, content;
    HashMap<String, String> map;
    String[] ti_data, id_data;
    Integer size;
    ListView listView;
    AlertDialog alertDialog;
    String vid2;
    ImageButton img;
    String title2,vname,name;
    private static String TAG = "broadcast";
    ImageButton imageButton, trash_bucket;
    ObjectMapper mapper = new ObjectMapper();
    ArrayList<HashMap<String, String>> map2;
    private RequestQueue mqueue;
    Intent intent2;
    TextView toolbar;
    MainActivity m = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_person);
        Intent intent = getIntent();
        vid2 = intent.getStringExtra("vid");
        vname = intent.getStringExtra("vname");
        name = intent.getStringExtra("name");
        Log.d("manageperson", "" + vid2);

        mqueue = Volley.newRequestQueue(this);


        toolbar=findViewById(R.id.toolbar_title);
        toolbar.setText(vname+"마을이장"+name +"님");

        imageButton = findViewById(R.id.plus);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent2 = new Intent(manage_person.this, manage_person_plus.class);
                intent2.putExtra("vid",vid2);
                intent2.putExtra("vname",vname);
                intent2.putExtra("name",name);
                startActivity(intent2);
            }
        });


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

                Intent intent = new Intent(manage_person.this, MainActivity.class);
                startActivity(intent);


                finish();


            }
        });


        listView = findViewById(R.id.person_list);


        String url = m.serverip+"api/villages/" + vid2 + "/users";
        Log.d(" ", "" + url);

/*
        try {

            List<Map<String, Object>> paramMap = new ObjectMapper().readValue(str, new TypeReference<List<Map<String, Object>>>() {
            });
            Log.d(" asdsadsad", " 121212" + paramMap.get(1).get("name"));

        } catch (IOException e) {
            Log.d("error2222", "");
            e.printStackTrace();

        }*/


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.isEmpty()) {
                    System.out.println("no data");

                    return;
                } else {

                    try {

                        List<Map<String, Object>> paramMap = new ObjectMapper().readValue(response, new TypeReference<List<Map<String, Object>>>() {
                        });

                        // Class class = new ObjectMapper().readValue(response, Class.class);

                        Log.d(" asdsadsad", " prm" + paramMap);
                        Log.d(" asdsadsad", " size" + paramMap.size());
                        size = paramMap.size();

                        String[] ti_data = new String[paramMap.size()];
                        String[] id_data = new String[paramMap.size()];

                        for (int i = 0; i < paramMap.size(); i++) {

                            ti_data[i] = paramMap.get(i).get("username").toString();
                            Log.d(TAG, " tidata2  " + ti_data[i]);

                            id_data[i] = paramMap.get(i).get("id").toString();

                        }

                        trash_bucket = findViewById(R.id.trash_bucket);
                        trash_bucket.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                intent2 = new Intent(manage_person.this, manage_person_delete.class);
                                intent2.putExtra("vid", vid2);
                                intent2.putExtra("id", id_data);

                                Log.d("vid", "vid" + vid2);
                                startActivity(intent2);

                            }
                        });


                        imageButton = findViewById(R.id.plus);
                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                intent2 = new Intent(manage_person.this, manage_person_plus.class);
                                intent2.putExtra("vid", vid2);


                                startActivity(intent2);
                            }
                        });


                        ArrayAdapter adapter = new ArrayAdapter(manage_person.this, R.layout.broadcast_content, R.id.simple_title, ti_data);
                        listView.setAdapter(adapter);


                    } catch (IOException e) {
                        Log.d("error2222", "");
                        e.printStackTrace();

                    }


                    Log.d(TAG, "handleSignInResult:size2 " + response);


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
}




