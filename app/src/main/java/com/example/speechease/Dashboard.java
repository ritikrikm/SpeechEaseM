package com.example.speechease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.speechease.Utils.Save;
import com.example.speechease.Utils.SaveSelection;
import com.example.speechease.fragment.Textspeech;
import com.example.speechease.fragment.emojispeech;
import com.example.speechease.fragment.template;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class Dashboard extends AppCompatActivity {
    private Toast backToast;
    BottomNavigationView navigationView;
    private   FirebaseAuth mFirebaseAuth ;
    androidx.appcompat.widget.Toolbar toolbar;
    private long backPressedTime;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        navigationView=findViewById( R.id.bottom_nav );
         toolbar = findViewById(R.id.toolbar);
        toolbar.setSubtitle("Speech Ease");
        toolbar.setSubtitleTextColor(R.color.purple_500);
        toolbar.inflateMenu(R.menu.main_menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.engl){
                    setLocal(Dashboard.this,"en");
                    finish();
                    startActivity(getIntent());
                }
                if(item.getItemId()==R.id.french){
                    setLocal(Dashboard.this,"fr");
                    finish();
                    startActivity(getIntent());

                }
                if(item.getItemId()==R.id.logout) {
                    FirebaseAuth.getInstance().signOut();
                    //saving session
                    Save.save(getApplicationContext(), "session", "false");
                    Intent intent = new Intent(getApplicationContext(), Home_Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);


                }
                if(item.getItemId() == R.id.profile){
                    Intent intent = new Intent(getApplicationContext(), profile_activity.class);

                    startActivity(intent);
                }
                return false;
            }
        });
        if(!haveNetworkConnection()){
            Toast.makeText(Dashboard.this,R.string.network,Toast.LENGTH_LONG).show();
        }
        final SpeechText text=new SpeechText();
        final template template=new template();
        final emojispeech emojispeech=new emojispeech();

        navigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.textspeech){

                    setFragment( text );
                    return true;
                }
                else if(id==R.id.template){
                    setFragment(template);
                    return true;

                } else if(id==R.id.emoji){
                    setFragment( emojispeech );
                    return true;
                }

                return false;
            }
        } );
        navigationView.setSelectedItemId( R.id.textspeech);
    }
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            finish();
        } else {
            backToast = Toast.makeText(getBaseContext(), R.string.back, Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
    public void setLocal(Activity activity, String langcode){
        Locale locale = new Locale(langcode);
        locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
    }
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
    private synchronized void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace( R.id.frame,fragment );

        fragmentTransaction.commitNow();

    }
    public void SESSION(){
        String selection = SaveSelection.read(getApplicationContext(), "selection", "en");
        if(selection.equals("en")){

            setLocal(Dashboard.this,"en");


        }
        else{

            setLocal(Dashboard.this,"fr");


        }
        startActivity(getIntent());
        finish();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}