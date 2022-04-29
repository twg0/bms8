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

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
import com.kplo.bms4.caregiverloginActivity;
import com.kplo.bms4.chiefloginActivity;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;


public class MainActivity extends AppCompatActivity {
    private Button kakaoAuth;
    private final static String TAG = "signupkakao";
    String id, email;
    ObjectMapper mapper = new ObjectMapper();
    Map<String, String> map;
    String name;
    private RequestQueue mqueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        mqueue = Volley.newRequestQueue(this);


/*

        Button button2 = (Button)findViewById(R.id.forcaregiver);

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, caregiverloginActivity.class);
                startActivity(intent);
            }

        });



        Button button = (Button)findViewById(R.id.forcheif);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, chiefloginActivity.class);
                startActivity(intent);
            }

        });
*/

/*

        Button button3 = (Button)findViewById(R.id.signup);

        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, signupkakaoActivity.class);
                startActivity(intent);
            }

        });
*/


        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if (oAuthToken != null) {
                    Log.i("user", oAuthToken.getAccessToken() + " " + oAuthToken.getRefreshToken());
                }
                if (throwable != null) {
                    Log.w(TAG, "invoke: " + throwable.getLocalizedMessage());
                }
                updateKakaoLoginUi(mqueue);
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

        updateKakaoLoginUi(mqueue);


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



/*

    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }
*/

    public void updateKakaoLoginUi(RequestQueue mqueue) {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if (user != null) {
                    Log.i(TAG, "id " + user.getId());
                    id = user.getId().toString();
                    email = user.getKakaoAccount().getEmail();


                    Log.d(".", "." + email);
                    email="user1";
                    String url = " http://10.0.2.2:8080/users/" + email;
                    String TAG = "main";


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

                                Log.d(TAG, "handleSignInResult:size2 " + map);
                                String Role;

                                Role = map.get("role");
                                name=map.get("username");
                                Log.d(TAG, "handleSignInResult:size22222 " + Role);
//로그인 시 이사람의 이메일을 통해 정보를 입력받는다.  현재 서버에서 role 은 chief,user 둘 밖에 나타내지않는다...
                                if (Role.equals("ROLE_USER")) {
                                    Log.d(".","common");
                                    Intent intent = new Intent(MainActivity.this, common.class);

                                    intent.putExtra("Role", Role);
//지금상태로 common 으로 가면 이사람의 trigger 를 모른다.....ㅠ
                                    intent.putExtra("name", name);
                                    intent.putExtra("email", email);
                                    startActivity(intent);
                                }


                                if (Role.equals("ROLE_CHIEF")) {
                                    Intent intent = new Intent(MainActivity.this, master.class);
                                    intent.putExtra("id", id);
                                    intent.putExtra("email", email);
                                    startActivity(intent);
                                }


/*
                    else
                    {
                    }
*/
                   /* if (Role.equals("caregiver")) {
                        Log.d(TAG, "you are not chief ");

                        UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                return null;
                            }
                        });


                        Intent intent2 = new Intent(chiefActivity.this, MainActivity.class);
                        startActivity(intent2);
                        finish();
                    }*/

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(".", "error");
                            Intent intent = new Intent(MainActivity.this, input_inform.class);
                            intent.putExtra("id", id);
                            intent.putExtra("email", email);
                            startActivity(intent);



                        }
                    });

                    stringRequest.setTag(TAG);
                    mqueue.add(stringRequest);

                    //

                    Log.i(TAG, "email " + user.getKakaoAccount().getEmail());

                }

                if (throwable != null) {
                    Log.w(TAG, "invoke: " + throwable.getLocalizedMessage());

                }
                return null;
            }

        });

    }


}