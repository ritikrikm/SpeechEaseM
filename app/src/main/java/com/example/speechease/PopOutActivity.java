package com.example.speechease;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.speechease.Utils.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class PopOutActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    //TextView name;
    ImageView image;
    TextToSpeech text;
    String gender,country;

    ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_out);

        Intent intent = getIntent();
        //String name = intent.getStringExtra("emojiName");
        String emojispeech = intent.getStringExtra("emojiSpeech");
        // name = findViewById(R.id.emoji);
        image = findViewById(R.id.images);
        button = findViewById(R.id.audio);
        text = new TextToSpeech(getApplicationContext(), this,"com.google.android.tts");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text =new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {

                        if(i!=TextToSpeech.ERROR){


                            text =new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                                @Override
                                public void onInit(int i) {
                                    if (i == TextToSpeech.SUCCESS) {




//                                            Intent installIntent = new Intent();
//                                installIntent.setAction(
//                                        TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
//                                startActivity(installIntent);

                                        Set<String> a=new HashSet<>();
//                                            String lang = Locale.getDefault().;

                                        //Voice v=new Voice("en-us-x-sfg#female_2-local",new Locale("en","US"),400,200,true,a);



                                        String vname = null;
                                        String l = null,c = null;
//                                           boolean selection=  Boolean.valueOf( Save.read(getApplicationContext(),"selection","true"));

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


//                                            if(lang.equals("fr")){
//                                                l  = String.valueOf(text.setLanguage(Locale.FRENCH));
//                                                c = "FR";
//                                            }
//                                            else{
//                                                l  = String.valueOf(text.setLanguage(Locale.ENGLISH));
//                                                c = "US";
//                                            }



                                        Voice v=new Voice(vname,new Locale(l,c),400,200,true,a);

                                        String p = String.valueOf(text.getVoices());


//                                            Log.d("Hello",p);
                                        text.setVoice(v);
                                        //text.setSpeechRate(0.8f);

//                                            // int result = T2S.setLanguage(Locale.US);
//                                            int result = text.setVoice(v);
//
//                                            if (result == TextToSpeech.LANG_MISSING_DATA
//                                                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                                                Log.e("TTS", "This Language is not supported");
//                                            } else {
                                        // btnSpeak.setEnabled(true);

                                        text.speak(emojispeech, TextToSpeech.QUEUE_FLUSH, null);
//                                            }

                                    } else {
                                        Log.e("TTS", "Initilization Failed!");
                                    }
//                                        if(i!=TextToSpeech.ERROR){
//                                            // To Choose language of speech
//                                            text.setLanguage(Locale.UK);
//                                            Set<String> a=new HashSet<>();
//                                        a.add("male");
//                                            text.setVoice(new Voice("en-us-x-sfg#male_2-local",new Locale("en","US"),400,200,true,a));
//                                            text.speak(md.getName(),TextToSpeech.QUEUE_FLUSH,null);
//                                            Log.d("UID",md.getUid());
//                                        }
                                }
                            });
//                                Set<String> a=new HashSet<>();
//                                a.add("male");//here you can give male if you want to select male voice.
//                                //Voice v=new Voice("en-us-x-sfg#female_2-local",new Locale("en","US"),400,200,true,a);
//                                String s = String.valueOf(text.setLanguage(Locale.US));
//                                Voice v=new Voice("en-us-x-sfg#male_6-local",new Locale("en",s),400,200,true,a);
//                                text.setVoice(v);
//                                text.setSpeechRate(0.8f);
//
//
//                                    // btnSpeak.setEnabled(true);
//                                    Log.e("TTS", "HI");
////                                    text.setLanguage(Locale.ENGLISH);
//                                    text.speak(md.getName(),TextToSpeech.QUEUE_FLUSH,null);
                        }



//                                text.setLanguage(Locale.ENGLISH);
//
//                                text.speak(md.getName(),TextToSpeech.QUEUE_FLUSH,null);
                    }

                });
            }
        });


        image.setImageResource(intent.getIntExtra("image",0));


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