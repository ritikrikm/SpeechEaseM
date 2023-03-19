package com.example.speechease.fragment;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.speechease.PopOutActivity;
import com.example.speechease.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link emojispeech#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emojispeech extends Fragment {

    GridView gridView;
    String[] emojis = {"I am Happy", "I am sad", "that's so funny", "I am angry", "all the best", "Great, I am excited", "I love it", "I am thinking"};
    String[] emojisDisplayName = {"Happy", "Sad", "Laugh", "Angry", "All the best", "Excited", "Like", "Thinking"};
    int[] emojiImages = {R.drawable.happy, R.drawable.sad, R.drawable.laugh, R.drawable.angry, R.drawable.atb, R.drawable.cool, R.drawable.like, R.drawable.think};

    public emojispeech() {
        // Required empty public constructor
    }

    public static emojispeech newInstance(String param1, String param2) {
        emojispeech fragment = new emojispeech();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_emojispeech, container, false);

        gridView = inflate.findViewById(R.id.gridview);
        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                @SuppressLint("RestrictedApi") Intent intent = new Intent(getApplicationContext(), PopOutActivity.class);
//                intent.putExtra("name", emojis[i]);
                String emoji = emojis[i];
                //String emojiDispName = emojisDisplayName[i];
                intent.putExtra("image", emojiImages[i]);
                intent.putExtra("emojiSpeech", emoji);
                //intent.putExtra("emojiName", emoji);
                Log.e("CHeck", emoji);
                startActivity(intent);
            }
        });



        return inflate;
    }

    private class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return emojiImages.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.row_data, null);

            TextView name = view1.findViewById(R.id.emoji);
            ImageView image = view1.findViewById(R.id.images);

            name.setText(emojisDisplayName[i]);
            image.setImageResource(emojiImages[i]);
            return view1;
        }
    }
}