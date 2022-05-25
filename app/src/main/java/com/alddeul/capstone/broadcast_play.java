package com.alddeul.capstone;

import static android.speech.tts.TextToSpeech.ERROR;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class broadcast_play extends AppCompatActivity {
    private TextToSpeech tts;
    ProgressBar progressBar;
    TextView toolbar;
    private Button speak_out;
    MainActivity m = new MainActivity();

    String vname, vid, name;
    ObjectMapper mapper = new ObjectMapper();
    Map<String, String> map;
    private RequestQueue mqueue, mqueue2;
    String TAG = "broadplay";
    String[] ti_data, con_data;
    Integer size;
    TextView nowtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_play);
        toolbar = findViewById(R.id.toolbar_title);

        Intent intent = getIntent();
        vname = intent.getStringExtra("vname");
        vid = intent.getStringExtra("vid");
        name = intent.getStringExtra("name");

        Log.d(" ", "" + vid);

        nowtime = findViewById(R.id.now_time);


        toolbar.setText(vname + " 마을" + name + " 님");
        /*progressBar = findViewById(R.id.progressbar);*/


        mqueue2 = Volley.newRequestQueue(this);


        String url2 = m.serverip+"api/villages/" + vid + "/files";
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.isEmpty()) {

                    return;
                } else {

                    try {
                        /*map = mapper.readValue(response, Map.class);*/

                        List<Map<String, Object>> paramMap = new ObjectMapper().readValue(response, new TypeReference<List<Map<String, Object>>>() {
                        });
                        Log.d(" asdsadsad", " prm" + paramMap);
                        Log.d(" asdsadsad", " size" + paramMap.size());
                        size = paramMap.size();

                        String[] ti_data = new String[paramMap.size()];
                        String[] con_data = new String[paramMap.size()];
                        String[] time_data = new String[paramMap.size()];
                        for (int i = 0; i < paramMap.size(); i++) {

                            ti_data[i] = paramMap.get(i).get("title").toString();
                            Log.d(" asdsadsad", " emails  " + paramMap.get(i).get("title"));
                            Log.d(" asdsadsad", " tidata  " + ti_data[i]);

                            con_data[i] = paramMap.get(i).get("contents").toString();
                            Log.d(" asdsadsad", " role  " + paramMap.get(i).get("contents"));
                            Log.d(" asdsadsad", " condata  " + con_data[i]);

                            time_data[i] = paramMap.get(i).get("createdTime").toString();

                        }


                        speak_out = findViewById(R.id.play_button);


                        speak_out.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            // LOLLIPOP이상 버전에서만 실행 가능
                            @Override
                            public void onClick(View v) {
                                speak(con_data[0]);
                                nowtime.setText(time_data[0]);
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.d("", "" + response);

                    // Class class = new ObjectMapper().readValue(response, Class.class);


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(".", "no ");


            }
        }) {
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

        stringRequest2.setTag(TAG);
        mqueue2.add(stringRequest2);


    }


    private void speak(String text) {
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != ERROR) {
                    int result = tts.setLanguage(Locale.KOREA); // 언어 선택
                    if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                        Log.e("TTS", "This Language is not supported");
                    } else {
                        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                } else {
                    Log.e("TTS", "Initialization Failed!");
                }
            }
        });
    }

/*

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(tts!=null){ // 사용한 TTS객체 제거
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
*/

}