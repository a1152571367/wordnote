package com.example.wordnote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wordnote.adapter.WordAdapter;
import com.example.wordnote.database.DataBaseHelperDict;
import com.example.wordnote.domain.WordValue;
import com.example.wordnote.utils.WordStore;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WordnoteActivity extends AppCompatActivity {

    DataBaseHelperDict db ;

    Button button;
    EditText editText;

    TextView key;
    EditText inter;
    TextView UNps;
    TextView USps;
    EditText word_orig;
    EditText word_trans;

    List<WordValue> words = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordnote);

        db = new DataBaseHelperDict(this,"dict",null,2);
        final SQLiteDatabase database = db.getWritableDatabase();

        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (ori == mConfiguration.ORIENTATION_PORTRAIT) {//竖屏
            //竖屏
            button = (Button)findViewById(R.id.search_word);
            editText = (EditText)findViewById(R.id.input_to_search);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String str = editText.getText().toString();
                    words.clear();
                    Cursor cursor = database.rawQuery("select * from dict where word like '%"+str+"%'", null);
//                if (cursor.moveToFirst()){
//                    do {
//                        WordValue wordValue = new WordValue();
//                        String word = cursor.getString(cursor.getColumnIndex("word"));
//                        wordValue.setWord(word);
//                        String pse = cursor.getString(cursor.getColumnIndex("pse"));
//                        wordValue.setPsE(pse);
//                        String prone = cursor.getString(cursor.getColumnIndex("prone"));
//                        wordValue.setPronE(prone);
//                        String psa = cursor.getString(cursor.getColumnIndex("psa"));
//                        wordValue.setPsA(psa);
//                        String prona = cursor.getString(cursor.getColumnIndex("prona"));
//                        wordValue.setPronA(prona);
//                        String interpret = cursor.getString(cursor.getColumnIndex("interpret"));
//                        wordValue.setInterpret(interpret);
//                        String sentorig = cursor.getString(cursor.getColumnIndex("sentorig"));
//                        wordValue.setSentOrig(sentorig);
//                        String senttrans = cursor.getString(cursor.getColumnIndex("senttrans"));
//                        wordValue.setSentTrans(senttrans);
//                        words.add(wordValue);
//
//
//                    }while (cursor.moveToNext());
//                }
                    words = getCursorReturnList(cursor);
                    showListToScreen(words);
                }
            });

            //查询数据库
            Cursor cursor = database.query("dict", null, null, null, null, null, null);
            //显示到屏幕recycerview
            words = getCursorReturnList(cursor);
            showListToScreen(words);
        }





//        if (cursor.moveToFirst()){
//            do {
//                WordValue wordValue = new WordValue();
//                String word = cursor.getString(cursor.getColumnIndex("word"));
//                wordValue.setWord(word);
//                String pse = cursor.getString(cursor.getColumnIndex("pse"));
//                wordValue.setPsE(pse);
//                String prone = cursor.getString(cursor.getColumnIndex("prone"));
//                wordValue.setPronE(prone);
//                String psa = cursor.getString(cursor.getColumnIndex("psa"));
//                wordValue.setPsA(psa);
//                String prona = cursor.getString(cursor.getColumnIndex("prona"));
//                wordValue.setPronA(prona);
//                String interpret = cursor.getString(cursor.getColumnIndex("interpret"));
//                wordValue.setInterpret(interpret);
//                String sentorig = cursor.getString(cursor.getColumnIndex("sentorig"));
//                wordValue.setSentOrig(sentorig);
//                String senttrans = cursor.getString(cursor.getColumnIndex("senttrans"));
//                wordValue.setSentTrans(senttrans);
//                words.add(wordValue);
//
//
//            }while (cursor.moveToNext());
//        }


        //查询数据库
//        List<WordValue> words = LitePal.findAll(WordValue.class);

//        WordValue wordValue = new WordValue();
//        wordValue.setWord("haha");
//        wordValue.setInterpret("解释");
//        words.add(wordValue);

//        LitePal.initialize(this);

//        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.view);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        WordAdapter wordAdapter = new WordAdapter(words);
//        recyclerView.setAdapter(wordAdapter);




    }

//    public  List<String> getListFromDB(){
//
//        //新建对象
//        List<String> words = new ArrayList<>();
//
//        //查询数据库
//        SQLiteDatabase database = db.getWritableDatabase();
//        Cursor cursor = database.query("dict", null, null, null, null, null, null);
//        if (cursor.moveToFirst()){
//            do {
//                String word = cursor.getString(0);
//                words.add(word);
//
//            }while (cursor.moveToNext());
//        }
//
//
//        return words;
//    }

    /**
     * 传入光标得到查询结果的list集合
     * @param cursor 查询光标
     * @return
     */
    public List<WordValue> getCursorReturnList(Cursor cursor){
        List<WordValue> words = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                WordValue wordValue = new WordValue();
                String word = cursor.getString(cursor.getColumnIndex("word"));
                wordValue.setWord(word);
                String pse = cursor.getString(cursor.getColumnIndex("pse"));
                wordValue.setPsE(pse);
                String prone = cursor.getString(cursor.getColumnIndex("prone"));
                wordValue.setPronE(prone);
                String psa = cursor.getString(cursor.getColumnIndex("psa"));
                wordValue.setPsA(psa);
                String prona = cursor.getString(cursor.getColumnIndex("prona"));
                wordValue.setPronA(prona);
                String interpret = cursor.getString(cursor.getColumnIndex("interpret"));
                wordValue.setInterpret(interpret);
                String sentorig = cursor.getString(cursor.getColumnIndex("sentorig"));
                wordValue.setSentOrig(sentorig);
                String senttrans = cursor.getString(cursor.getColumnIndex("senttrans"));
                wordValue.setSentTrans(senttrans);
                words.add(wordValue);


            }while (cursor.moveToNext());
        }

//        if (words.size() > 0) {
//            Collections.sort(words, new Comparator<WordValue>() {
//                @Override
//                public int compare(final Campaign object1, final Campaign object2) {
//                    return object1.getName().compareTo(object2.getName());
//                }
//            });
//        }
        Collections.sort(words);
        return words;
    }

    public void showListToScreen(List<WordValue> words){
        //竖屏显示方法
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        WordAdapter wordAdapter = new WordAdapter(words,0);
        recyclerView.setAdapter(wordAdapter);
    }

    public void showListToScreen_land(List<WordValue> words){
        //横屏显示方法
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        WordAdapter wordAdapter = new WordAdapter(words,1);
        recyclerView.setAdapter(wordAdapter);
    }

    public void showIntoRight(){

            WordValue wordValue = WordStore.storeValue;
            key.setText(wordValue.getWord());
            inter.setText(wordValue.getInterpret());
            UNps.setText(wordValue.getPsE());
            USps.setText(wordValue.getPsA());
            word_orig.setText(wordValue.getSentOrig());
            word_trans.setText(wordValue.getSentTrans());

    }
}
