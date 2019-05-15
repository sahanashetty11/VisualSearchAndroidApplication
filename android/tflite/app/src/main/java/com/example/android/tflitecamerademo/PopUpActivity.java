package com.example.android.tflitecamerademo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

public class PopUpActivity extends Activity {
    TextView t;
    Button amazon,flipkart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custompopup);
        t=findViewById(R.id.cancel);
        amazon=findViewById(R.id.amazon);
        flipkart=findViewById(R.id.flipkart);
        t.setOnClickListener(v -> {

        });
        amazon.setOnClickListener(v -> {

        });
        flipkart.setOnClickListener(v -> {

        });

    }
}
