package com.alddeul.capstone;

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

import java.util.HashMap;
import java.util.Map;

public class userjoinActivity extends AppCompatActivity {
   Button button;
    private RequestQueue queue;
    String TAG="userjoinActivity";
    String url;
    String id,email;
    private EditText Role,age,editID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.userjoin);

        Role=findViewById(R.id.mode);
        age=findViewById(R.id.age);


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


/*
               Intent intent3= new Intent(userjoinActivity.this,chiefActivity.class);
*/


               /*startActivity(intent3);*/




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
