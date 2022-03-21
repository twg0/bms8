package com.kplo.bms4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class chiefjoinActivity extends AppCompatActivity {

    private EditText editID,ediPassword;
    private Button sign_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chiefjoin);

        editID=findViewById(R.id.editID);
        ediPassword=findViewById(R.id.ediPassword);
        sign_in=findViewById(R.id.signin);

        Intent intent = getIntent();




        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID =editID.getText().toString();
                String userPassword =ediPassword.getText().toString();


              /*  Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success)
                            {
                                Toast.makeText(getApplicationContext(),"회원가입성공",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(chiefjoinActivity.this,chiefloginActivity.class);
                                startActivity(intent);
                            }

                            else{
                                Toast.makeText(getApplicationContext(),"회원가입실패",Toast.LENGTH_SHORT).show();
                                return;

                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };*/
/*

                registerRequest registerrequest = new registerRequest(userID,userPassword,responseListener);
                RequestQueue queue= Volley.newRequestQueue(chiefjoinActivity.this);
                queue.add(registerrequest);

*/



            }
        });

    }
}