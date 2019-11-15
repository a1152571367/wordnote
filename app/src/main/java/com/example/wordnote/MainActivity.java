package com.example.wordnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;
import android.widget.Toast;

import com.example.wordnote.database.DataBaseHelperDict;
import com.example.wordnote.domain.WordValue;
import com.example.wordnote.wordcontainer.Dict;

import org.litepal.LitePal;
import org.litepal.exceptions.DataSupportException;

import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity {

    public static final CountDownLatch ctl = new CountDownLatch(1);

    //    Button button;
    EditText input_word;
    Button button_search;
    Button button_store;
    DataBaseHelperDict dataBaseHelperDict;
    Dict dict;
    Dict dictnew;
    TextView output;
    WordValue word = null;
    TextView database;
    Button newdictbutton;

    TextView key;
    TextView inter;
    TextView UNps;
    TextView USps;
    TextView word_orig;
    TextView word_trans;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        button = (Button) findViewById(R.id.button);
        //database = (TextView) findViewById(R.id.database);
        input_word = (EditText) findViewById(R.id.word_input);
        //dataBaseHelperDict = new DataBaseHelperDict(this, "worddict.db", null, 2);
        dict = new Dict(this, "dict");
        dictnew = new Dict(this,"newdict");
        newdictbutton = (Button) findViewById(R.id.button_store_newdict);

        button_search = (Button) findViewById(R.id.button_search);
        button_store = (Button) findViewById(R.id.button_store);
        output = (TextView) findViewById(R.id.output);

        key = (TextView) findViewById(R.id.key_main);
        inter = (TextView) findViewById(R.id.inter_main);
        UNps = (TextView) findViewById(R.id.UNps_main);
        USps = (TextView) findViewById(R.id.USps_main);
//        word_orig = (TextView)findViewById(R.id.word_orig_main);
//        word_trans = (TextView)findViewById(R.id.word_trans_main);


//        button_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if ("".equals(input_word.getText().toString())) {
//                    Toast.makeText(MainActivity.this, "请输入单词", Toast.LENGTH_SHORT).show();
//                } else {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            word = dict.getWordFromInternet(input_word.getText().toString());
//
//                        }
//                    }).start();
//                    ctl.countDown();
//
//
////                        Thread thread = new Thread(new Runnable() {
////                            @Override
////                            public void run() {
////                               word = dict.getWordFromInternet(input_word.getText().toString());
////                            }
////                        });
////                        thread.setPriority(Thread.MAX_PRIORITY);
////                        thread.start();
//
////                    try {
////                        thread.join();
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
//
//                    if (word != null) {
//                        //Toast.makeText(MainActivity.this, word.getInterpret(), Toast.LENGTH_LONG).show();
//                        output.setText(word.getInterpret());
//                    } else {
//                        Toast.makeText(MainActivity.this, "未找到相关单词释义", Toast.LENGTH_LONG).show();
//                    }
//                }
//                word = null;
//
//
//            }
//        });

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if ("".equals(input_word.getText().toString())) {
                            //判断是否输入
                            showToastInThread("请输入单词");
                            //Toast.makeText(MainActivity.this, "请输入单词", Toast.LENGTH_SHORT).show();
                        } else {
                            word = dict.getWordFromInternet(input_word.getText().toString());
//                            if (word == null && word.getInterpret() == null) {
//                                //未找到相应单词
//                                showToastInThread("未找到相关单词释义");
//                                Log.d("MainActivity","if条件");
//                                //Toast.makeText(MainActivity.this, "未找到相关单词释义", Toast.LENGTH_LONG).show();
//                            } else {
//                                //打印到屏幕上
//                                showResearchWordInterpret(word);
//                                Log.d("MainActivity","else条件");
//                            }
                            if (word != null && word.getInterpret() != null && !"".equals(word.getPsA())) {
                                showResearchWordInterpret(word);
                                Log.d("MainActivity", "if条件");
                                Log.d("MainActivity", "释义"+word.getInterpret());
                            } else {
                                showToastInThread("未找到相关释义");
                                Log.d("MainActivity", "else条件");
                            }
                        }
                    }
                }).start();
            }
        });

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if ("".equals(input_word.getText().toString())) {
//
//                    Toast.makeText(MainActivity.this, "请输入单词", Toast.LENGTH_SHORT).show();
//                } else {
//                    dataBaseHelperDict.getWritableDatabase();
//
//
//                }
//            }
//        });

        button_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if ("".equals(input_word.getText().toString())) {
                            //没做任何输入
                            showToastInThread("请输入单词");
                            //  Toast.makeText(MainActivity.this, "请输入单词", Toast.LENGTH_SHORT).show();
                        } else {
                            //输入不空
                            //从数据库中查找
                            word = dict.getWordFromDict(input_word.getText().toString());
                            if (word == null) {
                                //若为空,说明数据库中没有此单词
                                //从网络获取
                                word = dict.getWordFromInternet(input_word.getText().toString());
                                if (word == null) {
                                    //拼写错误
                                    showToastInThread("请检查单词是否拼写正确");
                                    // Toast.makeText(MainActivity.this, "请检查单词是否拼写正确", Toast.LENGTH_LONG).show();
                                } else {
                                    //查找完毕
                                    dict.insertWordToDict(word, true);
                                    showToastInThread("单词已存入数据库");
                                    // Toast.makeText(MainActivity.this, "单词已存入数据库", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                //词典（数据库）中有
                                //showToastInThread(word.getInterpret());
                                showToastInThread("该单词已存入数据库,请不要重复操作");
                                //Toast.makeText(MainActivity.this,"该单词已存入数据库,请不要重复操作",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }).start();
            }
        });

//        newdictbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if ("".equals(input_word.getText().toString())) {
//                            //没做任何输入
//                            showToastInThread("请输入单词");
//                            //  Toast.makeText(MainActivity.this, "请输入单词", Toast.LENGTH_SHORT).show();
//                        } else {
//                            //输入不空
//                            //从数据库中查找
//                            word = dictnew.getWordFromDict(input_word.getText().toString());
//                            if (word == null) {
//                                //若为空,说明数据库中没有此单词
//                                //从网络获取
//                                word = dictnew.getWordFromInternet(input_word.getText().toString());
//                                if (word == null) {
//                                    //拼写错误
//                                    showToastInThread("请检查单词是否拼写正确");
//                                    // Toast.makeText(MainActivity.this, "请检查单词是否拼写正确", Toast.LENGTH_LONG).show();
//                                } else {
//                                    //查找完毕
//                                    dictnew.insertWordToDict(word, true);
//                                    showToastInThread("单词已存入数据库");
//                                    // Toast.makeText(MainActivity.this, "单词已存入数据库", Toast.LENGTH_LONG).show();
//                                }
//                            } else {
//                                //词典（数据库）中有
//                                //showToastInThread(word.getInterpret());
//                                showToastInThread("该单词已存入数据库,请不要重复操作");
//                                //Toast.makeText(MainActivity.this,"该单词已存入数据库,请不要重复操作",Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    }
//                }).start();
//            }
//        });

//        button_store.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if ("".equals(input_word.getText().toString())) {
//                    Toast.makeText(MainActivity.this, "请输入单词", Toast.LENGTH_SHORT).show();
//                } else {
//                    //输入不空
//                    word = dict.getWordFromDict(input_word.getText().toString());
//                    if (word == null) {
//                        //数据库中没有当前单词
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                //查找单词
//                                word = dict.getWordFromInternet(input_word.getText().toString());
//                            }
//                        }).start();
//                        if (word == null) {
//                            //单词拼写错误
//                            Toast.makeText(MainActivity.this, "请检查单词是否拼写正确", Toast.LENGTH_LONG).show();
//                        } else {
//                            dict.insertWordToDict(word, true);
//                            Toast.makeText(MainActivity.this, "单词已存入数据库", Toast.LENGTH_LONG).show();
//                        }
//                    } else {
//                        //单词已存在
//                        database.setText(word.getSentOrig());
//                        Toast.makeText(MainActivity.this, word.getInterpret(), Toast.LENGTH_LONG).show();
//                        //Toast.makeText(MainActivity.this,"该单词已存入数据库,请不要重复操作",Toast.LENGTH_LONG).show();
//                    }
//
//                }
//            }
//        });

    }

    /**
     * 创建活动菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    /**
     * 定义菜单相应事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //分别对每个菜单选项进行绑定
        Intent intent;
        switch (item.getItemId()) {
            case R.id.wordnote:
                intent = new Intent(MainActivity.this, WordnoteActivity.class);
                startActivity(intent);
                break;
            case R.id.help:
                intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
                break;

        }

        return true;
    }

    /**
     * 线程中的ui操作，用于把参数显示到屏幕上
     *
     * @param wordValue
     */
    private void showResearchWordInterpret(final WordValue wordValue) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在此进行ui操作

                output.setText(wordValue.getInterpret());
                key.setText(wordValue.getWord());
                inter.setText(wordValue.getInterpret());
                UNps.setText(wordValue.getPsE());
                USps.setText(wordValue.getPsA());
//                word_orig.setText(wordValue.getSentOrig());
//                word_trans.setText(wordValue.getSentTrans());
            }
        });
    }

    /**
     * 子线程中弹出toast
     *
     * @param string 需要显示的提示信息
     */
    private void showToastInThread(final String string) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, string, Toast.LENGTH_LONG).show();
            }
        });
    }
}
