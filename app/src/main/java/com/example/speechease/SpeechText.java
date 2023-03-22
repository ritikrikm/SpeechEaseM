package com.example.speechease;

import static android.content.ContentValues.TAG;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.speechease.Utils.SaveSelection;
import com.example.speechease.Utils.User;
import com.example.speechease.fragment.BucketRecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


public class SpeechText extends Fragment {
    BucketRecyclerView rv_internall;
    EditText mgetmessage;
    String country;
    Button clr_btn;
    String gender;
    SwipeRefreshLayout swipeContainer;

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
    String key,h_key;

    MessagesAdapter messagesAdapter;

    ArrayList<Messages> messagesArrayList;


    public SpeechText() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_new_chat,container,false);

        mgetmessage=v.findViewById(R.id.getmessage);
        msendmessagecardview=v.findViewById(R.id.carviewofsendmessage);
        msendmessagebutton=v.findViewById(R.id.imageviewsendmessage);
        clr_btn = v.findViewById(R.id.clr_btn);
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);


            auto();


        messagesArrayList=new ArrayList<>();
        mmessagerecyclerview=v.findViewById(R.id.rv1);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        mmessagerecyclerview.setLayoutManager(linearLayoutManager);
        messagesAdapter=new MessagesAdapter(getContext(),messagesArrayList);
        mmessagerecyclerview.setAdapter(messagesAdapter);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        calendar=Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("hh:mm a");
         key = FirebaseDatabase.getInstance().getReference().child("msgs")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid() ).push().getKey();
          h_key = key;
        msenderuid=firebaseAuth.getUid();
        mrecieveruid=FirebaseDatabase.getInstance().getReference().child("chats")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid() ).push().getKey();

        mrecievername="Unknown";

        senderroom=msenderuid+mrecieveruid;
        recieverroom=mrecieveruid+msenderuid;
        messagesAdapter.notifyDataSetChanged();

                clr_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("chats");
                        ref.child(firebaseAuth.getUid()).removeValue();
                        getActivity().recreate();

                    }
                });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                messagesAdapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }
        });
        msendmessagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                enteredmessage=mgetmessage.getText().toString();
                if(enteredmessage.isEmpty())
                {
                    Toast.makeText(getContext(),"Enter message first",Toast.LENGTH_SHORT).show();
                }

                else

                {
                    auto();
                    text =new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int i) {
                            if (i == TextToSpeech.SUCCESS) {



//                                            Intent installIntent = new Intent();
//                                installIntent.setAction(
//                                        TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
//                                startActivity(installIntent);

                                Set<String> a=new HashSet<>();
//                                            String lang = Locale.getDefault().;

                                //Voice v=new Voice("en-us-x-sfg#female_2-local",new Locale("en","US"),400,200,true,a);

                                String vname = null;
                                String l = null,c = null;
//                                           boolean selection=  Boolean.valueOf( Save.read(getApplicationContext(),"selection","true"));

                                if(gender.equals("Male") && (country.equals("fr"))){
                                    l="fr";
                                    c= "FR";
                                    vname = "fr-FR-language";
                                }
                                else if(gender.equals("Female") && (country.equals("fr"))){
                                    l="fr";
                                    c= "FR";
                                    vname = "fr-FR-language";
                                }
                                else if(gender.equals("Male") && country.equals("en")){
                                    l="en";
                                    c= "US";
                                    vname = "en-US-language";
                                }
                                else if(gender.equals("Female")&& country.equals("en")){
                                    l="en";
                                    c= "US";
                                    vname = "en-US-language";
                                }


//                                            if(lang.equals("fr")){
//                                                l  = String.valueOf(text.setLanguage(Locale.FRENCH));
//                                                c = "FR";
//                                            }
//                                            else{
//                                                l  = String.valueOf(text.setLanguage(Locale.ENGLISH));
//                                                c = "US";
//                                            }



                                Voice v=new Voice(vname,new Locale(l,c),400,200,true,a);

                                String p = String.valueOf(text.getVoices());


                                Log.d("Hello",p);
                                text.setVoice(v);
                                //text.setSpeechRate(0.8f);

//                                            // int result = T2S.setLanguage(Locale.US);
//                                            int result = text.setVoice(v);
//
//                                            if (result == TextToSpeech.LANG_MISSING_DATA
//                                                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                                                Log.e("TTS", "This Language is not supported");
//                                            } else {
                                // btnSpeak.setEnabled(true);

                                text.speak(enteredmessage, TextToSpeech.QUEUE_FLUSH, null);
//                                            }

                            } else {
                                Log.e("TTS", "Initilization Failed!");
                            }
//                            if(i!=TextToSpeech.ERROR){
//                                // To Choose language of speech
//                                text.setLanguage(Locale.UK);
//                                text.speak(enteredmessage,TextToSpeech.QUEUE_FLUSH,null);
//                            }
                        }
                    });





                    Date date=new Date();
                    currenttime=simpleDateFormat.format(calendar.getTime());
                    Messages messages=new Messages(firebaseAuth.getUid()+key,"Unknown",enteredmessage,firebaseAuth.getUid(),date.getTime(),currenttime);
                    Log.e("Check",firebaseAuth.getUid()+key);

                    firebaseDatabase=FirebaseDatabase.getInstance();


                    firebaseDatabase.getReference().child("chats")
                            .child(firebaseAuth.getUid())

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

        // Inflate the layout for this fragment
        return v;
    }

    private void auto() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeContainer.setRefreshing(true);
                swipeContainer.setRefreshing(false);
                Log.e("Enter","HI");
            }
        }, 500);
    }

    public void onStart() {
        super.onStart();
        auto();
        DatabaseReference databaseReference=firebaseDatabase.getReference().child("chats").child(firebaseAuth.getUid());

        messagesAdapter=new MessagesAdapter(getContext(),messagesArrayList);

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

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("Users");
        // Read from the database
        Query query = myRef.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getUid());

        myRef.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getUid());
        myRef.keepSynced(true);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    User value = dataSnapshot1.getValue(User.class);


                    gender = value.getGender();
                    country = value.getCoun();
                }

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

    @Override
    public void onResume() {
        super.onResume();
        auto();
    }
}