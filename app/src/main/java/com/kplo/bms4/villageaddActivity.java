package com.kplo.bms4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class villageaddActivity extends AppCompatActivity {
    String TAG="villagejoinActivity";

    private EditText villageids,zipcodes,citys,streets;
    private Button sign_in;
    private RequestQueue queue;
    String i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.villageadd);
/*
        editID=findViewById(R.id.editID);
        ediPassword=findViewById(R.id.ediPassword);*/

        sign_in=findViewById(R.id.signin);
        villageids=findViewById(R.id.villageid);
        zipcodes=findViewById(R.id.zipcode);
        streets=findViewById(R.id.street);

        Intent intent = getIntent();


        String url = " http://10.0.2.2:8080/user/post/"+villageids;
/*
        String url2 = " https://localhost:8080/user/post/"+id;
*/
        Log.d(TAG, ":personName "+url);


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
               /*
                params.put("guard_user_id", guard_user_id);
*/
                params.put("villageid",villageids.getText().toString());

                params.put("zipcode",zipcodes.getText().toString());
                params.put("street",streets.getText().toString());
                params.put("city",citys.getText().toString());
                return params;
            }
        };


        stringRequest.setTag(TAG);




        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                queue.add(stringRequest);

/*

                Log.d(TAG, "handleSignInResult:personName3 " + id);
                Log.d(TAG, "handleSignInResult:personName4 " + email);
*/



                Intent intent3= new Intent(villageaddActivity.this,chiefActivity.class);


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