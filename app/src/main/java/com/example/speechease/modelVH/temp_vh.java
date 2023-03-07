package com.example.speechease.modelVH;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.speechease.R;

public class temp_vh  extends RecyclerView.ViewHolder {

    public TextView name;
    //ImageView Cancel_btn;
   public CardView cv;

    public temp_vh(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name);
        cv = itemView.findViewById(R.id.greeting1);

    }
}
