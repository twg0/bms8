package com.kplo.bms4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kakao.sdk.user.UserApiClient;

import java.util.HashMap;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Person_info_guardActivity extends AppCompatActivity {

    Intent intent;
    int trigger;
    LinearLayout linearLayout;
    TextView name2, relation, age2, address, phone, email2, registration, toolbar;
    String name, email, role, vname, id;
    private RequestQueue queue, queue2;
    String TAG = "personinfo";
ImageButton img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_guard_info);

        intent = getIntent();
        id = intent.getStringExtra("id");
        role = intent.getStringExtra("Role");
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");


        linearLayout = findViewById(R.id.guard_info);
        queue2 = Volley.newRequestQueue(this);

        if (role.equals("ROLE_USER"))
            linearLayout.setVisibility(View.INVISIBLE);

        toolbar = findViewById(R.id.toolbar_title);
        toolbar.setText(vname + "마을" + name + "님");


        name2 = findViewById(R.id.name);
        relation = findViewById(R.id.rela);
        age2 = findViewById(R.id.age);
        address = findViewById(R.id.addre);
        phone = findViewById(R.id.phon);
        email2 = findViewById(R.id.emai);
        registration = findViewById(R.id.regis);

        name2.setText(name);
        email2.setText(email);
        String url2 = " http://10.0.2.2:8080/api/users/" + id;

        Button villageadd = findViewById(R.id.villageadd);
        villageadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Person_info_guardActivity.this, addvillage.class);
               intent.putExtra("id",id);
                startActivity(intent);
            }
        });
img=findViewById(R.id.logout);
img.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {
                return null;
            }
        });


        Intent intent2 = new Intent(Person_info_guardActivity.this, MainActivity.class);
        startActivity(intent2);
        finish();
    }
});

        Button addcaregiver = findViewById(R.id.caregiveradd);
        addcaregiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Person_info_guardActivity.this, caregiverjoinActivity.class);
                startActivity(intent);
            }
        });

        Button delete = findViewById(R.id.guard_data);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest stringRequest2 = new StringRequest(Request.Method.DELETE, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        /*tv.setText(response);*/
                        Log.d(" "," 탈퇴완료");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.getMessage());

                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();


                        params.put("email", email);

                        return params;
                    }
                };


                stringRequest2.setTag(TAG);
                queue2.add(stringRequest2);

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}