/*
package com.kplo.bms4;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class positionlistActivity extends AppCompatActivity {

    private RequestQueue queue,queue2;
    String TAG="position";

    TextView txt,content;
    String id,contents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.positionlist);
        queue2 = Volley.newRequestQueue(this);



txt=findViewById(R.id.positioncontent);
        content=findViewById(R.id.content);


        Intent intent=getIntent();
        id =intent.getExtras().getString("file_id");
        contents =intent.getExtras().getString("contents");

        txt.setText(id);
        content.setText(contents);

        String url2 = " http://10.0.2.2:8080/file/remove/"+id;



        StringRequest stringRequest2 = new StringRequest(Request.Method.DELETE, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("er","hi"+response);



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


                params.put("file_id",id);

                return params;
            }
        };


        stringRequest2.setTag(TAG);




        Button rmv = findViewById(R.id.deletebtn);
        rmv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                queue2.add(stringRequest2);

            Intent intent = new Intent(positionlistActivity.this,chiefboard_activity.class);
            startActivity(intent);


            }
        });


    }
}*/
