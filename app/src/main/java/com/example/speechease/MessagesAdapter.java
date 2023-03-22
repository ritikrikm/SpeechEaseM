package com.example.speechease;

import static android.content.ContentValues.TAG;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.speechease.Utils.SaveSelection;
import com.example.speechease.Utils.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MessagesAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Messages> messagesArrayList;
    TextToSpeech text;
    String gender;
    String country;


    int ITEM_SEND=1;
    int ITEM_RECIEVE=2;

    int ITEM_LOUD = 3;

    public MessagesAdapter(Context context, ArrayList<Messages> messagesArrayList) {
        this.context = context;
        this.messagesArrayList = messagesArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        if(viewType==ITEM_SEND)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.senderchatlayout,parent,false);

            return new SenderViewHolder(view);
        }
        else if(viewType == ITEM_LOUD){
            View view= LayoutInflater.from(context).inflate(R.layout.senderchatlayout,parent,false);
            return new speaker(view);
        }
        else
        {
            View view= LayoutInflater.from(context).inflate(R.layout.recieverchatlayout,parent,false);
            return new RecieverViewHolder(view);
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Messages messages=messagesArrayList.get(position);

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

        if(holder.getClass()==SenderViewHolder.class)
        {

            Log.e("HI","WORKING");
            SenderViewHolder viewHolder=(SenderViewHolder)holder;
            viewHolder.textViewmessaage.setText(messages.getMessage());
            viewHolder.timeofmessage.setText(messages.getCurrenttime());
            viewHolder.loudbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    @SuppressLint("RestrictedApi") Context c = getApplicationContext();
                    text = new TextToSpeech(c, new TextToSpeech.OnInitListener() {


                        @Override
                        public void onInit(int i) {
                            if (i == TextToSpeech.SUCCESS) {
                                    notifyDataSetChanged();


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

                                text.speak(messages.getMessage(), TextToSpeech.QUEUE_FLUSH, null);
//                                            }

                            } else {
                                Log.e("TTS", "Initilization Failed!");
                            }
//                            if(i!=TextToSpeech.ERROR){
//                                // To Choose language of speech
//                                notifyDataSetChanged();
//                                text.setLanguage(Locale.CANADA_FRENCH);
//                                text.speak(messages.getMessage(),TextToSpeech.QUEUE_FLUSH,null);
//
//                            }


                        }
                    });


                }
            });


        }
       else if(holder.getClass() == speaker.class){
            speaker viewHolder=(speaker)holder;
           // viewHolder.loudbtn.sp
            Log.e("HI","WORKING");
        }
        else
        {
            RecieverViewHolder viewHolder=(RecieverViewHolder)holder;
            viewHolder.textViewmessaage.setText(messages.getMessage());
            viewHolder.timeofmessage.setText(messages.getCurrenttime());
        }








    }


    @Override
    public int getItemViewType(int position) {
        Messages messages=messagesArrayList.get(position);


        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSenderId()))

        {
            return  ITEM_SEND;
        }
        
        else
        {
            return ITEM_RECIEVE;
        }
    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }







class speaker extends RecyclerView.ViewHolder{
        ImageButton loudbtn;


    public speaker(@NonNull View itemView) {
        super(itemView);
        loudbtn = itemView.findViewById(R.id.sp_btn);
    }
}
    class SenderViewHolder extends RecyclerView.ViewHolder
    {

        TextView textViewmessaage;
        TextView timeofmessage;
        ImageButton loudbtn;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewmessaage=itemView.findViewById(R.id.sendermessage);
            timeofmessage=itemView.findViewById(R.id.timeofmessage);
            loudbtn = itemView.findViewById(R.id.sp_btn);


        }
    }

    class RecieverViewHolder extends RecyclerView.ViewHolder
    {

        TextView textViewmessaage;
        TextView timeofmessage;


        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewmessaage=itemView.findViewById(R.id.sendermessage);
            timeofmessage=itemView.findViewById(R.id.timeofmessage);
        }
    }




}


