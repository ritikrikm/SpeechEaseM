package com.example.speechease.fragment;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.speechease.ChatActivity;
import com.example.speechease.NameChange;
import com.example.speechease.Specificchat;
import com.example.speechease.Utils.firebasemodel;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.speechease.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class Textspeech extends Fragment {


    LinearLayoutManager linearLayoutManager;
    FirestoreRecyclerAdapter<firebasemodel,NoteViewHolder> chatAdapter;
    ImageView mimageviewofuser;

    // Firestore
    BucketRecyclerView mrecyclerview;
   FloatingActionButton mAddFab;
    private FirestoreRecyclerOptions<firebasemodel> options;
    FirebaseRecyclerAdapter<firebasemodel,ChatActivity> adapter;
    @Override
    public void onStart() {
        super.onStart();
        chatAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        if(chatAdapter!=null)
        {
            chatAdapter.stopListening();
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_textspeech,container,false);
       // rv_internall = v.findViewById(R.id.recyclerview);
        mrecyclerview=v.findViewById(R.id.recyclerview);


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        mrecyclerview.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAddFab = v.findViewById(R.id.add_fab);

        mAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewChat.class);
                String key = FirebaseDatabase.getInstance().getReference().child("msgs")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid() ).push().getKey();
                intent.putExtra("key",key);
                startActivity(intent);
            }
        });

        Query query= firebaseFirestore.collection("Users").whereEqualTo("uid",firebaseAuth.getUid());
        FirestoreRecyclerOptions<firebasemodel> allusername=new FirestoreRecyclerOptions.Builder<firebasemodel>().setQuery(query,firebasemodel.class).build();

        chatAdapter=new FirestoreRecyclerAdapter<firebasemodel, NoteViewHolder>(allusername) {
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i, @NonNull firebasemodel firebasemodel) {

                noteViewHolder.particularusername.setText(firebasemodel.getName());

                if(firebasemodel.getStatus().equals("Online"))
                {

                    noteViewHolder.statusofuser.setText(firebasemodel.getStatus());
                    noteViewHolder.statusofuser.setTextColor(Color.GREEN);


                }
                else
                {
                    noteViewHolder.statusofuser.setText(firebasemodel.getStatus());
                }
                noteViewHolder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(getActivity(), NameChange.class);

                        intent.putExtra("name",firebasemodel.getName());
                        intent.putExtra("ntid",firebasemodel.getUid());
                        intent.putExtra("key",firebasemodel.getKey());

                        startActivity(intent);
                    }
                });
                noteViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(getActivity(),Specificchat.class);

                        intent.putExtra("name",firebasemodel.getName());
                        intent.putExtra("ntid",firebasemodel.getUid());
                        intent.putExtra("key",firebasemodel.getKey());

                        startActivity(intent);
                    }
                });



            }

            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.chatviewlayout,parent,false);
                return new NoteViewHolder(view);
            }
        };


        mrecyclerview.setHasFixedSize(true);
        linearLayoutManager = new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
       // linearLayoutManager=new LinearLayoutManager(getContext());
      //  linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mrecyclerview.setLayoutManager(linearLayoutManager);
        mrecyclerview.setAdapter(chatAdapter);

        ItemTouchHelper.SimpleCallback simpleCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        //Toast.makeText(Notifications.this,"swiped",Toast.LENGTH_SHORT).show();
                        // int id = viewHolder.getBindingAdapterPosition();
                        int id = viewHolder.getBindingAdapterPosition();

                        String uid = chatAdapter.getItem(id).getUid();
                        String key = chatAdapter.getItem(id).getKey();

                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                        alert.setTitle("Delete")
                                        .setMessage("Are you sure?")
                                                .setNegativeButton("No",null)
                                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                            firebaseFirestore.collection("Users").document(uid+key).delete();

                                                                FirebaseDatabase.getInstance().getReference().child("chats")
                                                                        .child( uid+key).removeValue();

                                                            }
                                                        });
alert.show();



                    }


                    @Override
                    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                        new RecyclerViewSwipeDecorator.Builder(getContext(),c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                                .addSwipeRightActionIcon(R.drawable.ic_delete_black_24dp)
                                .addSwipeRightBackgroundColor(getResources().getColor(R.color.purple))
                                .create()
                                .decorate();




                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }


                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);

        itemTouchHelper.attachToRecyclerView(mrecyclerview);


        return v;

    }


    public class NoteViewHolder extends RecyclerView.ViewHolder
    {

        private TextView particularusername;
        private TextView statusofuser;
        private ImageView edit;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            particularusername=itemView.findViewById(R.id.nameofuser);
            statusofuser=itemView.findViewById(R.id.statusofuser);
           // mimageviewofuser=itemView.findViewById(R.id.imageviewofuser);
            edit = itemView.findViewById(R.id.edit);



        }

    }




}