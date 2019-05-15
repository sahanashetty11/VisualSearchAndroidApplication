package com.example.android.tflitecamerademo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ImgAct  extends Activity {

    ArrayList<String> list=new ArrayList<String>();
    ArrayList<String> img=new ArrayList<String>();
    ArrayList<String> links=new ArrayList<String>();
    String name;
    String url;
    String type;
    String link;
    TextView tv1,tv2,tv3,tv4,tv5;
    ImageView im1,im2,im3,im4,im5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
        Intent intent=getIntent();
        type=intent.getStringExtra("type");
        type=type.replaceAll(" ","+");
        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.tv3);
        tv4=findViewById(R.id.tv4);
        tv5=findViewById(R.id.tv5);
        im1=findViewById(R.id.im1);
        im2=findViewById(R.id.im2);
        im3=findViewById(R.id.im3);
        im4=findViewById(R.id.im4);
        im5=findViewById(R.id.im5);

        Content content=new Content();
        content.execute();


        im1.setOnClickListener(v -> {
            String url =links.get(1);
            Intent in = new Intent(ImgAct.this,OpenBuying.class);
            in.putExtra("urls",url);
            startActivity(in);


        });
        im2.setOnClickListener(v -> {
            String url =links.get(2);
            Intent in = new Intent(ImgAct.this,OpenBuying.class);
            in.putExtra("urls",url);
            startActivity(in);
        });
        im3.setOnClickListener(v -> {
            String url =links.get(3);
            Intent in = new Intent(ImgAct.this,OpenBuying.class);
            in.putExtra("urls",url);
            startActivity(in);
        });
        im4.setOnClickListener(v -> {
            String url =links.get(4);
            Intent in = new Intent(ImgAct.this,OpenBuying.class);
            in.putExtra("urls",url);
            startActivity(in);
        });
        im5.setOnClickListener(v -> {
            String url =links.get(5);
            Intent in = new Intent(ImgAct.this,OpenBuying.class);
            in.putExtra("urls",url);
            startActivity(in);
        });

    }

    private class Content extends AsyncTask<Void,Void,Void>{
        Document doc;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                do{String urr="https://www.amazon.in/s?k="+type;
                     doc = Jsoup.connect(urr).get();
                     Elements elements=doc.select("span[data-component-type=s-product-image]");

                   for (Element element:elements){
                       Elements e=element.getElementsByTag("a");
                       link=e.attr("href");
                       Elements f=element.getElementsByTag("img");
                       name=f.attr("alt");
                       Elements g=element.getElementsByTag("img");
                       url=g.attr("src");

                       img.add(url);
                       list.add(name);
                       links.add(link);

                   }
               }while(img.size()<5 ||list.size()<5);



            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(list.size()==0 || img.size()==0)
            {
                tv1.setText("Cant Load");
            }
            else {
                Picasso.get().load(img.get(1)).into(im1);
                Picasso.get().load(img.get(2)).into(im2);
                Picasso.get().load(img.get(3)).into(im3);
                Picasso.get().load(img.get(4)).into(im4);
                Picasso.get().load(img.get(5)).into(im5);
                tv1.setText(list.get(1));
                tv2.setText(list.get(2));
                tv3.setText(list.get(3));
                tv4.setText(list.get(4));
                tv5.setText(list.get(5));
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
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