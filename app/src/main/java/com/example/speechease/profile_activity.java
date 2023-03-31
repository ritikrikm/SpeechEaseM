package com.example.speechease;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.speechease.Utils.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class profile_activity extends AppCompatActivity {
    Spinner spinnerLanguages;
    ImageView info1,info2;
    TextView name,email,contact;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    String cg;
    Button btn,cv,changeTTSBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = findViewById(R.id.name_profile);
        info1 = findViewById(R.id.info1);
        info2 = findViewById(R.id.info2);
        email = findViewById(R.id.email_profile);
        contact = findViewById(R.id.conta);
        btn = findViewById(R.id.upda_prof_btn);
        cv = findViewById(R.id.change_voice);
        changeTTSBtn = findViewById(R.id.change_tts);
        firebaseAuth=FirebaseAuth.getInstance();
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                                            Intent installIntent = new Intent();
                                installIntent.setAction(
                                        TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                                startActivity(installIntent);
            }
        });

        changeTTSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("com.android.settings.TTS_SETTINGS");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
info1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i=new Intent(getApplicationContext(),info1_popup.class);
        startActivity(i);
    }
});
info2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i=new Intent(getApplicationContext(),info2_popup.class);
        startActivity(i);

    }
});
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("Users");
        // Read from the database
        Query query = myRef.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getUid());
        myRef.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getUid());
        myRef.keepSynced(true);
        Log.e("uuuid",FirebaseAuth.getInstance().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    User value = dataSnapshot1.getValue(User.class);

                    String n = value.name;
                    String n_one=value.email;
                    String n_two=value.contactn;
                    name.setText(n);
                    email.setText( n_one );
                    contact.setText(value.code+ n_two );
                    if(value.getGender().equals("Male")){
                        spinnerLanguages.setSelection(0);
                    }
                    else if(value.getGender().equals("Female"))
                        spinnerLanguages.setSelection(1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




         spinnerLanguages=findViewById(R.id.spinner_languages);

        ArrayAdapter<CharSequence>adapter= ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);


        spinnerLanguages.setAdapter(adapter);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == btn){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Users");
                    myRef.keepSynced(true);
                    String text = spinnerLanguages.getSelectedItem().toString();
                    myRef.child(FirebaseAuth.getInstance().getUid()).child("gender").setValue(text);
                    Toast.makeText(profile_activity.this,R.string.pu,Toast.LENGTH_LONG).show();
                    finish();

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}