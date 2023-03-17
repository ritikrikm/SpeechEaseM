package com.example.speechease;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.speechease.Utils.Save;
import com.example.speechease.Utils.SaveSelection;

import java.util.Locale;

public class Selection extends AppCompatActivity {
Button eng;
Button fre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        SESSION();
        eng = findViewById(R.id.eng);
        fre = findViewById(R.id.fre);
        eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocal(Selection.this,"en");
                SaveSelection.save(getApplicationContext(), "selection", "true");
                finish();
                Intent intent = new Intent(Selection.this, Home.class);
                startActivity(intent);


            }
        });
        fre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocal(Selection.this,"fr");
                SaveSelection.save(getApplicationContext(), "selection", "false");
                finish();
                Intent intent = new Intent(Selection.this, Home.class);
                startActivity(intent);
            }
        });
    }

    public void SESSION(){
        String selection = SaveSelection.read(getApplicationContext(), "selection", "en");
        if(selection.equals("en")){

            setLocal(Selection.this,"en");
            finish();
            Intent intent = new Intent(Selection.this, Home.class);
            startActivity(intent);
        }
        else{

            setLocal(Selection.this,"fr");
            finish();
            Intent intent = new Intent(Selection.this, Home.class);
            startActivity(intent);

        }

    }


    public void setLocal(Activity activity,String langcode){
        Locale locale = new Locale(langcode);
        locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
    }
}