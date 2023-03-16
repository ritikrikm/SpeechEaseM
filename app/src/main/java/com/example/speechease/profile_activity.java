package com.example.speechease;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.speechease.Utils.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile_activity extends AppCompatActivity {
    Spinner spinnerLanguages;
    TextView name,email,contact;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    String cg;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = findViewById(R.id.name_profile);
        email = findViewById(R.id.email_profile);
        contact = findViewById(R.id.conta);
        btn = findViewById(R.id.upda_prof_btn);
        firebaseAuth=FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        // Read from the database
        myRef.keepSynced(true);
        myRef.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
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
                    else
                        spinnerLanguages.setSelection(1);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
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
                    Toast.makeText(profile_activity.this,"Profile Updated",Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}