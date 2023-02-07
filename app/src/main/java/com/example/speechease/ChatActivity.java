package com.example.speechease;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ChatActivity extends RecyclerView.ViewHolder {

    public TextView name;
    public RelativeLayout layout;
    public ImageView edit;

    public ChatActivity(@NonNull View itemView) {
        super(itemView);

       name = itemView.findViewById(R.id.nameofuser);
       layout = itemView.findViewById(R.id.layoutclick);
       edit = itemView.findViewById(R.id.edit);
    }
}