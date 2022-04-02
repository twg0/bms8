package com.kplo.bms4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.kakao.sdk.user.UserApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class caregiverActivity extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TextView caregivertxt,test;
    String TAG = "caregiverActivity";
    HashMap<String,String> map2 = new HashMap<>();

    String Name;
    String email;
    String id,username;
    String  age,phonenumber,Role,responseget;
    private RequestQueue mqueue;
    int size=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.caregiver);
/*
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);*/



        Intent intent = getIntent();
        id=intent.getStringExtra("id");
        email=intent.getStringExtra("email");

        Log.d(TAG, "handleSignInResult:personName2 " + id);


        caregivertxt = findViewById(R.id.caregiver);
        caregivertxt.setText(email);



        Button logout_button = (Button) findViewById(R.id.logoutbutton);

        logout_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        return null;
                    }
                });


                Intent intent = new Intent(caregiverActivity.this, caregiverloginActivity.class);
                startActivity(intent);
                finish();
            }

        });

        mqueue = Volley.newRequestQueue(this);
        String url = " http://10.0.2.2:8080/user/getuserId/" + id;

        String url2 = " http://10.0.2.2:8080/user/all";

        Log.d(TAG, "handleSignInResult:personEmail " + email);
        Log.d(TAG, "handleSignInResult:personurl " + url);
/*

        if(  ) {
            Toast.makeText(getApplicationContext(), "you are not caregiver", Toast.LENGTH_SHORT).show();
            signout();
        }
*/


/*


        final JsonObjectRequest jsonrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    Role=response.getString("Role");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
*/

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "handleSignInResult:repsonse " + response);


                responseget=response.toString();

                size = 1;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        stringRequest.setTag(TAG);
        mqueue.add(stringRequest);




        Button button = (Button) findViewById(R.id.boardbutton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(caregiverActivity.this, caregiverboardActivity.class);
                startActivity(intent);
            }

        });


        Button buttonmodify = (Button) findViewById(R.id.modifybutton);

        buttonmodify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(caregiverActivity.this, caregiverjoinActivity.class);
               /*
                intent.putExtra("Name", Name);
                intent.putExtra("Email", Email);*/
                intent.putExtra("id", id);
                intent.putExtra("email", email);
                startActivity(intent);
            }

        });


        Button weather_button = (Button) findViewById(R.id.weatherbutton);

        weather_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(caregiverActivity.this, weatherActivity.class);
                startActivity(intent);
            }

        });

/*
        jsonrequest.setTag(TAG);
        mqueue.add(jsonrequest);*/
        Log.d(TAG, "handleSignInResult:Role "+size );

        Log.d(TAG, "handleSignInResult:Role " +Role );
    }



    @Override
    protected void onStop() {
        super.onStop();
        if (mqueue != null) {
            mqueue.cancelAll(TAG);
        }
    }

}
