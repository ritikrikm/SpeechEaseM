package com.example.speechease;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.speechease.Messages;
import com.example.speechease.MessagesAdapter;
import com.example.speechease.R;
import com.example.speechease.Utils.firebasemodel;
import com.example.speechease.fragment.BucketRecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Specificchat extends AppCompatActivity {
    BucketRecyclerView rv_internall;
    EditText mgetmessage;
    int i=0;
    ImageButton msendmessagebutton;
    TextToSpeech text;
    CardView msendmessagecardview;
    androidx.appcompat.widget.Toolbar mtoolbarofspecificchat;
    ImageView mimageviewofspecificuser;
    TextView mnameofspecificuser;
    private FirebaseFirestore firebaseFirestore;
    private String enteredmessage;

    String mrecievername,sendername,mrecieveruid,msenderuid;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    String senderroom,recieverroom;

    ImageButton mbackbuttonofspecificchat;

    RecyclerView mmessagerecyclerview;

    String currenttime;
    String nid;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    MessagesAdapter messagesAdapter;

    ArrayList<Messages> messagesArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);
        Intent i = getIntent();
          nid = i.getStringExtra("ntid");

        mgetmessage=findViewById(R.id.getmessage);
        msendmessagecardview=findViewById(R.id.carviewofsendmessage);
        msendmessagebutton=findViewById(R.id.imageviewsendmessage);
        mtoolbarofspecificchat=findViewById(R.id.toolbarofspecificchat);
        mnameofspecificuser=findViewById(R.id.Nameofspecificuser);

        mbackbuttonofspecificchat=findViewById(R.id.backbuttonofspecificchat);

        messagesArrayList=new ArrayList<>();
        mmessagerecyclerview=findViewById(R.id.rv1);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mmessagerecyclerview.setLayoutManager(linearLayoutManager);
        messagesAdapter=new MessagesAdapter(getApplicationContext(),messagesArrayList);
        mmessagerecyclerview.setAdapter(messagesAdapter);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        calendar=Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("hh:mm a");


        msenderuid=firebaseAuth.getUid();
        mrecieveruid=FirebaseDatabase.getInstance().getReference().child("msgs")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid() ).push().getKey();
        mrecievername="Unknown";

        senderroom=nid;
        recieverroom=mrecieveruid+msenderuid;
        firebaseFirestore = FirebaseFirestore.getInstance();

       // mnameofspecificuser.setText(firebasemodel.getName());



        msendmessagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                enteredmessage=mgetmessage.getText().toString();
                if(enteredmessage.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter message first",Toast.LENGTH_SHORT).show();
                }

                else

                {
                    text =new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int i) {

                            if(i!=TextToSpeech.ERROR){
                                // To Choose language of speech
                                text.setLanguage(Locale.UK);
                                text.speak(enteredmessage,TextToSpeech.QUEUE_FLUSH,null);
                            }
                        }
                    });

                    Date date=new Date();
                    currenttime=simpleDateFormat.format(calendar.getTime());
                    Messages messages=new Messages(nid,"Unknown",enteredmessage,firebaseAuth.getUid(),date.getTime(),currenttime);
                    firebaseDatabase=FirebaseDatabase.getInstance();

                    firebaseDatabase.getReference().child("chats")
                            .child(nid)

                            .push().setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    onStart();
//                                    firebaseDatabase.getReference()
//                                            .child("chats")
//                                            .child(recieverroom)
//                                            .child("messages")
//                                            .push()
//                                            .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Void> task) {
//
//                                                }
//                                            });
                                }
                            });

                    mgetmessage.setText(null);



                }




            }
        });



    }
    protected void onStart() {
        super.onStart();
        DatabaseReference databaseReference=firebaseDatabase.getReference().child("chats").child(nid);
        messagesAdapter=new MessagesAdapter(getApplicationContext(),messagesArrayList);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();

                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    Messages messages=snapshot1.getValue(Messages.class);
                    messagesArrayList.add(messages);
                }
                messagesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        DocumentReference documentReference=firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
//        documentReference.update("status","Online").addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Toast.makeText(getApplicationContext(),"Now User is Online",Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}