package com.example.wordnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wordnote.database.DataBaseHelperDict;
import com.example.wordnote.domain.WordValue;

public class WordInDBToShowActivity extends AppCompatActivity {

    TextView key;
    EditText inter;
    TextView UNps;
    TextView USps;
    EditText word_orig;
    EditText word_trans;

    Button button_update_db;
    Button button_delect_db;

    DataBaseHelperDict dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_in_dbto_show);

        //获取javabean
        final WordValue wordValue = (WordValue) getIntent().getSerializableExtra("bean");

        dbhelper = new DataBaseHelperDict(this,"dict",null,2);
        final SQLiteDatabase db = dbhelper.getWritableDatabase();

        //获取控件
        key = (TextView) findViewById(R.id.key);
        inter = (EditText) findViewById(R.id.inter);
        UNps = (TextView) findViewById(R.id.UNps);
        USps = (TextView) findViewById(R.id.USps);
        word_orig = (EditText) findViewById(R.id.word_orig);
        word_trans = (EditText) findViewById(R.id.word_trans);
        button_update_db = (Button)findViewById(R.id.button_update_db);
        button_delect_db = (Button)findViewById(R.id.button_delect_db);

        //为各个textview设置文本
        key.setText(wordValue.getWord());
        inter.setText(wordValue.getInterpret());
        UNps.setText(wordValue.getPsE());
        USps.setText(wordValue.getPsA());
        word_orig.setText(wordValue.getSentOrig());
        word_trans.setText(wordValue.getSentTrans());


        //为按钮绑定单击事件监听器
        button_delect_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //从数据库中删除当前项，并跳转回原activity
                int i = db.delete("dict", "word=?", new String[]{wordValue.getWord()});
                if (i>0){
                    Toast.makeText(WordInDBToShowActivity.this,"删除成功",Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(WordInDBToShowActivity.this,"删除失败",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(WordInDBToShowActivity.this,WordnoteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //更新事件
        button_update_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("interpret",inter.getText().toString());
                values.put("sentorig",word_orig.getText().toString());
                values.put("senttrans",word_trans.getText().toString());
                int i = db.update("dict", values, "word = ?", new String[]{key.getText().toString()});
                if (i>0){
                    Toast.makeText(WordInDBToShowActivity.this,"更新成功",Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(WordInDBToShowActivity.this,"更新失败",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(WordInDBToShowActivity.this,WordnoteActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
