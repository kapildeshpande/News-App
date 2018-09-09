package com.example.kapil.newsapp;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        String imgUrl = (String) getIntent().getExtras().get("imgUrl");
        String text = (String) getIntent().getExtras().get("Heading");
        String text_description = (String) getIntent().getExtras().get("Description");

        TextView textView = findViewById(R.id.heading);
        TextView textView1 = findViewById(R.id.description);
        ImageView imageView = findViewById(R.id.image);

        //Glide.with(this).load(imgUrl).into(imageView);
        GlideApp.with(this)
                .load(imgUrl)
                .placeholder(R.drawable.ic_launcher_web)
                .into(imageView);
        textView.setText(text);
        textView1.setText(text_description);
    }
}
