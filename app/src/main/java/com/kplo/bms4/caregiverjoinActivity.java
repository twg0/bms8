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
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class caregiverjoinActivity extends AppCompatActivity {
    String TAG="caregiverjoinActivity";

    TextView idtxt;
    private EditText agetxt,phonenumbert,oldnamet,oldpersonalIDt,relationshipt,Role;
    private RequestQueue queue;
    private Button btnsend,btnok;
    /*private TextView tv;*/
    String i,Id,Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        queue = Volley.newRequestQueue(this);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.caregiverjoin);

        Intent intent = getIntent();
         Id =intent.getExtras().getString("Id");
         Email =intent.getExtras().getString("Email");

        /*String Name =intent.getExtras().getString("Name");*/
        i= Id;

        String url = " http://10.0.2.2:8080/user/post/"+i;
/*
        Log.d(TAG, ":personName "+Name);
*/
        Log.d(TAG, "handleSignInResult:personName3 " + Id);
        Log.d(TAG, "handleSignInResult:personName4 " + Email);

        Role=findViewById(R.id.mode);
        agetxt=findViewById(R.id.age);
        phonenumbert=findViewById(R.id.phonenumber);
/*
        addresst=findViewById(R.id.address);
*/
        oldnamet=findViewById(R.id.oldname);
        oldpersonalIDt=findViewById(R.id.oldpersonalID);
        relationshipt=findViewById(R.id.relationship);

/*
        tv=findViewById(R.id.tv2);
*/

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
                params.put("Id", Id);

                params.put("Email",Email);


                params.put("age", agetxt.getText().toString());
                params.put("phonenumber", phonenumbert.getText().toString());
                params.put("Role", Role.getText().toString());

                return params;
            }
        };


        stringRequest.setTag(TAG);




        btnsend =findViewById(R.id.sendbutton);

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                queue.add(stringRequest);

                Intent intent3= new Intent(caregiverjoinActivity.this,caregiverActivity.class);
                startActivity(intent3);


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