package com.example.speechease;

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

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MessagesAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Messages> messagesArrayList;
    TextToSpeech text;


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
                                Set<String> a=new HashSet<>();
                                a.add("male");//here you can give male if you want to select male voice.
                                String lang = Locale.getDefault().getLanguage();
                                String l,c;
                                if(lang.equals("fr")){
                                    l  = String.valueOf(text.setLanguage(Locale.FRENCH));
                                    c = "FR";
                                }
                                else{
                                    l  = String.valueOf(text.setLanguage(Locale.ENGLISH));
                                    c = "US";
                                }

//                                Intent installIntent = new Intent();
//                                installIntent.setAction(
//                                        TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
//                                startActivity(installIntent);



                                Voice v=new Voice("en-us-x-sfg#male_2-local",new Locale(l,c),400,200,true,a);
                                text.setVoice(v);
                                text.setSpeechRate(0.8f);

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
