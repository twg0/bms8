package com.kplo.bms4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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


public class chiefloginActivity extends AppCompatActivity {

    private EditText editID, ediPassword;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String url = "http://www.google.com";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Intent intent = getIntent();

        editID = findViewById(R.id.editID);
        ediPassword = findViewById(R.id.ediPassword);

        Button button2 = (Button) findViewById(R.id.loginbutton);
        textView =findViewById(R.id.textresult);

//////////////////


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest(url);

                    Intent intent = new Intent(chiefloginActivity.this, chiefActivity.class);
                    startActivity(intent);


            }
        });


        if (AppHelper.requestQueue == null)
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());


        ////////////////



        Button button= (Button)findViewById(R.id.joinbutton);

        button.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(chiefloginActivity.this, chiefjoinActivity.class);
            startActivity(intent);
        }

    });

    }


    public void sendRequest(String url) {

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textView.setText(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText(error.getMessage());
                    }
                }
        )
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", editID.getText().toString());
                params.put("password", ediPassword.getText().toString());

                return params;
            }
        };


        // 이전 결과가 있더라도 새로 요청
        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(this);//queue 초기화


        AppHelper.requestQueue.add(request);

        Toast.makeText(getApplicationContext(), "Request sent", Toast.LENGTH_SHORT).show();


    }




}