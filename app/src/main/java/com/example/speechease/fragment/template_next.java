package com.example.speechease.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;

import com.example.speechease.R;

import java.util.Locale;

public class template_next extends AppCompatActivity {
    CardView greeting;
    TextToSpeech text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_template);
        greeting = findViewById(R.id.greeting1);

        greeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view==greeting){


                    text =new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int i) {

                            if(i!=TextToSpeech.ERROR){
                                // To Choose language of speech
                                text.setLanguage(Locale.UK);

                                text.speak("Hi, what are the options in the menu.",TextToSpeech.QUEUE_FLUSH,null);
                            }
                        }
                    });
                }
            }
        });

    }
}