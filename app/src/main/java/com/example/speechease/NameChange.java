package com.example.speechease;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NameChange extends AppCompatActivity {
        EditText et;
        Button upbtn;
    private FirebaseFirestore  firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_change);
        firebaseFirestore=FirebaseFirestore.getInstance();
        et = findViewById(R.id.upname);
        upbtn = findViewById(R.id.updatebtn);
        upbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),R.string.empty,Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent i = getIntent();
                   String nid = i.getStringExtra("ntid");
                   String key = i.getStringExtra("key");
                    DocumentReference documentReference=firebaseFirestore.collection("Users").document(nid+key);
                    Map<String , Object> userdata=new HashMap<>();
                    userdata.put("name",et.getText().toString());
                    userdata.put("uid",nid);
                    userdata.put("status","Online");
                    userdata.put("key",key);
                        documentReference.set(userdata).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(),R.string.name_updated,Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });

                }
            }
        });


    }
}