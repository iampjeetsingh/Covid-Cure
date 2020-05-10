package com.example.hp_awareness_app;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InfoWeb extends AppCompatActivity {

    String url;
    WebView website;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        website = findViewById(R.id.websiteGoI);
        url = "https://www.mohfw.gov.in/";
        website.loadUrl(url);
    }
}
