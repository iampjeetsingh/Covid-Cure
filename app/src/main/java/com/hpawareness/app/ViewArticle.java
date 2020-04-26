package com.hpawareness.app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.view.View;

public class ViewArticle extends AppCompatActivity {

    private Context context = ViewArticle.this;
    private WebView webView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_article);
        url = getIntent().getStringExtra("url");
        if(url==null)
            finish();
        webView = findViewById(R.id.webview);
        webView.loadUrl(url);
    }

    public void openClick(View v){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}
