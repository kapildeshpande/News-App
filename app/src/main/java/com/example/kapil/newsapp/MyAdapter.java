package com.example.kapil.newsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by kapil on 03-03-2018.
 */

public class MyAdapter extends RecyclerView.Adapter <MyAdapter.ViewHolder> {

    Context context;
    ArrayList <JSONData> dataList;

    public MyAdapter (Context context, ArrayList<JSONData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_caption_image,parent,false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {

        final JSONData jsonData = dataList.get(position);
        if (jsonData.getImageURL() != null)
            GlideApp.with(context)
                    .load(jsonData.getImageURL())
                    .placeholder(R.drawable.ic_launcher_web)
                    .into(holder.imageView);
        holder.textView1.setText(jsonData.getTitle());
        holder.textView2.setText(jsonData.getDescription());

        holder.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailNewsActivity.class);
                intent.putExtra("imgUrl",jsonData.getImageURL());
                intent.putExtra("Heading",jsonData.getTitle());
                intent.putExtra("Description",jsonData.getDescription());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView c;
        TextView textView1;
        TextView textView2;
        ImageView imageView;
        public ViewHolder (View v) {
            super(v);
            c = (CardView) v.findViewById(R.id.card_view);
            textView1 = (TextView) v.findViewById(R.id.info_text);
            textView2 = (TextView) v.findViewById(R.id.info_description);
            imageView = (ImageView) v.findViewById(R.id.info_image);
        }
    }

}
