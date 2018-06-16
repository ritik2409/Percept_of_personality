package com.example.android.assessmentgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by ritik on 6/6/2018.
 */

public class ChooseGameAdapter extends RecyclerView.Adapter<ChooseGameAdapter.MyViewHolder> {

    Activity activity;
    int resource;
    String[] storyTitle;
    Integer[] storyImage;
    String dataFile;

    public ChooseGameAdapter(Activity activity, String[] storyTitle, Integer[] storyImage, int resource, String dataFile) {
        this.activity = activity;
        this.storyTitle = storyTitle;
        this.storyImage = storyImage;
        this.resource = resource;
        this.dataFile = dataFile;
    }

    @Override
    public ChooseGameAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = activity.getLayoutInflater()
                .inflate(resource, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ChooseGameAdapter.MyViewHolder holder, int position) {

        String title = storyTitle[position];
        Integer image = storyImage[position];
        holder.text.setText(title);
        Glide.with(activity).load(image).into(holder.icon);


    }

    @Override
    public int getItemCount() {
        return storyTitle.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView text;
        public ImageView icon;
        Context c;

        public MyViewHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.storyTitle);
            icon = (ImageView) itemView.findViewById(R.id.storyImage);
            c = itemView.getContext();
            itemView.setOnClickListener(this);
            itemView.setClickable(true);


        }



        @Override
        public void onClick(View v) {
            ((GameDetails)activity).askResume(getAdapterPosition());

        }
    }
}
