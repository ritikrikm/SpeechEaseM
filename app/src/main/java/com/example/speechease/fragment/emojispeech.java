package com.example.speechease.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.speechease.Adapter;
import com.example.speechease.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class emojispeech extends Fragment {

    RecyclerView recyclerView;
    List<String> names;
    List<Integer> images;
    Adapter adapter;

//    public emojispeech() {
//        // Required empty public constructor
//    }

//    public static emojispeech newInstance(String param1, String param2) {
//        emojispeech fragment = new emojispeech();
//        Bundle args = new Bundle();
//
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emojispeech, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);

        names = new ArrayList<>();
        images = new ArrayList<>();

        names.add(getString(Integer.parseInt(String.valueOf(R.string.happy))));
        names.add(getString(Integer.parseInt(String.valueOf(R.string.sad))));
        names.add(getString(Integer.parseInt(String.valueOf(R.string.laugh))));
        names.add(getString(Integer.parseInt(String.valueOf(R.string.angry))));
        names.add(getString(Integer.parseInt(String.valueOf(R.string.atb))));
        names.add(getString(Integer.parseInt(String.valueOf(R.string.excited))));
        names.add(getString(Integer.parseInt(String.valueOf(R.string.like))));
        names.add(getString(Integer.parseInt(String.valueOf(R.string.think))));
        names.add(getString(Integer.parseInt(String.valueOf(R.string.sick))));
        names.add(getString(Integer.parseInt(String.valueOf(R.string.tired))));

        images.add(R.drawable.happy);
        images.add(R.drawable.sad);
        images.add(R.drawable.laugh);
        images.add(R.drawable.angry);
        images.add(R.drawable.atb);
        images.add(R.drawable.cool);
        images.add(R.drawable.like);
        images.add(R.drawable.think);
        images.add(R.drawable.sick);
        images.add(R.drawable.tired);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2); // create a grid layout with 2 columns
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new Adapter(getActivity(), names, images); // create an adapter with data
        recyclerView.setAdapter(adapter); // set the adapter for the RecyclerView

        return view;





    }


}