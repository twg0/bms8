package com.alddeul.capstone;

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
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.sdk.user.UserApiClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Person_infomasterActivity extends AppCompatActivity {

    Intent intent;
    int trigger;
    LinearLayout linearLayout;
    TextView name2,relation,age2,address,phone,email2,registration,toolbar;
    String name,email,role,vname,id,vid;
    private RequestQueue queue,queue2;
    String TAG="personinfomaster";
    ObjectMapper mapper = new ObjectMapper();
    Map<String,String> map;
ImageButton logout;
    MainActivity m = new MainActivity();


    private RequestQueue mqueue, mqueue2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_masterinfo);


        intent = getIntent();
        id=intent.getStringExtra("id");
        role = intent.getStringExtra("Role");
        name=intent.getStringExtra("name");
        email=intent.getStringExtra("email");
        vname=intent.getStringExtra("vname");


        queue2 = Volley.newRequestQueue(this);
        mqueue = Volley.newRequestQueue(this);
        mqueue2 = Volley.newRequestQueue(this);

        String url3 = m.serverip+"api/users/" + email;
        StringRequest stringRequest3 = new StringRequest(Request.Method.GET, url3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.isEmpty()) {

                    return;
                } else {

                    try {
                        map = mapper.readValue(response, Map.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    Log.d("", "" + response);
                    String phonenum;
                    phonenum = map.get("phoneNumber");


                    name2 = findViewById(R.id.name);

                    age2 = findViewById(R.id.age);
                    /*address = findViewById(R.id.addre);*/
                    phone = findViewById(R.id.phon);
                    email2 = findViewById(R.id.emai);

                    name2.setText(name);
                    email2.setText(email);
                    phone.setText(phonenum);


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("infoguard", "no userdata");


            }
        }){
            @Override //response를 UTF8로 변경해주는 소스코드
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, "UTF-8");
                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    // log error
                    return Response.error(new ParseError(e));
                } catch (Exception e) {
                    // log error
                    return Response.error(new ParseError(e));
                }
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

        };

        stringRequest3.setTag(TAG);
        mqueue2.add(stringRequest3);




        queue2 = Volley.newRequestQueue(this);
logout=findViewById(R.id.logout);

logout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {
                return null;
            }
        });


        Intent intent2 = new Intent(Person_infomasterActivity.this, MainActivity.class);
        startActivity(intent2);
        finish();
    }
});


        toolbar=findViewById(R.id.toolbar_title);
        toolbar.setText( vname+"마을이장"+ name+"님");



        String url2 = m.serverip+"api/admins/"+id;

        String url = m.serverip+"api/admins/"+id+"/villages";


        queue = Volley.newRequestQueue(this);


/*

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                */
/*tv.setText(response);*//*


                try {
                    map = mapper.readValue(response, Map.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                vid= String.valueOf(map.get("id"));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.getMessage());
                Log.d("master info","register village");
            }
        });



        stringRequest.setTag(TAG);
        queue.add(stringRequest);
*/



////



        Button delete=findViewById(R.id.guard_data);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest stringRequest2 = new StringRequest(Request.Method.DELETE, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        /*tv.setText(response);*/
                        Log.d("delete","succ");
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


                        /*params.put("email",email);*/

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