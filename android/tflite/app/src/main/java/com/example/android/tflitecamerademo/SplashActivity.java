package com.example.android.tflitecamerademo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.FirebaseApp;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        ImageView imageView=findViewById(R.id.imagee);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.flash);
        imageView.startAnimation(animation);


        Thread timer=new Thread()
        {
            @Override
            public void run() {

                try {
                    sleep(4000);
                    Intent intent=new Intent(getApplicationContext(),LogInActivity.class);
                    startActivity(intent);
                    finish();
                    super.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        timer.start();
    }
}
