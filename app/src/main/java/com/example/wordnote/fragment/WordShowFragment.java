package com.example.wordnote.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordnote.R;
import com.example.wordnote.domain.WordValue;

public class WordShowFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wordshow,container,false);
        return view;
    }

    public void refresh(WordValue wordValue){
        View viewById = view.findViewById(R.id.vis_layout);
        viewById.setVisibility(View.VISIBLE);
        TextView key = (TextView) view.findViewById(R.id.key_land);
        EditText inter = (EditText) view.findViewById(R.id.inter_land);
        TextView UNps = (TextView) view.findViewById(R.id.UNps_land);
        TextView USps = (TextView) view.findViewById(R.id.USps_land);
        EditText word_orig = (EditText) view.findViewById(R.id.word_orig_land);
        EditText word_trans = (EditText) view.findViewById(R.id.word_trans_land);

        key.setText(wordValue.getWord());
        inter.setText(wordValue.getInterpret());
        UNps.setText(wordValue.getPsE());
        USps.setText(wordValue.getPsA());
        word_orig.setText(wordValue.getSentOrig());
        word_trans.setText(wordValue.getSentTrans());
    }
}
