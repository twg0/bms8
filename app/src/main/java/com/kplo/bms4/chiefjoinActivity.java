package com.kplo.bms4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class chiefjoinActivity extends AppCompatActivity {
    String TAG="chiefjoinActivity";

    private EditText editID,ediPassword,Role, agetxt,phonenumbert;
    private Button sign_in;
    private RequestQueue queue,queue2;
    String i,id,email,guard_user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        queue2 = Volley.newRequestQueue(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chiefjoin);
/*
        editID=findViewById(R.id.editID);
        ediPassword=findViewById(R.id.ediPassword);*/
        sign_in=findViewById(R.id.signin);
        Role=findViewById(R.id.mode);
        agetxt=findViewById(R.id.age);
        phonenumbert=findViewById(R.id.phonenumber);

        Intent intent = getIntent();

        id =intent.getExtras().getString("id");
        email =intent.getExtras().getString("email");

        String url = " http://10.0.2.2:8080/user/post/"+email;
        String url2 = " http://10.0.2.2:8080/user/remove/"+email;
        Log.d(TAG, ":personName "+url);
        guard_user_id="0";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                /*tv.setText(response);*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);

                params.put("email",email);
                params.put("guard_user_id", guard_user_id);

                params.put("age", agetxt.getText().toString());
                params.put("phonenumber", phonenumbert.getText().toString());
                params.put("Role", Role.getText().toString());
                return params;
            }
        };


        stringRequest.setTag(TAG);




        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                queue.add(stringRequest);


                Log.d(TAG, "handleSignInResult:personName3 " + id);
                Log.d(TAG, "handleSignInResult:personName4 " + email);



                Intent intent3= new Intent(chiefjoinActivity.this,chiefActivity.class);
                intent3.putExtra("id",id);
                intent3.putExtra("email",email);


                startActivity(intent3);




            }
        });
/////
        StringRequest stringRequest2 = new StringRequest(Request.Method.DELETE, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                /*tv.setText(response);*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();


                params.put("email",email);

                return params;
            }
        };


        stringRequest2.setTag(TAG);



        Button rm=findViewById(R.id.rmbtn);
        rm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queue2.add(stringRequest2);

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }


}