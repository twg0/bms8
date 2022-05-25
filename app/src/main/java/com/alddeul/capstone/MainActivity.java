package com.alddeul.capstone;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.*;

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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
/*import com.kplo.bms4.caregiverloginActivity;*/


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;


public class MainActivity extends AppCompatActivity {
    private Button kakaoAuth;
    private final static String TAG = "main";
    String email, phone;
    String id2;
    Map<String, String> map;
    String name, villname, vid2, Role, url2;
    private RequestQueue mqueue, mqueue2;
    ObjectMapper mapper = new ObjectMapper();
    String guardid, message, title;
    private RequestQueue queue;
    public static String  Cookies ="1";
    public static String  serverip ="http://3.212.91.66:8080/";
    public static String  local ="http://10.0.2.2:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*createNotificationChannel();*/
/*



        String token = FirebaseMessaging.getInstance().getToken().getResult();
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String token) {
                Log.d("find","find"+token);
            }
        });
*/


        /////
        Intent intent = getIntent();


        mqueue = Volley.newRequestQueue(this);
        mqueue2 = Volley.newRequestQueue(this);
        queue = Volley.newRequestQueue(this);

        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if (oAuthToken != null) {
                    Log.i("user2222222", oAuthToken.getAccessToken() + " " + oAuthToken.getRefreshToken());
                }
                if (throwable != null) {
                    Log.w(TAG, "invoke: " + throwable.getLocalizedMessage());
                }
                updateKakaoLoginUi();
                return null;
            }
        };


        kakaoAuth = findViewById(R.id.kakao_login);

        kakaoAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(MainActivity.this)) {
                    UserApiClient.getInstance().loginWithKakaoTalk(MainActivity.this, callback);
                    Log.d("main", "mainkakao clicked");

                } else {
                    UserApiClient.getInstance().loginWithKakaoAccount(MainActivity.this, callback);
                    Log.d("main", "mainkakao clicked");

                }


            }

        });


        updateKakaoLoginUi();

/*
        Button userbutton = (Button)findViewById(R.id.foruser);
        userbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, userloginActivity.class);
                startActivity(intent);
            }
        });
*/

        /*getHashKey();*/

    }


    public void updateKakaoLoginUi() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {

                if (user != null) {
                    /*id = user.getId().toString();*/
                    email = user.getKakaoAccount().getEmail();

                    Log.d(".", ".email " + email);

////////////////////////////////////////////
                    String TAG = "main";

                    emailregister();
/*
loginapi();
*/



                    /*choosecommonormaster();*/

//////////////////////////////////////////


                    ////////////////////////////////////////////////////////

                    /*jsonToObjectUrl();*/

                    Log.d("", "flag");

                    Log.i(TAG, "email " + user.getKakaoAccount().getEmail());

                } else {

                    Log.d("main", "Login to kakao first");
                }

                if (throwable != null) {
                    Log.w(TAG, "invoke: " + throwable.getLocalizedMessage());

                }
                return null;

            }

        });

    }

    public void emailregister() {

        String url3 = serverip+"api/users?email=" + email;
        Log.d("main", "url3" + url3);

        JSONObject js2 = new JSONObject();

        try {

            js2.put("email", email);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq2 = new JsonObjectRequest(
                Request.Method.POST, url3, js2,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString() + " i am queen");
                        Log.d(TAG, "emailregister");

                        ////// choosemastaeror 시작
                        choosecommonormaster();
                        /*loginapi();*/
////// choosemastaeror 끝


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "email register failed: " + error.getMessage());
                choosecommonormaster();

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


    public void loginapi() {

////////////
        String pwd = "password";
        Log.d("loginapi", "email " + email);
/*
        String url = " http://10.0.2.2:8080/api/login?email="+email+"&password="+pwd;
*/

        String url = serverip +"login";


        Log.d("main", "url" + url);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("main", "mainresponse" + response);
                Log.d(TAG, response.toString() + " i am queen");
                Log.d(TAG, "login api success");

                /*
                CookieManager cookieManager = User_Resource.cookieManager; //전역에서 동일한 주소로 세팅을 하기 위해 static 변수로 만들었습니다

                cookies = http_conn.getHeaderFields().get("Set-Cookie");
                cookieManager.put( url.toURI() , http_conn.getHeaderFields());
                List<HttpCookie> list_cookies = cookieManager.getCookieStore().getCookies();

                for(int i = 0; i < list_cookies.size(); i++) {
                    HttpCookie httpCookie = list_cookies.get(i);
                    cookieManager.getCookieStore().add(url.toURI(), httpCookie);

                }
*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.getMessage());
                Log.d("error login ", "login api error");
                choosecommonormaster();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);

                params.put("password", pwd);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded;");
                return headers;


            }
            ///
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                // since we don't know which of the two underlying network vehicles
                // will Volley use, we have to handle and store session cookies manually
                Log.i("response",response.headers.toString());
                Map<String, String> responseHeaders = response.headers;
                 Cookies = responseHeaders.get("Set-Cookie");
                Log.i("cookies",Cookies);
                return super.parseNetworkResponse(response);
            }
            ////

        };


        stringRequest.setTag(TAG);
        queue.add(stringRequest);


///////////////////


        //////////////////


    }


    public void choosecommonormaster() {


        String url2 = serverip+"api/users/" + email;
        Log.d(TAG, "url2  " + url2);


        StringRequest stringRequest3 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "handleSignInResult:repsonse " + response);

                if (response.isEmpty()) {
                    return;
                } else {

                    try {

                        map = mapper.readValue(response, Map.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String Role;

                    Role = map.get("role");
                    /*Role = "ROLE_CHIEF";*/
                    name = map.get("username");
                    id2 = String.valueOf(map.get("id"));


                    Log.d("main", "phone" + phone);

                    if (name.equals("TEMP_NAME")) {

                        Intent intent = new Intent(MainActivity.this, input_inform.class);
                        intent.putExtra("email", email);
                        Log.d("main", "id2222" + id2);
                        intent.putExtra("id", id2);
                        startActivity(intent);
                        return;
                    }


                    Integer size = 0;


                    // Class class = new ObjectMapper().readValue(response, Class.class);

                    if (Role.equals("ROLE_ADMIN"))//
                    {
                        Log.d("chief", "suces");
                        Intent intent = new Intent(MainActivity.this, master.class);
                        intent.putExtra("email", email);
                        intent.putExtra("id", id2);
                        intent.putExtra("name", name);

                        startActivity(intent);
                        return;

                    }
////////

                    String url4 = serverip+"api/users/" + id2 + "/ward";

                    StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url4, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "handleSignInResult:repsonse1212 " + response);

                            if (response.isEmpty()) {

                                return;
                            } else {

                                try {
                                    map = mapper.readValue(response, Map.class);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                Log.d(TAG, "handleSignInResult:size2 " + map);


                                guardid = String.valueOf(map.get("id"));


                                // Class class = new ObjectMapper().readValue(response, Class.class);


                                Log.d(" caregiver", "");

                                Intent intent = new Intent(MainActivity.this, commoncaregiver.class);
                                intent.putExtra("email", email);
                                intent.putExtra("Role", Role);
                                intent.putExtra("name", name);
                                intent.putExtra("id", id2);
                                intent.putExtra("message", message);
                                intent.putExtra("title", title);
                                intent.putExtra("guardid",guardid);
                                startActivity(intent);

                                return;


                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("main", "you dont have old");


                            Intent intent = new Intent(MainActivity.this, common.class);
                            intent.putExtra("email", email);
                            intent.putExtra("Role", Role);
                            intent.putExtra("name", name);
                            Log.d("send name", "send name" + name);
                            intent.putExtra("id", id2);
                            intent.putExtra("message", message);
                            intent.putExtra("title", title);
                            startActivity(intent);

                            return;
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


                    /////
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("main", "no userdata");


                Intent intent = new Intent(MainActivity.this, input_inform.class);
                intent.putExtra("email", email);
                Log.d("main", "id " + id2);
                intent.putExtra("id", id2);
                startActivity(intent);
                return;

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


    }

}




