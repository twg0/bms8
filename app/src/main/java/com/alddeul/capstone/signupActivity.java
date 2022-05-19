package com.alddeul.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class signupActivity extends AppCompatActivity {
   Button button;
    private RequestQueue queue;
    String TAG="joinActivity";
    String url;
    String id,email;
    ImageButton img;
    private EditText agetxt,phonenumbert,Role,guard_user_id,birth,device,name2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_inform);

        birth=findViewById(R.id.birthday);
        device=findViewById(R.id.deviceID);

        Role=findViewById(R.id.roleedit);
        name2=findViewById(R.id.name);


        /**/

/*
        guard_user_id=findViewById(R.id.oldpersonalID);
*/



        Intent intent = getIntent();
        id =intent.getExtras().getString("id");
        email =intent.getExtras().getString("email");



        url = " http://10.0.2.2:8080/users";
// Optional Parameters to pass as POST request

////
        /*
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                *//*tv.setText(response);*//*
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
              *//*  params.put("user_id", id);
                params.put("device_id",device.getText().toString());
*//*
                params.put("email",email);
                params.put("username", name2.getText().toString());
                params.put("Role", Role.getText().toString());
*//*
                params.put("birthday", birth.getText().toString());



                params.put("guard_user_id", guard_user_id.getText().toString());*//*
                return params;
            }
        };


        stringRequest.setTag(TAG);*/


        img=findViewById(R.id.info_input);

       img.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

/*
               queue.add(stringRequest);
*/

               // Adding request to request queue

             signupclick(Role.getText().toString(),name2.getText().toString());

               Intent intent3= new Intent(signupActivity.this,MainActivity.class);
               startActivity(intent3);

           }
       });


    }
/*
    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }*/



    public void signupclick(String Role,String name){

        JSONObject js = new JSONObject();
        try {
            js.put("email",email);
            js.put("username",name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Make request for JSONObject
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString() + " i am queen");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
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




        Volley.newRequestQueue(this).add(jsonObjReq);



    }

}
