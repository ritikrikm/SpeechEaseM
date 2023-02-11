package com.example.speechease.fragment;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class NewChat extends AppCompatActivity {
    BucketRecyclerView rv_internall;
    EditText mgetmessage;

    ImageButton msendmessagebutton;

    CardView msendmessagecardview;
    androidx.appcompat.widget.Toolbar mtoolbarofspecificchat;
    ImageView mimageviewofspecificuser;
    TextView mnameofspecificuser;
    private FirebaseFirestore firebaseFirestore;
    private String enteredmessage;
    Intent i;
    String mrecievername,sendername,mrecieveruid,msenderuid;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    String senderroom,recieverroom;

    ImageButton mbackbuttonofspecificchat;

    RecyclerView mmessagerecyclerview;

    String currenttime;
    Calendar calendar;
    TextToSpeech text;
    SimpleDateFormat simpleDateFormat;
String key;
    MessagesAdapter messagesAdapter;

    ArrayList<Messages> messagesArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

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
        i = getIntent();
         key = i.getStringExtra("key");

        msenderuid=firebaseAuth.getUid();
        mrecieveruid=FirebaseDatabase.getInstance().getReference().child("chats")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid() ).push().getKey();

        mrecievername="Unknown";

        senderroom=msenderuid+mrecieveruid;
        recieverroom=mrecieveruid+msenderuid;

        DatabaseReference databaseReference=firebaseDatabase.getReference().child("chats").child(firebaseAuth.getUid()+key);
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

       // myRef.FirebaseAuth.getInstance().getUid();.setValue(messages);
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
                    Messages messages=new Messages(firebaseAuth.getUid()+key,"Unknown",enteredmessage,firebaseAuth.getUid(),date.getTime(),currenttime);


                    firebaseDatabase=FirebaseDatabase.getInstance();


                    firebaseDatabase.getReference().child("chats")
                            .child(firebaseAuth.getUid()+key)

                            .push().setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                        Log.e("HEY","HIII");

                                }
                            });

                    mgetmessage.setText(null);



                }




            }
        });



    }
    private void updatenameoncloudfirestore() {

        firebaseFirestore=FirebaseFirestore.getInstance();
      //  CollectionReference documentReference = firebaseFirestore.collection("Users").document(firebaseAuth.getUid()).collection(firebaseAuth.getUid()+key);
        DocumentReference documentReference=firebaseFirestore.collection("Users").document(firebaseAuth.getUid()+key);

        Map<String , Object> userdata=new HashMap<>();
        userdata.put("name","Unknown");

        userdata.put("uid",firebaseAuth.getUid());
        userdata.put("status","Online");
        userdata.put("key",key);


        documentReference.set(userdata).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Toast.makeText(getApplicationContext(),"Profile Update Succusfully",Toast.LENGTH_SHORT).show();

            }
        });

    }
    protected void onStart() {
        super.onStart();
        updatenameoncloudfirestore();

//        DocumentReference documentReference=firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
//        documentReference.update("status","Online").addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Toast.makeText(getApplicationContext(),"Now User is Online",Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}