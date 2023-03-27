package com.example.speechease;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class PopOutActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button;
    private ArrayList<Integer> images;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_out);

        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);

        // Get the images ArrayList from the intent extras
        images = intent.getIntegerArrayListExtra("images");

        if (position >= 0 && position < images.size()) {
            imageView.setImageResource(images.get(position));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String imageText = getImageText(position);
                    Toast.makeText(PopOutActivity.this, imageText, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            finish();
        }
    }

    private String getImageText(int position) {
        String imageText = "";
        switch (position) {
            case 0:
                imageText = getString(R.string.happy);
                break;
            case 1:
                imageText = getString(R.string.sad);
                break;
            case 2:
                imageText = getString(R.string.laugh);
                break;
            case 3:
                imageText = getString(R.string.angry);
                break;
            case 4:
                imageText = getString(R.string.atb);
                break;
            case 5:
                imageText = getString(R.string.excited);
                break;
            case 6:
                imageText = getString(R.string.like);
                break;
            case 7:
                imageText = getString(R.string.think);
                break;
            case 8:
                imageText = getString(R.string.sick);
                break;
            case 9:
                imageText = getString(R.string.tired);
                break;

        }
        return imageText;
    }
}
