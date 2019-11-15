package com.example.wordnote.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordnote.R;
import com.example.wordnote.WordInDBToShowActivity;
import com.example.wordnote.adapter.WordAdapter;
import com.example.wordnote.domain.WordValue;
import com.example.wordnote.utils.WordStore;

import java.util.List;

public class WordLIstFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.words_frag,container,false);
        return view;

    }

    class landAdapter extends RecyclerView.Adapter<landAdapter.ViewHolder>{
        private List<WordValue> wordlist_all;

        public landAdapter(List<WordValue> wordlist) {
            wordlist_all = wordlist;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WordValue wordValue = wordlist_all.get(holder.getAdapterPosition());
                    Fragment fragment = getFragmentManager().findFragmentById(R.layout.fragment_wordshow);
                }
            });
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            WordValue wordValue = wordlist_all.get(position);
            
        }

        @Override
        public int getItemCount() {
            return wordlist_all.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            View title;
            public ViewHolder (View view){
                super(view);
                title = view;
            }
        }
    }
}
