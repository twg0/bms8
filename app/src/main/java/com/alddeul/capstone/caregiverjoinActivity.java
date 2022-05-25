package com.alddeul.capstone;

import android.content.Intent;
import android.os.Bundle;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class caregiverjoinActivity extends AppCompatActivity {
    String TAG = "caregiverjoinActivity";

    TextView toolbar;
    private EditText agetxt, phonenumbert, oldnamet, oldpersonalIDt, relationshipt, Role, guard_user_id;
    private RequestQueue queue;
    private Button btnsend;
    String gid;
    String id2, email, id3, id, name;
    EditText old;
    MainActivity m = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        queue = Volley.newRequestQueue(this);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.caregiverjoin);

        Intent intent = getIntent();
        id2 = intent.getExtras().getString("id");
        email = intent.getExtras().getString("email");
        name = intent.getExtras().getString("name");

        toolbar = findViewById(R.id.toolbar_title);
        toolbar.setText(name + "님 피보호자 등록");


        btnsend = findViewById(R.id.send);

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Long gid;
                gid = Long.parseLong(id2);
                old = findViewById(R.id.guardid);
                id3 = old.getText().toString();
                Long id = Long.parseLong(id3);

                String url = m.serverip+"api/users/" + id + "/guardian?guardianId=" + gid;

                Log.d(TAG, ":url " + url);

                add(url, gid);


            }
        });


    }

    public void add(String url, Long gid) {


        JSONObject js = new JSONObject();

        try {

            js.put("guardianId", gid);


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