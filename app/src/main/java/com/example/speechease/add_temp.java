package com.example.speechease;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_temp extends AppCompatActivity {
    //created two view holder and model to store and get the database from the firebase

    TextView add_temp;
    EditText name;
    Button add;
    Intent i;
    String key;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cat);
        add_temp = findViewById(R.id.add_temp);
        name = findViewById(R.id.upcate);
        add = findViewById(R.id.addbtn);
        add_temp.setText(R.string.add_template);
        name.setHint(R.string.tempname);
        i = getIntent();
        key = i.getStringExtra("key");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!name.getText().toString().isEmpty()){
                String key2 = FirebaseDatabase.getInstance().getReference().child("Template").push().getKey();
                    assert key != null;
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Template").child(key2);
                    db.keepSynced(true);
                    db.child("name").setValue(name.getText().toString());
                    db.child("uid").setValue(FirebaseAuth.getInstance().getUid());
                    db.child("key2").setValue(key2);
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
