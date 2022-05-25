package com.alddeul.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class manage_person_delete extends AppCompatActivity implements View.OnClickListener {

    ListView listView;

    String vid, vname, name;
    ArrayList<Delete_personVO> items;
    Button deleteBtn, closeBtn;
    private RequestQueue mqueue;
    Integer size;
    private static String TAG = "delete";
    private RequestQueue queue, queue2;
    MainActivity m = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_person_delete);
        queue2 = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        vid = intent.getStringExtra("vid");
        vname = intent.getStringExtra("vname");
        name = intent.getStringExtra("name");
        String[] id_data = intent.getStringArrayExtra("id");

        Log.d("manage", "vid" + vid);


        listView = findViewById(R.id.person_list);
///////////////////////////////////

        String url = m.serverip+"api/villages/" + vid + "/users";
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


                        for (int i = 0; i < paramMap.size(); i++) {

                            ti_data[i] = paramMap.get(i).get("username").toString();

                            Log.d(TAG, " tidata2  " + ti_data[i]);


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
                                        String url2 = m.serverip+"api/users/" + id_data[i];


                                        StringRequest stringRequest2 = new StringRequest(Request.Method.DELETE, url2, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Log.d("er", "hi" + response);

                                                for (int i = 0; i < paramMap.size(); i++)
                                                    ti_data[i] = null;

                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.d("error", error.getMessage());
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
                               /* Intent intent = new Intent(manage_person_delete.this, master.class);
                                intent.putExtra();
                                startActivity(intent);*/


                                finish();
                                Intent intent = new Intent(manage_person_delete.this, manage_person.class);
                                intent.putExtra("vid", vid);
                                intent.putExtra("vname", vname);
                                intent.putExtra("name", name);
                                startActivity(intent);

                            }
                        });


                        closeBtn.setOnClickListener(manage_person_delete. this);

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
        }) {
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


        ///////////////////////

    }

    @Override
    public void onClick(View view) {

        if (view == closeBtn) {
            finish();
            Log.d("managedelete", "close");
            Intent intent = new Intent(manage_person_delete.this, manage_person.class);
            intent.putExtra("vid", vid);
            intent.putExtra("vname", vname);
            intent.putExtra("name", name);
            startActivity(intent);
        }

    }

}