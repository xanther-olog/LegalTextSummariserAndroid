package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class definitionWebView extends AppCompatActivity {
    private WebView definitionWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Bundle b1=getIntent().getExtras();
        String url="https://www.merriam-webster.com/dictionary/"+b1.getString("lookUp")+"#legalDictionary";
        definitionWebView = findViewById(R.id.definitionWebView);
        definitionWebView.getSettings().setJavaScriptEnabled(true);
        definitionWebView.loadUrl(url);

    }
}

