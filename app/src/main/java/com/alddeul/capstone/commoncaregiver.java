package com.alddeul.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kakao.sdk.user.UserApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import okhttp3.Cookie;

public class commoncaregiver extends AppCompatActivity {
    TextView text;
    Button guard_data, notice, weather;
    ImageButton person_info2;
    ImageButton logout;
    Integer trigger = 0; // 주민(0)과 보호자(1) 구분 플래그
    String id, villname, vid, vname2, vname;
    ObjectMapper mapper = new ObjectMapper();
    Map<String, String> map;
    String Role, name, email;
    private RequestQueue mqueue, mqueue2, queue;
    String city;
    Integer size = 0;
    TextView weather2;
    String token, token2, message, title, guardid, lat, lon;
    MainActivity m = new MainActivity();
    ObjectMapper mapper2 = new ObjectMapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commoncaregiver);
        mqueue = Volley.newRequestQueue(this);
        mqueue2 = Volley.newRequestQueue(this);
        queue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        String TAG = "comoncare";

        id = intent.getStringExtra("id");

        Role = intent.getStringExtra("Role");
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        message = intent.getStringExtra("message");
        title = intent.getStringExtra("title");
        guardid = intent.getStringExtra("guardid");

        Log.d("commoncare", " body" + message);
        Log.d("commoncare", " id" + id);
        Log.d("commoncare", " cookie" + m.Cookies);
        Log.d("commoncare", " Role" + Role);
        text = findViewById(R.id.toolbar_title);
        gettoken();
/*

        Intent intent1=new Intent(getApplicationContext(),MyFirebaseMessagingService.class);
        startService(intent1);
*/

        /*


        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500); // 0.5초간 진동
*/

        weather2 = findViewById(R.id.weather);

        String url2 = m.serverip+"api/users/" + id + "/villages";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.isEmpty()) {

                    return;
                } else {

                    try {
                        JSONObject json = new JSONObject(response);
                        Log.d("", "res" + json);
                        JSONObject json2 = json.optJSONObject("location");
                        Log.d("com", "location " + json2);

                        try {

                            lon = (json2.optString("longitude"));
                            lat = (json2.optString("latitude"));



                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }


                        Log.d("com", "city" + city);

                        String url = " https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon="+lon+"&appid=b6fbd4253485afbad2502ce04bdb87ef";


/*
                        String url = " https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=b6fbd4253485afbad2502ce04bdb87ef";
*/
                        StringRequest stringRequest3 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.isEmpty()) {

                                    return;
                                } else {

                                    try {
                                        JSONObject json = new JSONObject(response);
                                        Log.d("", "res" + json);

                                        JSONObject json2 = json.optJSONObject("main");
                                        Log.d("comcare", "comcare" + json2);


                                        double temp2;
                                        temp2 = (json2.optDouble("temp")) - 273.15;
                                        int temp3;
                                        temp3 = (int) temp2;
                                        weather2.setText(String.valueOf(temp3) + "°C");

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    /*

                                     */


                                    /*
                                     */


                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("infoguard", "no weatherdata");


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

                        stringRequest3.setTag(TAG);
                        mqueue.add(stringRequest3);


/////


                        /////

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    /*

                     */


                    /*
                     */


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("infoguard", "no userdata");


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
        mqueue2.add(stringRequest);


        //////


        /*weather = findViewById(R.id.weatherbtn);*/

/*        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });*/


        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        return null;
                    }
                });


                Intent intent2 = new Intent(commoncaregiver.this, MainActivity.class);
                startActivity(intent2);
                finish();


            }
        });


        String url3 = m.serverip+"api/users/" + id + "/villages";
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.isEmpty()) {

                    return;
                } else {

                    try {
                        map = mapper.readValue(response, Map.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    Log.d("", "" + response);
                    vname = map.get("nickname");
                    vid = String.valueOf(map.get("id"));

                    vname2 = vname + "마을" + name + "님";

                    text.setText(vname2);

                    // Class class = new ObjectMapper().readValue(response, Class.class);


                    person_info2 = findViewById(R.id.person_info);
                    person_info2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            String url = m.serverip+"api/users/" + email;

                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("TAG ", "handleSignInResult:repsonse " + response);

                                    if (response.isEmpty()) {


                                        return;
                                    } else {

                                        try {
                                            map = mapper.readValue(response, Map.class);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        Log.d("TAG ", "handleSignInResult:size2 " + map);
                                        String Role;

                                        Role = map.get("role");
                                        name = map.get("username");
                                        id = String.valueOf(map.get("id"));
                                        Role = "ROLE_USER";
                                        Log.d("TAG ", "handleSignInResult:size22222 " + Role);

                                        Intent intent2 = new Intent(commoncaregiver.this, Person_info_guardActivity.class);
                                        intent2.putExtra("Role", Role);
                                        intent2.putExtra("name", name);
                                        intent2.putExtra("email", email);
                                        intent2.putExtra("id", id);
                                        intent2.putExtra("vname", vname);

                                        startActivity(intent2);
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

                            /*stringRequest.setTag(TAG);*/
                            mqueue2.add(stringRequest);


                        }
                    });


                    guard_data = findViewById(R.id.guard_data);


                    guard_data.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            getstatedata();
                            ///////
                          /*  Intent intent2 = new Intent(commoncaregiver.this, state_data.class);
                            intent2.putExtra("nickname", vname);
                            intent2.putExtra("name", name);
                            intent2.putExtra("guradId", guardid);


                            startActivity(intent2);*/

                        }
                    });


                    Button play = findViewById(R.id.play_button);
                    play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(commoncaregiver.this, broadcast_play.class);
                            intent.putExtra("vid", vid);
                            intent.putExtra("vname", vname);
                            intent.putExtra("name", name);

                            startActivity(intent);
                        }
                    });


                    notice = findViewById(R.id.notice);
                    notice.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent2 = new Intent(commoncaregiver.this, broadcast_notice.class);
                            intent2.putExtra("vid", vid);
                            intent2.putExtra("vname", vname);
                            intent2.putExtra("name", name);

                            Log.d("commoncare", "vname" + vname);

                            startActivity(intent2);
                        }
                    });


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("commoncaregiver", "no village");


/*

                            Intent intent = new Intent(MainActivity.this, input_inform.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
*/

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

        stringRequest2.setTag(TAG);
        mqueue2.add(stringRequest2);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public String gettoken() {

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("input", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();

/*
                        String msg = getString(R.string.msg_token_fmt, token);
*/
                        Log.d(" token value", token);

                        /*sendtoken();*/
                        sendearthquaketoken();
                    }
                });

        return token;

    }


    public void sendtoken() {

/*
        myFirebaseMessagingService=(MyFirebaseMessagingService)getApplicationContext();
*/
/*
        String url2 = " http://10.0.2.2:8080/api/notification/token?token="+token;
*/

        String url2 = m.serverip+"api/notification/token";

        String TAG = "token ";


        Log.d(" ", " token" + token);


        Log.d("main", "url2" + url2);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString() + " i am queen");
                Log.d(TAG, "send token success");


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error send token ", "comcare");


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("token", token);


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded;");
                return headers;
            }


        };


        stringRequest.setTag(TAG);
        queue.add(stringRequest);


    }


    public void sendearthquaketoken() {

/*
        myFirebaseMessagingService=(MyFirebaseMessagingService)getApplicationContext();
*/
/*
        String url2 = " http://10.0.2.2:8080/api/notification/token?token="+token;
*/

        String url2 = m.serverip+"api/notification/test";

        String TAG = "caregiver token test ";


        Log.d(" ", " token" + token);


        Log.d("commoncare", "url2" + url2);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("commoncare", response.toString() + " i am queen");
                Log.d(TAG, "send earthtoken success");


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error send earthtoken ", "");


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("token", token);


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");


                return headers;

            }


        };


        stringRequest.setTag(TAG);
        queue.add(stringRequest);


    }


    public void getstatedata() {


        String url2 = m.serverip+"api/messages/detect/" + guardid;

        String TAG = "caregiver ";


        Log.d("commoncare", "url2" + url2);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.isEmpty()) {

                    return;
                } else {

                    try {

                        List<Map<String, Object>> paramMap = new ObjectMapper().readValue(response, new TypeReference<List<Map<String, Object>>>() {
                        });


                        Log.d(" care", " prm" + paramMap);
                        Log.d(" care", " size" + paramMap.size());
                        size = paramMap.size();

                        String[] temp_data = new String[paramMap.size()];
                        String[] humid_data = new String[paramMap.size()];
                        String[] time_data = new String[paramMap.size()];

                        for (int i = 0; i < paramMap.size(); i++) {

                            temp_data[i] = paramMap.get(i).get("temperature").toString();
                            Log.d(TAG, " temp   " + temp_data[i]);

                            humid_data[i] = paramMap.get(i).get("humidity").toString();
                            Log.d(TAG, " humidity  " + humid_data[i]);
                            time_data[i] = paramMap.get(i).get("detect_time").toString();
                            Log.d(TAG, " time  " + time_data[i]);

                        }

                        Log.d("care", "respon " + response);

                        Intent intent2 = new Intent(commoncaregiver.this, state_data.class);
                        intent2.putExtra("nickname", vname);
                        intent2.putExtra("name", name);
                        intent2.putExtra("guradId", guardid);
                        intent2.putExtra("temperature",temp_data);
                        intent2.putExtra("humidity",humid_data);
                        intent2.putExtra("detect_time",time_data);
                        intent2.putExtra("size",size);


                        Log.d("caregiver", "guard" + guardid);
                        Log.d("caregiver", "size " + size);

                        startActivity(intent2);

                    } catch (IOException e) {
                        Log.d("care", "io exception");
                        e.printStackTrace();

                    }
                }
            }
        },new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse (VolleyError error){
                Log.d("caregiver", "no userdata");


            }
            })

            {
                @Override //response를 UTF8로 변경해주는 소스코드
                protected Response<String> parseNetworkResponse (NetworkResponse response){
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
                protected Map<String, String> getParams () throws AuthFailureError {
                return super.getParams();
            }

            }

            ;


        stringRequest.setTag(TAG);
        mqueue2.add(stringRequest);


        }


    }