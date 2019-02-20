package com.carriel.gregory.topquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewWelcome;
    private EditText mEditTextName;
    private Button mButtonPlayGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    /**
     * initialise les views
     */
    private void init() {
        mTextViewWelcome=findViewById(R.id.activity_main_msg_welcom_txt);
        mEditTextName=findViewById(R.id.activity_main_name_editTxt);
        mButtonPlayGame=findViewById(R.id.activity_main_play_btn);
    }


}
