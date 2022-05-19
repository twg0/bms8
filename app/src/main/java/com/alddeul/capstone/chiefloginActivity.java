/*
package com.kplo.bms4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;


public class chiefloginActivity extends AppCompatActivity {

    */
/*
        GoogleSignInOptions gso;
        GoogleSignInClient gsc;
        ImageView googlebtn;*//*


    TextView txt;
    private Button kakaoAuth;
    private final static String TAG = "유저";
    String id,email,username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);



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
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(chiefloginActivity.this))
                {
                    UserApiClient.getInstance().loginWithKakaoTalk(chiefloginActivity.this, callback);


                }

                else
                {
                    UserApiClient.getInstance().loginWithKakaoAccount(chiefloginActivity.this, callback);


                }
            }

        });


        updateKakaoLoginUi();



        txt=findViewById(R.id.logintext);

        txt.setText("이장 로그인");

        Intent intent = getIntent();



        Button buttonhome= (Button)findViewById(R.id.homebutton);

        buttonhome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chiefloginActivity.this, MainActivity.class);
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

                    Intent intent = new Intent(chiefloginActivity.this, chiefActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("email",email);
                    intent.putExtra("username",username);


                    startActivity(intent);
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
