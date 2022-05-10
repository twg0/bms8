package com.kplo.bms4;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.os.*;

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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
/*import com.kplo.bms4.caregiverloginActivity;*/


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;


public class MainActivity extends AppCompatActivity {
    private Button kakaoAuth;
    private final static String TAG = "main";
    String email;
    String id2;
    Map<String, String> map;
    String name, villname, vid2, Role,url2;
    private RequestQueue mqueue, mqueue2;
    ObjectMapper mapper = new ObjectMapper();
    String guardid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        mqueue = Volley.newRequestQueue(this);
        mqueue2 = Volley.newRequestQueue(this);


        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if (oAuthToken != null) {
                    Log.i("user", oAuthToken.getAccessToken() + " " + oAuthToken.getRefreshToken());
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


                } else {
                    UserApiClient.getInstance().loginWithKakaoAccount(MainActivity.this, callback);


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
                    Log.i(TAG, "id " + user.getId());
                    /*id = user.getId().toString();*/
                    email = user.getKakaoAccount().getEmail();

                    Log.d(".", ".email " + email);

                    String url = " http://10.0.2.2:8080/api/users/" + email;
                    String TAG = "main";

/*

                    Intent intent = new Intent(MainActivity.this, common.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
*/


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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


                                Integer size = 0;


                                // Class class = new ObjectMapper().readValue(response, Class.class);

                Log.d("chief","");
                                if (Role.equals("ROLE_ADMIN"))//
                                {
                                    Log.d("chief","suces");
                                    Intent intent = new Intent(MainActivity.this, master.class);
                                    intent.putExtra("email", email);
                                    intent.putExtra("id", id2);
                                    intent.putExtra("name",name);

                                    startActivity(intent);
                                    return;

                                }
////////

                                url2 = " http://10.0.2.2:8080/api/users/" + id2 + "/ward";

                                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
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



Log.d(" caregiver","");

                                                Intent intent = new Intent(MainActivity.this, commoncaregiver.class);
                                                intent.putExtra("email", email);
                                                intent.putExtra("Role", Role);
                                                intent.putExtra("name", name);
                                            intent.putExtra("id", id2);

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
                                            Log.d("send name","send name"+name);
                                        intent.putExtra("id", id2);

                                        startActivity(intent);

                                        return;
/*

                            Intent intent = new Intent(MainActivity.this, input_inform.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
*/


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
                            startActivity(intent);
                            return;

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


                    ////////////////////////////////////////////////////////

                    /*jsonToObjectUrl();*/

                    Log.d("", "flag");
/*

                    if (name.isEmpty()) {
                        Intent intent = new Intent(MainActivity.this, input_inform.class);
                        intent.putExtra("email", email);
                        startActivity(intent);

                    } else {

                        if (Role.equals("ROLE_CHIEF"))//
                        {
                            Intent intent = new Intent(MainActivity.this, master.class);
                            intent.putExtra("email", email);
                            startActivity(intent);


                        }
                         else {//

*/
/*
                            if (  )//피보호자 리스트가 없는경우
                            {
                                Intent intent = new Intent(MainActivity.this, common.class);
                                intent.putExtra("email", email);
                                startActivity(intent);

                            } else {//있는경우

                                Intent intent = new Intent(MainActivity.this, common.class);
                                intent.putExtra("email", email);
                                startActivity(intent);
                            }
*/
                    /*



                        }


                    }
*/

                    Log.i(TAG, "email " + user.getKakaoAccount().getEmail());

                }

                if (throwable != null) {
                    Log.w(TAG, "invoke: " + throwable.getLocalizedMessage());

                }
                return null;

            }

        });

    }

/*

    private void jsonToObjectUrl() {
        final ObjectMapper mapper = new ObjectMapper();

        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    email="user1";
                    userinfo info = mapper.readValue(new URL("http://10.0.2.2:8080/api/users/" + email), userinfo.class);

                    Log.d("json string -> object\n", " " + info.getRole());


                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            Log.d("json string -> object\n", " " + info.getRole());
name=info.getName();
                            if (name.isEmpty()) {
                                Intent intent = new Intent(MainActivity.this, input_inform.class);
                                intent.putExtra("email", email);
                                startActivity(intent);

                            }

                            else{


*/
/*

                                if (  )//피보호자 리스트가 없는경우
                                {
                                    Intent intent = new Intent(MainActivity.this, common.class);
                                    intent.putExtra("email", email);
                                    startActivity(intent);

                                } else {//있는경우

                                    Intent intent = new Intent(MainActivity.this, common.class);
                                    intent.putExtra("email", email);
                                    startActivity(intent);
                                }
*//*



                            }




                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

*/

}
