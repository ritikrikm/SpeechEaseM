package com.example.speechease;

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

import com.example.speechease.modelVH.internall_md;
import com.example.speechease.modelVH.template_md;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Edit_cat extends AppCompatActivity {
    EditText cat;
    Button addbtn;

    String key;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cat);
        cat = findViewById(R.id.upcate);

        addbtn= findViewById(R.id.addbtn);
        textView = findViewById(R.id.add_temp);
        textView.setText(R.string.edit_temp);
        addbtn.setText(R.string.update);
        Intent i = getIntent();
        key = i.getStringExtra("key");
        FirebaseDatabase database = FirebaseDatabase.getInstance()
                ;
        DatabaseReference myRef = database.getReference();
        Query query = myRef.child("TemplateCat").orderByChild("key").equalTo(key);

        Log.e("Key",key);

        // Read from the database
        myRef.keepSynced(true);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot appleSnapshot: snapshot.getChildren()) {
                    internall_md value = appleSnapshot.getValue(internall_md.class);
                    assert value != null;
                    cat.setText(value.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!cat.getText().toString().isEmpty()){

                    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("TemplateCat").child(key);
                    db.keepSynced(true);
                    db.child("name").setValue(cat.getText().toString());
                    db.child("uid").setValue(FirebaseAuth.getInstance().getUid());
                    db.child("key").setValue(key);
                    Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
                    finish();



                }
                else{
                    Toast.makeText(getApplicationContext(),R.string.empty,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}