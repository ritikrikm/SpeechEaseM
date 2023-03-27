package com.example.speechease;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<String> names;
    List<Integer> images;
    LayoutInflater inflater;


    public Adapter(Context ctxt, List<String> names, List<Integer> images) {
        this.names = names;
        this.images = images;
        this.inflater = LayoutInflater.from(ctxt);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view = layoutInflater.inflate(R.layout.activity_image, parent, false);
//        ViewHolder viewHolder = new ViewHolder(view);
        View view = inflater.inflate(R.layout.activity_image, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(names.get(position));
        holder.images.setImageResource(images.get(position));

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public ImageView images;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView);
            images = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), PopOutActivity.class);
                    intent.putExtra("position", getAdapterPosition());

                    // Pass the images ArrayList to the PopOutActivity
                    ArrayList<Integer> images = new ArrayList<>(Arrays.asList(R.drawable.happy, R.drawable.sad, R.drawable.laugh, R.drawable.angry, R.drawable.atb, R.drawable.cool, R.drawable.like, R.drawable.think, R.drawable.sick, R.drawable.tired));
                    intent.putIntegerArrayListExtra("images", images);

                    view.getContext().startActivity(intent);
                }
            });
        }
    }


}

