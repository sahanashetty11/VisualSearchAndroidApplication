package com.example.android.tflitecamerademo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

public class BuyActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        WebView ourBrow=(WebView) findViewById(R.id.web);
        ourBrow.getSettings().setJavaScriptEnabled(true);
        ourBrow.getSettings().setLoadWithOverviewMode(true);
        ourBrow.getSettings().setUseWideViewPort(true);
        ourBrow.setWebViewClient(new ourViewClient());
        ourBrow.loadUrl("https://www.google.com/search?q=supermarkets+near+me&oq=supermarkets+near+me&aqs=chrome..69i57j0l3.5598j0j4&client=ms-android-xiaomi&sourceid=chrome-mobile&ie=UTF-8#istate=lrl:xpd");
    }
}
