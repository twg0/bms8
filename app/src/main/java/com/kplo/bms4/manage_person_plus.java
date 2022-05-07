package com.kplo.bms4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
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

    String url;
    String vid,id2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_person_plus);
Intent intent =getIntent();
vid=intent.getStringExtra("vid");

ImageButton img =findViewById(R.id.info_input);


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText id=findViewById(R.id.input_name);
                id2=id.getText().toString();
                Long id3=Long.parseLong(id2);

                Long vid2= Long.parseLong(vid);
                 url = " http://10.0.2.2:8080/api/users/" + id3+"/villages";
                JSONObject js = new JSONObject();
                try {
                    js.put("id", id3);
                    js.put("villageId", vid2);
                    Log.d("vid","vid"+vid2);

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


                                /*finish();*/
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


                Volley.newRequestQueue(manage_person_plus.this).add(jsonObjReq);


            }
        });
    }


}