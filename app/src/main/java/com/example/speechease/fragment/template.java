package com.example.speechease.fragment;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.speechease.Home_Login;
import com.example.speechease.R;

import java.util.Locale;


public class template extends Fragment {

    CardView greeting_cate;
    TextToSpeech text;
        // Required empty public constructor




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_template_cate,container,false);
        greeting_cate = v.findViewById(R.id.greeting_cate);

        greeting_cate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    Intent intent = new Intent(getActivity(), template_next.class);

                    startActivity(intent);

//                    text =new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
//                        @Override
//                        public void onInit(int i) {
//
//                            if(i!=TextToSpeech.ERROR){
//                                // To Choose language of speech
//                                text.setLanguage(Locale.UK);
//
//                                text.speak("It’s a pleasure to meet you",TextToSpeech.QUEUE_FLUSH,null);
//                            }
//                        }
//                    });

            }
        });

        // Inflate the layout for this fragment
        return v;
    }
}