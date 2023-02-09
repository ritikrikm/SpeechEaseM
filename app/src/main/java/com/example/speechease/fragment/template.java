package com.example.speechease.fragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.speechease.R;

import java.util.Locale;


public class template extends Fragment {

    CardView greeting;
    TextToSpeech text;
        // Required empty public constructor




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_template,container,false);
        greeting = v.findViewById(R.id.greeting);

        greeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view==greeting){
                    text =new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int i) {

                            if(i!=TextToSpeech.ERROR){
                                // To Choose language of speech
                                text.setLanguage(Locale.UK);
                                text.speak("It’s a pleasure to meet you",TextToSpeech.QUEUE_FLUSH,null);
                            }
                        }
                    });
                }
            }
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_template, container, false);
    }
}