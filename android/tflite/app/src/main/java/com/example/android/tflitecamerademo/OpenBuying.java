package com.example.android.tflitecamerademo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class OpenBuying extends Activity {
    WebView ourBrow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        Intent intent = getIntent();
        String u = intent.getStringExtra("urls");
        u ="https://www.amazon.in/"+ u ;
        Intent Getintent = new Intent(Intent.ACTION_VIEW, Uri.parse(u));
        startActivity(Getintent);
    }
}

