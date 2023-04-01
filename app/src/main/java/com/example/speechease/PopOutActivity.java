package com.example.speechease;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.speechease.Utils.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class PopOutActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private ImageView imageView;
    private Button button;
    private ArrayList<Integer> images;
    String gender,country;
    TextToSpeech text;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_out);

        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);
        text = new TextToSpeech(getApplicationContext(), this,"com.google.android.tts");
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
                    text =new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int i) {

                            if(i!=TextToSpeech.ERROR){


                                text =new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int i) {
                                        if (i == TextToSpeech.SUCCESS) {




                                            Set<String> a=new HashSet<>();

                                            String vname = null;
                                            String l = null,c = null;

                                            if(gender.equals("Male") && (country.equals("fr"))){
                                                l="fr";
                                                c= "FR";
                                                vname = "fr-FR-language";
                                            }
                                            else if(gender.equals("Female") && (country.equals("fr"))){
                                                l="fr";
                                                c= "FR";
                                                vname = "fr-FR-language";
                                            }
                                            else if(gender.equals("Male") && country.equals("en")){
                                                l="en";
                                                c= "US";
                                                vname = "en-US-language";
                                            }
                                            else if(gender.equals("Female")&& country.equals("en")){
                                                l="en";
                                                c= "US";
                                                vname = "en-US-language";
                                            }
                                            Log.e("check",l+c+vname);


                                            Voice v=new Voice(vname,new Locale(l,c),400,200,true,a);

                                            String p = String.valueOf(text.getVoices());

                                            text.setVoice(v);


                                            text.speak(imageText, TextToSpeech.QUEUE_FLUSH, null);


                                        } else {
                                            Log.e("TTS", "Initilization Failed!");
                                        }

                                    }
                                });

                            }
                        }

                    });
                }
                });
            }
        }


    private String getImageText(int position) {
        String imageText = "";
        switch (position) {
            case 0:
                imageText = getString(R.string.h);
                break;
            case 1:
                imageText = getString(R.string.s);
                break;
            case 2:
                imageText = getString(R.string.l);
                break;
            case 3:
                imageText = getString(R.string.a);
                break;
            case 4:
                imageText = getString(R.string.atbest);
                break;
            case 5:
                imageText = getString(R.string.e);
                break;
            case 6:
                imageText = getString(R.string.li);
                break;
            case 7:
                imageText = getString(R.string.t);
                break;
            case 8:
                imageText = getString(R.string.sic);
                break;
            case 9:
                imageText = getString(R.string.tire);
                break;

        }
        return imageText;
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("Users");
        // Read from the database
        Query query = myRef.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getUid());

        myRef.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getUid());
        myRef.keepSynced(true);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    User value = dataSnapshot1.getValue(User.class);


                    gender = value.getGender();
                    country = value.getCoun();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }

    @Override
    public void onInit(int i) {

    }
}
