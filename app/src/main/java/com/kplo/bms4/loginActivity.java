/*
package com.kplo.bms4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;


public class loginActivity extends AppCompatActivity {
    private final static String TAG = "로그인";
    TextView txt;
    private Button kakaoAuth;
    String id,email,username,Role;
    private RequestQueue mqueue;
    String url;
    ObjectMapper mapper = new ObjectMapper();
    Map<String,String> map;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mqueue = Volley.newRequestQueue(this);



/////////

        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>()
        {
            @Override public Unit invoke(OAuthToken oAuthToken, Throwable throwable)
            {
                if (oAuthToken != null)
            {
                Log.i("user", oAuthToken.getAccessToken() + " " + oAuthToken.getRefreshToken()); }
                if (throwable != null)
                    {
                        Log.w(TAG, "invoke: " + throwable.getLocalizedMessage());
                    }
                         updateKakaoLoginUi();
                return null;
            }
        };



        kakaoAuth = findViewById(R.id.kakao_auth_button);

        kakaoAuth.setOnClickListener(new View.OnClickListener() {
             @Override public void onClick(View v)
             {
                 if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(loginActivity.this))
                 {
                     UserApiClient.getInstance().loginWithKakaoTalk(loginActivity.this, callback);


                 }

                 else
                 {
                     UserApiClient.getInstance().loginWithKakaoAccount(loginActivity.this, callback);


                 }
             }

        });



        updateKakaoLoginUi();



        txt=findViewById(R.id.logintext);

        txt.setText(" 로그인");


        Intent intent = getIntent();
*/
/*
        Button button2 = (Button)findViewById(R.id.loginbutton);

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(caregiverloginActivity.this, caregiverActivity.class);
                startActivity(intent);
            }

        });*//*



        Button buttonhome= (Button)findViewById(R.id.homebutton);

        buttonhome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this, MainActivity.class);
                startActivity(intent);
            }

        });


    }


    public void updateKakaoLoginUi() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>()
         {
         @Override public Unit invoke(User user, Throwable throwable)
        {
            if (user != null) {
                 Log.i(TAG, "id " + user.getId());
                id=user.getId().toString();
                email=user.getKakaoAccount().getEmail();




                Log.i(TAG, "email " + user.getKakaoAccount().getEmail());
                url = " http://10.0.2.2:8080/user/" + email;

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "handleSignInResult:repsonse " + response);


                        if (response.isEmpty()) {
                            Log.d(TAG, "handleSignInResult:repsonse getout");

                            return;
                        }

                        else {

                            try {
                                map = mapper.readValue(response, Map.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }



                            Role = map.get("role");



                            if (Role.equals("chief")) {
                                Log.d(TAG, "you are  chief ");



*/
/*
                                Intent intent2 = new Intent(loginActivity.this, chiefActivity.class);
*//*

*/
/*                                intent2.putExtra("user_id",id);
                                intent2.putExtra("email",email);
                                intent2.putExtra("username",username);

                                startActivity(intent2);
                                finish();*//*


                            }

                            else if (Role.equals("caregiver")||Role.equals("user")) {




                                Intent intent2 = new Intent(loginActivity.this, caregiverActivity.class);
                                intent2.putExtra("id",id);
                                intent2.putExtra("email",email);
                                intent2.putExtra("username",username);

                                startActivity(intent2);
                                finish();

                            }




                            Log.d(TAG, "handleSignInResult:Role " + Role);

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

*/
/*

                Intent intent = new Intent(loginActivity.this, caregiverActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("email",email);
                intent.putExtra("username",username);
                startActivity(intent);
*//*






                 }

                if (throwable != null)
                 {
                 Log.w(TAG, "invoke: " + throwable.getLocalizedMessage());

                 }
                 return null;

        }

         });

    }



        }*/
