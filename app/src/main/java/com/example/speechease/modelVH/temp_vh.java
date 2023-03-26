package com.example.speechease.modelVH;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.speechease.R;

public class temp_vh  extends RecyclerView.ViewHolder {

    public TextView name;
    //ImageView Cancel_btn;
   public CardView cv;
   public Toolbar toolbar;

    public temp_vh(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.cat_card);
        cv = itemView.findViewById(R.id.cardview);
        toolbar = itemView.findViewById(R.id.tool);

    }
}
