package com.example.speechease.fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.speechease.R;
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

import java.util.Locale;

public class template_next extends AppCompatActivity {
    BucketRecyclerView recyclerView;
    TextToSpeech text;
    private FirebaseRecyclerOptions<template_md> options;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter<template_md, temp_vh> adapter;
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

//                            Intent intent = new Intent(getApplicationContext(), Home_Login.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);


                      }
                      if(menuItem.getItemId() == R.id.delete){
//                            Intent intent = new Intent(getApplicationContext(), profile_activity.class);
//
//                            startActivity(intent);
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
                                // To Choose language of speech
                                text.setLanguage(Locale.UK);

                                text.speak(md.getName(),TextToSpeech.QUEUE_FLUSH,null);
                            }
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
    }


}