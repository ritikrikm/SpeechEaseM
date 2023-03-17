package com.example.speechease;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.speechease.Utils.User;
import com.example.speechease.modelVH.template_md;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Edit_cat_next extends AppCompatActivity {
    TextView add_temp;
    EditText name;
    Button add;
    Intent i;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cat);
        add_temp = findViewById(R.id.add_temp);
        name = findViewById(R.id.upcate);
        add = findViewById(R.id.addbtn);
        add_temp.setText(R.string.edit_temp);

        add.setText(R.string.update);
        i = getIntent();
        key = i.getStringExtra("key2");
        FirebaseDatabase database = FirebaseDatabase.getInstance()
                ;
        DatabaseReference myRef = database.getReference();
        Query query = myRef.child("Template").orderByChild("key2").equalTo(key);

        Log.e("Key",key);

        // Read from the database
        myRef.keepSynced(true);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot appleSnapshot: snapshot.getChildren()) {
                    template_md value = appleSnapshot.getValue(template_md.class);
                    assert value != null;
                    name.setText(value.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!name.getText().toString().isEmpty()){

                    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Template").child(key);
                    db.keepSynced(true);
                    db.child("name").setValue(name.getText().toString());
                    db.child("uid").setValue(FirebaseAuth.getInstance().getUid());
                    db.child("key2").setValue(key);

                    Toast.makeText(getApplicationContext(),R.string.updated,Toast.LENGTH_SHORT).show();
                    finish();



                }
                else{
                    Toast.makeText(getApplicationContext(),R.string.empty,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}