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

public class signupActivity extends AppCompatActivity {
   Button button;
    private RequestQueue queue;
    String TAG="joinActivity";
    String url;
    String id,email;
    private EditText agetxt,phonenumbert,Role,guard_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        Role=findViewById(R.id.mode);
        agetxt=findViewById(R.id.age);
        phonenumbert=findViewById(R.id.phonenumber);
        /**/

        guard_user_id=findViewById(R.id.oldpersonalID);

        Role=findViewById(R.id.mode);


        Intent intent = getIntent();
        id =intent.getExtras().getString("id");
        email =intent.getExtras().getString("email");


        url = " http://10.0.2.2:8080/user/post/"+email;


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
                Map<String, String> params = new HashMap<>();
                params.put("id", id);

                params.put("email",email);

                params.put("age", agetxt.getText().toString());
                params.put("phonenumber", phonenumbert.getText().toString());
                params.put("guard_user_id", guard_user_id.getText().toString());

                params.put("Role", Role.getText().toString());

                return params;
            }
        };


        stringRequest.setTag(TAG);


        button=findViewById(R.id.sendbutton);

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               queue.add(stringRequest);

               Intent intent3= new Intent(signupActivity.this,MainActivity.class);
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
