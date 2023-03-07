package com.example.speechease.modelVH;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.speechease.R;

public class internal_vh extends RecyclerView.ViewHolder {

    public TextView cmpname;

    public CardView cv;

    public internal_vh(@NonNull View itemView) {
        super(itemView);


        cmpname = itemView.findViewById(R.id.cat_card);
        cv = itemView.findViewById(R.id.cardview);

    }
}
