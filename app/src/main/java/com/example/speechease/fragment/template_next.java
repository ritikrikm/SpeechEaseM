package com.example.speechease.fragment;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.speechease.Edit_cat;
import com.example.speechease.Edit_cat_next;
import com.example.speechease.R;
import com.example.speechease.Utils.Save;
import com.example.speechease.Utils.SaveSelection;
import com.example.speechease.Utils.User;
import com.example.speechease.add_cat;
import com.example.speechease.add_temp;
import com.example.speechease.modelVH.internal_vh;
import com.example.speechease.modelVH.internall_md;
import com.example.speechease.modelVH.temp_vh;
import com.example.speechease.modelVH.template_md;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class template_next extends AppCompatActivity implements TextToSpeech.OnInitListener {
    BucketRecyclerView recyclerView;
    String gender;
    String country;
    TextToSpeech text;
     FirebaseRecyclerOptions<template_md> options;
     DatabaseReference databaseReference;
     FirebaseRecyclerAdapter<template_md, temp_vh> adapter;
    FirebaseAuth firebaseAuth;

    FloatingActionButton fab;
    Intent i;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_template_cate);
        fab = findViewById(R.id.add_fab1);
        recyclerView = findViewById(R.id.rv_internall);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseAuth= FirebaseAuth.getInstance();
        text = new TextToSpeech(getApplicationContext(), this,"com.google.android.tts");

         i = getIntent();
        key = i.getStringExtra("key");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Template")
                ;
        databaseReference.keepSynced(true);
        Query query = databaseReference.orderByChild("key").equalTo(key);
        options = new FirebaseRecyclerOptions.Builder<template_md>().setQuery(query,template_md.class).build();

        adapter = new FirebaseRecyclerAdapter<template_md, temp_vh>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final temp_vh tv, int i, @NonNull final template_md md) {
                tv.toolbar.inflateMenu(R.menu.menu_temp);

              tv.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                  @Override
                  public boolean onMenuItemClick(MenuItem menuItem) {
                      if(menuItem.getItemId()==R.id.edit) {
                          Intent intent = new Intent(getApplicationContext(), Edit_cat_next.class);
                          intent.putExtra("key2",md.getKey2());

                          startActivity(intent);
//                            Intent intent = new Intent(getApplicationContext(), Home_Login.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);


                      }
                      if(menuItem.getItemId() == R.id.delete){
                          DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Template").child(md.getKey2());
                          db.keepSynced(true);
                          db.removeValue();
                      }
                      return false;
                  }
              });
        tv.name.setText(md.getName());


        tv.cv.setOnClickListener(new View.OnClickListener() {
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
                                                c= "CA";
                                                vname = "fr-CA-language";
                                            }
                                            else if(gender.equals("Male") && country.equals("en")){
                                                l="en";
                                                c= "US";
                                                vname = "en-US-language";
                                            }
                                            else if(gender.equals("Female")&& country.equals("en")){
                                                l="en";
                                                c= "GB";
                                                vname = "en-GB-language";
                                            }

                Log.e("Checkthis",l+c+vname);



                                            Voice v=new Voice(vname,new Locale(l,c),400,200,true,a);




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

                                                text.speak(md.getName(), TextToSpeech.QUEUE_FLUSH, null);
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





            }


            @NonNull
            @Override
            public temp_vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new temp_vh( LayoutInflater.from(template_next.this).inflate(R.layout.fragment_template,parent,false));
            }
        };
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), add_temp.class);
                intent.putExtra("key", key);

                startActivity( intent );
            }
        });


    }
    protected void onStart(){
        super.onStart();
        adapter.startListening();
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
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//                    User value = dataSnapshot1.getValue(User.class);
//                    assert value != null;
//                    gender = value.getGender();
//                    country = value.getCoun();
//
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
    }

    @Override
    public void onInit(int i) {

    }

//    private class TTSInit extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            t=new TextToSpeech(getContext(), status -> {
//                if (status == TextToSpeech.SUCCESS) {
//                    t.setLanguage(Locale.ENGLISH);
//                    /* now you can invoke speak() */
//                }
//            });
//            return null;
//        }
//    }
}