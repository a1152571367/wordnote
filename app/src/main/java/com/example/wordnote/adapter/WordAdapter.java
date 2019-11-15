package com.example.wordnote.adapter;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordnote.R;
import com.example.wordnote.WordInDBToShowActivity;
import com.example.wordnote.WordnoteActivity;
import com.example.wordnote.domain.WordValue;
import com.example.wordnote.utils.WordStore;

import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {

    //用于存储查询出的数据wordvalue
    private List<WordValue> words;
    private int solution;


    //构造方法，获取对应控件
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView word;
        TextView word_interpret;
        View wordview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            word = (TextView)itemView.findViewById(R.id.word);
            word_interpret = (TextView)itemView.findViewById(R.id.word_interpret);
            wordview = itemView;
        }
    }

    public WordAdapter(List<WordValue> words,int solution) {
        this.words = words;
        this.solution= solution;
    }

    /**
     * 创建viweholder实例
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //获取view对象
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item,parent,false);
        //ViewHolder holder = new ViewHolder(view);
        //获取viewholder用于绑定点击事件
        final ViewHolder holder = new ViewHolder(view);
        //点击当前单词跳转到详细信息界面
        holder.wordview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取当前位置
                int position = holder.getAdapterPosition();
                //通过位置找到相应javabean
                WordValue wordValue = words.get(position);
                //intent跳转
                if (solution == 0) {
                    //竖屏情况，则跳转
                    Intent intent = new Intent(view.getContext(),WordInDBToShowActivity.class);
                    intent.putExtra("bean",wordValue);
                    view.getContext().startActivity(intent);
                }
                else if (solution == 1){
                    //竖屏情况
                    //将当前的wordvalue对象赋值进入工具类保存，跨文件通信
                    WordStore.storeValue = wordValue;
                    
                }
            }
        });
//        holder.word.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int position = holder.getAdapterPosition();
//                WordValue wordValue = words.get(position);
//                Toast.makeText(view.getContext(),"这是单词+"+wordValue.getWord(),Toast.LENGTH_LONG).show();
//            }
//        });
//        holder.word_interpret.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int position = holder.getAdapterPosition();
//                WordValue wordValue = words.get(position);
//                Toast.makeText(view.getContext(),"这是翻译+"+wordValue.getInterpret(),Toast.LENGTH_LONG).show();
//            }
//        });


        return holder;
    }

    /**
     * 对子项进行赋值
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        WordValue wordValue = words.get(position);
        holder.word.setText(wordValue.getWord());
        holder.word_interpret.setText(wordValue.getInterpret());
    }

    @Override
    public int getItemCount() {
        return words.size();
    }



}
