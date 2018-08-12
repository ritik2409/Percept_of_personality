package com.moodcafe.assessmentgame;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by ritik on 6/6/2018.
 */

public class ChooseGameAdapter extends RecyclerView.Adapter<ChooseGameAdapter.MyViewHolder> {

    Activity activity;
    int resource;
    Integer[] storyImage;
    String dataFile;

    public ChooseGameAdapter(Activity activity, Integer[] storyImage, int resource, String dataFile) {
        this.activity = activity;
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

        Integer image = storyImage[position];
        Glide.with(activity).load(image).into(holder.icon);


    }

    @Override
    public int getItemCount() {
        return storyImage.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView icon;
        Context c;

        public MyViewHolder(View itemView) {
            super(itemView);

            icon = (ImageView) itemView.findViewById(R.id.storyImage);
            c = itemView.getContext();
            itemView.setOnClickListener(this);
            itemView.setClickable(true);


        }



        @Override
        public void onClick(View v) {
            ((MainActivity)activity).askResume(getAdapterPosition());

        }
    }
}
