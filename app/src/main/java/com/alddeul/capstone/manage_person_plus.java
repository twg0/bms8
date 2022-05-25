package com.alddeul.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

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

public class manage_person_plus extends AppCompatActivity {
    String TAG = "plus";
    private RequestQueue queue;
    MainActivity m = new MainActivity();

    String url,name,vname;
    String vid, id;
    EditText id2;
    ImageButton img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_person_plus);
        Intent intent = getIntent();
        vid = intent.getStringExtra("vid");
        name= intent.getStringExtra("name");
        vname= intent.getStringExtra("vname");
        queue = Volley.newRequestQueue(this);

         img = findViewById(R.id.info_input);
        id2 = findViewById(R.id.input_name);


        Log.d("vid", "vid " + vid);


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id = id2.getText().toString();
                Log.d("vid", "id " + id);


                Long vid2 = Long.parseLong(vid);

                Log.d("vid", "vid2 " + vid2);
                url = m.serverip+"api/users/" + id+"/villages?villageId=" +vid2;
                /*adduser(url);*/
                add(url,vid2);

                Intent intent = new Intent(manage_person_plus.this, manage_person.class);
                intent.putExtra("vid",vid);
                intent.putExtra("vname",vname);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
    }

    public void add(String url,Long vid2) {
        String TAG = "plus";

        JSONObject js = new JSONObject();
        try {

            js.put("villageId", vid2);
            Log.d("this","here ");



        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("this","here error");
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
                VolleyLog.d(TAG, "Errornot send: " + error.getMessage());
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