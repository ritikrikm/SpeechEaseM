package com.example.speechease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class info2_popup extends AppCompatActivity {
    ImageView cross;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info1_popup);
        textView = findViewById(R.id.textemail);
        cross = findViewById(R.id.cross_btn_rf);
        textView.setText(R.string.info_voice);
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}