package com.example.speechease.fragment;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.speechease.Edit_cat;
import com.example.speechease.Home_Login;
import com.example.speechease.R;
import com.example.speechease.Utils.Save;
import com.example.speechease.add_cat;
import com.example.speechease.modelVH.internal_vh;
import com.example.speechease.modelVH.internall_md;
import com.example.speechease.profile_activity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Locale;


public class template extends Fragment {
    BucketRecyclerView rv_internall;
    DatabaseReference drinternall;
    FirebaseAuth firebaseAuth;
    private LinearLayoutManager mLayoutManager;
    FirebaseRecyclerOptions<internall_md> optionsinternall;
    FirebaseRecyclerAdapter<internall_md, internal_vh> adapterinternall;
    FloatingActionButton fab;
        // Required empty public constructor




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_template_cate,container,false);
        firebaseAuth= FirebaseAuth.getInstance();
        fab = view.findViewById(R.id.add_fab1);

        rv_internall = view.findViewById(R.id.rv_internall);
        mLayoutManager=new LinearLayoutManager(container.getContext());
        mLayoutManager.setReverseLayout( true );
        mLayoutManager.setStackFromEnd(true);

        rv_internall.setLayoutManager(mLayoutManager  );
        rv_internall.setAdapter( adapterinternall );
        drinternall = FirebaseDatabase.getInstance().getReference().child("TemplateCat");
        drinternall.keepSynced(true);
        Query query = drinternall.orderByChild("uid" ).equalTo( firebaseAuth.getUid() );
        optionsinternall = new FirebaseRecyclerOptions.Builder<internall_md>().setQuery(query,internall_md.class).build();
        adapterinternall = new FirebaseRecyclerAdapter<internall_md, internal_vh>(optionsinternall) {
            @Override
            protected void onBindViewHolder(@NonNull internal_vh holder, int position, @NonNull final internall_md model) {
                holder.cmpname.setText(model.getName());
                holder.tool.inflateMenu(R.menu.menu_temp);
                holder.tool.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId()==R.id.edit) {

                            Intent intent = new Intent(getContext(), Edit_cat.class);
                            intent.putExtra("key",model.getKey());

                            startActivity(intent);


                        }
                        if(item.getItemId() == R.id.delete){
                            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("TemplateCat").child(model.getKey());
                            db.keepSynced(true);
                            db.removeValue();
                        }
                        return false;
                    }
                });


                holder.cv.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), template_next.class);
                        intent.putExtra( "key",model.getKey() );
                        startActivity( intent );

                    }
                } );

            }

            @NonNull
            @Override
            public internal_vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new internal_vh(LayoutInflater.from(view.getContext()).inflate(R.layout.template_layout, parent,false));
            }
        };
        rv_internall.setAdapter(adapterinternall);
        adapterinternall.startListening();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), add_cat.class);


                        startActivity( intent );
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}