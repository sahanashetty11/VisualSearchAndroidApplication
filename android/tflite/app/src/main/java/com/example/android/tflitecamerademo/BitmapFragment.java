package com.example.android.tflitecamerademo;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import static android.content.ContentValues.TAG;


public class BitmapFragment extends Activity {

    private ImageClassifier classifier;
    TextView textView,text2,text4;
    Button button2,button3,button4;
    String tt;
    String loreal="Loreal";
    Dialog mydialog;
    String nivea="Nivea";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydialog=new Dialog(this);
        setContentView(R.layout.activity_bitmap);
        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        textView = (TextView) findViewById(R.id.text);
        text2=findViewById(R.id.text2);
        text4=findViewById(R.id.text4);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);

        Intent intent = getIntent();
        Bitmap photo = (Bitmap) intent.getParcelableExtra("bitmap");
        imageView.setImageBitmap(photo);
        try {
            classifier = new ImageClassifier(this);
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (classifier == null ) {
            showToast("Uninitialized Classifier or invalid context.");
            return;
        }
        String textToShow = classifier.classifyFrame(photo);
        tt=textToShow;
       // photo.recycle();


        showToast(textToShow);
        System.out.println(textToShow);
        //String u;
        //u = textView.getText().toString();

        switch(textToShow.trim().toLowerCase()){

            case "loreal color protecting shampoo":
                text2.setText(loreal);
                text4.setText("Color Protecting Shampoo");
                break;
            case "loreal paris 6 oil nourish shampoo":
                text2.setText(loreal);
                text4.setText("Oil Care Shampoo");
                break;
            case "loreal paris express scalp refreshner":
                text2.setText(loreal);
                text4.setText("Dry scalp refreshner");
                break;
            case "loreal paris extraordinary clay hydrating shampoo":
                text2.setText(loreal);
                text4.setText("Clay hydrating Shampoo");
                break;
            case "loreal paris fall repair antihairfall shampoo":
                text2.setText(loreal);
                text4.setText("Anti Hairfall Shampoo");
                break;
            case "nivea aloe hydration cream":
                text2.setText(nivea);
                text4.setText("Moisturizing cream");
                break;
            case "nivea aloe hydration normal skin":
                text2.setText(nivea);
                text4.setText("Normal Skin moisturizing cream");
                break;
            case "nivea purifying face wash mixedtooily skin":
                text2.setText(nivea);
                text4.setText("Oily skin FaceWash");
                break;
            case "nivea cocoa nourish lotion verydry skin":
                text2.setText(nivea);
                text4.setText("Very dry skin lotion");
                break;
            case "nivea milk delights face wash normal skin":
                text2.setText(nivea);
                text4.setText("Normal Skin FaceWash");
                break;
            case "nivea refreshing face wash normal skin":
                text2.setText(nivea);
                text4.setText("Normal Skin FaceWash");
                break;
            case "nivea shea smooth body milk dry skin":
                text2.setText(nivea);
                text4.setText("Dry skin body milk");
                break;
            case "nivea total face cleanup normal skin":
                text2.setText(nivea);
                text4.setText("Normal Skin FaceWash");
                break;
                default:
                    text2.setText("None");
                    text4.setText("None");


        }


        button2.setOnClickListener(v -> {
           Intent intent1=new Intent(BitmapFragment.this,ImgAct.class);
           intent1.putExtra("type",text4.getText().toString());
           startActivity(intent1);
        });

        button4.setOnClickListener(v -> {
            Intent intent2=new Intent(BitmapFragment.this,BuyActivity.class);
            startActivity(intent2);

        });


    }
    public void button3(View v){
        TextView t;
        Button amazon,flipkart;
        mydialog.setContentView(R.layout.custompopup);
        t=mydialog.findViewById(R.id.cancel);
        amazon=mydialog.findViewById(R.id.amazon);
        flipkart=mydialog.findViewById(R.id.flipkart);
        t.setOnClickListener(view -> {
            mydialog.dismiss();
        });
        amazon.setOnClickListener(view -> {
            String text=tt.trim().toLowerCase();
            text=text.replace(' ','+');
            String url="https://www.amazon.in/s?k="+text+"&ref=nb_sb_noss_2";
            Intent Getent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(Getent);
        });
        flipkart.setOnClickListener(view -> {
            String text=tt.trim().toLowerCase();
            text=text.replace(" ","%20");
            String url="https://www.flipkart.com/search?q="+text+"&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=off";
            Intent Getent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(Getent);
        });
        mydialog.show();
    }

    private void showToast(String s) {
        textView.setText(s);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
