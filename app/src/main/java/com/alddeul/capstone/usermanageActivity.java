package com.alddeul.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.sdk.user.UserApiClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class usermanageActivity extends AppCompatActivity /*implements AdapterView.OnItemClickListener*/ {
    String vid2;

    ArrayList<HashMap<String, String>> ti_con;
    ArrayList<String> title, content;
    HashMap<String, String> map;
    String[] ti_data, con_data;
    Integer size;
    ListView listView;
    AlertDialog alertDialog;
    ImageButton img, plus2;
    String title2;
    private final static String TAG = "broadcast";
    MainActivity m = new MainActivity();

    ObjectMapper mapper = new ObjectMapper();
    ArrayList<HashMap<String, String>> map2;
    private RequestQueue mqueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usermanage);
        Intent intent=getIntent();
        vid2=intent.getStringExtra("vid");

        mqueue = Volley.newRequestQueue(this);
        plus2 = findViewById(R.id.plus);
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

                Intent intent = new Intent(usermanageActivity.this, MainActivity.class);
                startActivity(intent);


                finish();


            }
        });


        listView = findViewById(R.id.broad_list);





        String url = m.serverip+"villages/"+vid2+"/files";

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
                        Log.d(" asdsadsad", " prm" + paramMap);
                        Log.d(" asdsadsad", " size" + paramMap.size());
                        size = paramMap.size();

                        String[] ti_data = new String[paramMap.size()];
                        String[] con_data = new String[paramMap.size()];

                        for (int i = 0; i < paramMap.size(); i++) {

                            ti_data[i] = paramMap.get(i).get("title").toString();
                            Log.d(" asdsadsad", " emails  " + paramMap.get(i).get("email"));
                            Log.d(" asdsadsad", " tidata  " + ti_data[i]);

                            con_data[i] = paramMap.get(i).get("role").toString();
                            Log.d(" asdsadsad", " role  " + paramMap.get(i).get("role"));
                            Log.d(" asdsadsad", " condata  " + con_data[i]);

                        }

                        ArrayAdapter adapter = new ArrayAdapter(usermanageActivity.this, R.layout.user_content, R.id.simpleuser_title, ti_data);
                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                                Log.d(TAG, "flag2 ");
                                Log.d(" asdsadsad", " condata111  " + con_data[i]);
                                Log.d(" asdsadsad", " ta111  " + ti_data[i]);


                                AlertDialog.Builder builder = new AlertDialog.Builder(usermanageActivity.this);

                                builder.setTitle(ti_data[i] + " 방송 내용");
                                builder.setMessage(con_data[i]);
                                builder.setPositiveButton("확인", null);

                                alertDialog = builder.create();
                                alertDialog.show();

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


        plus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.d(TAG, "flag ");


            }
        });

/*

        try {
            map2=mapper.readValue(str, Map.class);
            title2=map2.get("title");

            Log.d(" "," "+title2);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

*/

    }


/*

    public static List<Map<String, Object>> getListMapFromJsonArray(JSONArray jsonArray )
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        if( jsonArray != null )
        {
            int jsonSize = jsonArray.size();
            for( int i = 0; i < jsonSize; i++ )
            {
                Map<String, Object> map = JsonUtil.getMapFromJsonObject( (JSONObject) jsonArray.get(i) );
                list.add( map );
            }
        }

        return list;
    }
*/


}