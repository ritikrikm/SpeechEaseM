package com.example.speechease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.speechease.Utils.Save;
import com.example.speechease.Utils.SaveSelection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.Objects;

public class Selection extends AppCompatActivity {
Button eng;
Button fre;
    FirebaseUser firebaseUser;
    FirebaseAuth mFirebaseAuth;
    SharedPreferences sharedPref;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences.Editor editor;
    public static final String country = "nameKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);


        sharedPref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
         editor = sharedPref.edit();


        SESSION();
        eng = findViewById(R.id.eng);
        fre = findViewById(R.id.fre);
        mFirebaseAuth = FirebaseAuth.getInstance();





        eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocal(Selection.this,"en");
//                SaveSelection.save(getApplicationContext(), "selection", "en");
                editor.putString(country,"en");
                editor.apply();


                finish();
                Intent intent = new Intent(Selection.this, Home.class);
                startActivity(intent);


            }
        });
        fre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocal(Selection.this,"fr");
//                SaveSelection.save(getApplicationContext(), "selection", "fr");
                editor.putString(country,"fr");
                editor.apply();
              //  db.child("coun").setValue("fr");
                finish();
                Intent intent = new Intent(Selection.this, Home.class);
                startActivity(intent);
            }
        });
    }

    public void SESSION(){
       // boolean selection = Boolean.parseBoolean(SaveSelection.read(getApplicationContext(), "selection", "en"));
        String check = sharedPref.getString(country,"");

        if(check.equals("en")){

            setLocal(Selection.this,"en");
            finish();
            Intent intent = new Intent(Selection.this, Home.class);
            intent.putExtra("country", country);
            startActivity(intent);
        }
        else if(check.equals("fr")){

            setLocal(Selection.this,"fr");
            finish();
            Intent intent = new Intent(Selection.this, Home.class);
            intent.putExtra("country", country);
            startActivity(intent);

        }

    }


    public void setLocal(Activity activity,String langcode){
        Locale locale = new Locale(langcode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
    }
}