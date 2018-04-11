package com.popo.szuspider;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class Show extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent intent=getIntent();
        String web=intent.getStringExtra("web");
        WebView webView=(WebView)findViewById(R.id.wbv);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData(web,"text/html","utf-8");
    }
}
