package com.kplo.bms4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class manage_person_delete extends AppCompatActivity implements View.OnClickListener {

    ListView listView;

    String vid;
    ArrayList<Delete_personVO> items;
    Button deleteBtn, closeBtn;
    private RequestQueue mqueue;
    Integer size;
    private static String TAG = "delete";
    private RequestQueue queue,queue2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_person_delete);
        queue2 = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        vid = intent.getStringExtra("vid");
        String[] id_data = intent.getStringArrayExtra("id");

        Log.d("manage", "vid" + vid);


        listView = findViewById(R.id.person_list);
///////////////////////////////////

        String url = " http://10.0.2.2:8080/api/villages/" + vid + "/users";
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

        mqueue = Volley.newRequestQueue(this);

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
                        String[] con_data = new String[paramMap.size()];

                        for (int i = 0; i < paramMap.size(); i++) {

                            ti_data[i] = paramMap.get(i).get("username").toString();
                            Log.d(TAG, " tidata2  " + ti_data[i]);
/*
                            con_data[i] = paramMap.get(i).get("contents").toString();
                            Log.d(TAG, " condata  " + con_data[i]);*/

                        }

                        items = new ArrayList<Delete_personVO>();
                        for (int i = 0; i < ti_data.length; i++) {
                            String s = ti_data[i];
                            boolean b = false;
                            Delete_personVO item = new Delete_personVO(b, s);
                            items.add(item);
                        }

                        MyAdapter adapter = new MyAdapter(items);

                        listView.setAdapter(adapter);

                        deleteBtn = findViewById(R.id.delete);
                        closeBtn = findViewById(R.id.close);

                        deleteBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Integer count;
                                Integer checked;
                                count = adapter.getCount();
                                checked = listView.getCheckedItemPosition();

                                if (count > 0) {

                                    if (checked > -1 && checked < count) {
                                        items.remove(checked);

                                        listView.clearChoices();

                                        adapter.notifyDataSetChanged();

                                    }

                                }
                                /////

                                for (int i = 0; i < count; i++) {
                                    if (adapter.isChecked(i)) {
                                        Log.d("delete", "this " + i);

//
                                        String url2 = " http://10.0.2.2:8080/api/users/"+id_data[i];



                                        StringRequest stringRequest2 = new StringRequest(Request.Method.DELETE, url2, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Log.d("er","hi"+response);



                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.d("error",error.getMessage());
                                            }
                                        }) {

                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                Map<String, String> params = new HashMap<String, String>();


                                                /*params.put("file_id",id);*/

                                                return params;
                                            }
                                        };


                                        stringRequest2.setTag(TAG);
                                        queue2.add(stringRequest2);


//////////////////////////////

                                    }

                                }
/*
Intent intent=new Intent(manage_person_delete.this,master.class);

                                startActivity(intent);*/



                            }
                        });


                        closeBtn.setOnClickListener(manage_person_delete.this);


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


        ///////////////////////

    }

    @Override
    public void onClick(View view) {
        if (view == closeBtn)
            finish();
    }
}