package com.example.speechease;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class PopOutActivity extends AppCompatActivity {

    //TextView name;
    ImageView image;

    ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_out);

        Intent intent = getIntent();
        String name = intent.getStringExtra("i");
        // name = findViewById(R.id.emoji);
        image = findViewById(R.id.images);
        button = findViewById(R.id.audio);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PopOutActivity.this, name, Toast.LENGTH_SHORT).show();
            }
        });


        image.setImageResource(intent.getIntExtra("image",0));


    }
}