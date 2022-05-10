package com.kplo.bms4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.sdk.user.UserApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class addvillage extends AppCompatActivity {

    TextView chieftxt;
    String TAG = "chief";
    ObjectMapper mapper = new ObjectMapper();
    Map<String, String> map;
    ArrayList<villageselect_VO> items;

    String id2, username, Role, vid2;
    private RequestQueue mqueue, mqueue2;
    ListView listView;
    String vid;
    String Name;
    EditText nickname, address;
    String email;
    ImageButton select;
    Integer size = 0;
    String[] vid_data, vid_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.addvillage);
        Intent intent = getIntent();
        id2 = intent.getStringExtra("id");
        Long id = Long.parseLong(id2);
        mqueue2 = Volley.newRequestQueue(this);
        mqueue = Volley.newRequestQueue(this);
        Button send = findViewById(R.id.reg_button);
        listView = findViewById(R.id.villlist);


        String url = " http://10.0.2.2:8080/api/villages" ;

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


                        Log.d(" asdsadsad", " prm" + paramMap);
                        Log.d(" asdsadsad", " size" + paramMap.size());
                        size = paramMap.size();

                        String[] vid_data = new String[paramMap.size()];
                        String[] vid_name = new String[paramMap.size()];

                        for (int i = 0; i < paramMap.size(); i++) {

                            vid_data[i] = paramMap.get(i).get("id").toString();
                            Log.d(TAG, " vid  " + vid_data[i]);
/*

                            vid_name[i] = paramMap.get(i).get("address").toString();
                            Log.d(TAG, " vidname  " + vid_name[i]);
*/

                        }

                        items = new ArrayList<villageselect_VO>();
                        for (int i = 0; i < vid_data.length; i++) {
                            String s = vid_data[i];
                            boolean b = false;
                            villageselect_VO item = new villageselect_VO(b, s);
                            items.add(item);
                        }

                        MyvillageAdapter adapter = new MyvillageAdapter(items);

                        listView.setAdapter(adapter);


                        send.setOnClickListener(new View.OnClickListener() {
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
                                        Log.d("select", "this " + i);


             String url2 = " http://10.0.2.2:8080/api/users/" + id + "/villages?villageId=" + vid_data[i];
                                        Long vid = Long.parseLong(vid_data[i]);

                                        addvill(id,vid, url2);


//////////////////////////////

                                    }

                                }
/*
Intent intent=new Intent(manage_person_delete.this,master.class);

                                startActivity(intent);*/


                            }
                        });


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

/*

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Long vid = Long.parseLong(vid2);
                String url = " http://10.0.2.2:8080/api/users/" + id + "/villages?villageId=" + vid;

                addvill(id2, vid, url);

            }
        });
*/

    }


    public void addvill(Long id, Long vid, String url2) {

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