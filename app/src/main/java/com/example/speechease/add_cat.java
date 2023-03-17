package com.example.speechease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class add_cat extends AppCompatActivity {
        EditText cat;
        Button addbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cat);
        cat = findViewById(R.id.upcate);
        addbtn= findViewById(R.id.addbtn);


        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!cat.getText().toString().isEmpty()){

//                    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("TemplateCat");
//                    dbref.keepSynced(true);
//                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if (snapshot.getValue() != null){
//
//                                Toast.makeText(getApplicationContext(),"User on this phone number already exists",Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });


                    String key = FirebaseDatabase.getInstance().getReference().child("TemplateCat").push().getKey();
                    assert key != null;
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("TemplateCat").child(key);
                    db.keepSynced(true);
                    db.child("name").setValue(cat.getText().toString());
                    db.child("uid").setValue(FirebaseAuth.getInstance().getUid());
                    db.child("key").setValue(key);
                    finish();



                }
                else{
                    Toast.makeText(getApplicationContext(),R.string.empty,Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}