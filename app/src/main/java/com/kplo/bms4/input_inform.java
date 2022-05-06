package com.kplo.bms4;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kakao.sdk.user.UserApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class input_inform extends AppCompatActivity {

    TextView targetTextView;
    ImageButton info_input;
    CheckBox checkBox;
    RadioGroup radioGroup;
    RadioButton radioButton;
    LinearLayout linearLayout;
    EditText name, birth, phone, guard_name, guard_birth, master_id;

    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_inform);

        //입력된 정보
        name = (EditText) findViewById(R.id.input_name);


        phone = (EditText) findViewById(R.id.input_phone);
        /*guard_name = (EditText) findViewById(R.id.input_guard_name);*/


        Intent intent = getIntent();
        String email;
        email = intent.getStringExtra("email");


        //Check box 이벤트 등록
        checkBox = findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox.setText("is Checked");
                } else {
                    checkBox.setText("is unChecked");
                }
            }
        });

        checkBox.setVisibility(View.GONE);

        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.check(R.id.radio1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.radio1) {
                   /* linearLayout=findViewById(R.id.guard);
                    linearLayout.setVisibility(View.GONE);*/

                    flag = 0;

                } else if (checkedId == R.id.radio2) {

                   /* linearLayout=findViewById(R.id.guard);
                    linearLayout.setVisibility(View.VISIBLE);*/
                    flag = 1;

                } else {
                   /* linearLayout=findViewById(R.id.guard);
                    linearLayout.setVisibility(View.GONE);*/

                    flag = 2;

                }
            }
        });


        info_input = findViewById(R.id.info_input);
        info_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                String url;

                String name2;
                name2 = name.getText().toString();

                String p2;
                p2 = phone.getText().toString();
                if (flag == 0) {
                    url = " http://10.0.2.2:8080/api/users";
                    usersignupclick(name2, email, url, p2);
                    /*
                    UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                        @Override
                        public Unit invoke(Throwable throwable) {
                            return null;
                        }
                    });
*/

                    /*finish();*/
                } else if (flag == 1) {
                    url = " http://10.0.2.2:8080/api/users";
                    caregiverignupclick(name2, email, url, p2);
                   /* UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                        @Override
                        public Unit invoke(Throwable throwable) {
                            return null;
                        }
                    });
*/

/*
                    finish();
*/

                } else {
                    url = " http://10.0.2.2:8080/api/users";

                    chiefsignupclick(name2, email, url, p2);
                   /* UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                        @Override
                        public Unit invoke(Throwable throwable) {
                            return null;
                        }
                    });*/


/*
                    finish();
*/

                }


            }
        });


    }

    public void usersignupclick(String name, String email, String url, String phone) {
        String TAG = "a";
        JSONObject js = new JSONObject();
        try {
            js.put("email", email);
            js.put("username", name);
            js.put("phoneNumber", phone);
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

                        Intent intent;
                        intent = new Intent(getApplicationContext(), common.class);
                        intent.putExtra("email", email);

                        startActivity(intent);
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


        Volley.newRequestQueue(this).add(jsonObjReq);


    }


    public void caregiverignupclick(String name, String email, String url, String phone) {
        String TAG = "aa";

        JSONObject js = new JSONObject();
        try {
            js.put("email", email);
            js.put("username", name);
            js.put("phoneNumber", phone);

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
                      /*  UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                return null;
                            }
                        });
                        */

                        Intent intent;
                        intent = new Intent(getApplicationContext(), commoncaregiver.class);
                        intent.putExtra("email", email);


                        startActivity(intent);
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


        Volley.newRequestQueue(this).add(jsonObjReq);

    }


    public void chiefsignupclick(String name, String email, String url, String phone) {
        String TAG = "aaa";

        JSONObject js = new JSONObject();
        try {
            js.put("email", email);
            js.put("username", name);
            js.put("phoneNumber", phone);


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
                       /* UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                return null;
                            }
                        });*/

                        Intent intent;
                        intent = new Intent(getApplicationContext(), master.class);
                        intent.putExtra("email", email);


                        startActivity(intent);
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


        Volley.newRequestQueue(this).add(jsonObjReq);

    }


}