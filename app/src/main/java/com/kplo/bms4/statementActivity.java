package com.kplo.bms4;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class statementActivity extends AppCompatActivity {




    private WebView mWebView;
    private WebSettings mWebSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.state);

        Intent intent = getIntent();
        mWebView=findViewById(R.id.webview_state);
        mWebView.loadUrl("http://www.naver.com");

    }
}
