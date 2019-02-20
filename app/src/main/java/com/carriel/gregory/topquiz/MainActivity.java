package com.carriel.gregory.topquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
        controlNameInput();
    }

    /**
     * initialize views
     */
    private void init() {
        mTextViewWelcome=findViewById(R.id.activity_main_msg_welcom_txt);
        mEditTextName=findViewById(R.id.activity_main_name_editTxt);
        mButtonPlayGame=findViewById(R.id.activity_main_play_btn);
    }

    /**
     * Check if user type name
     * if yes active button
     * else inactive button
     */
    private void controlNameInput(){
        mButtonPlayGame.setEnabled(false);
        mEditTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mButtonPlayGame.setEnabled(s.length()>3);
// if(s.length()>3){
//                    mButtonPlayGame.setEnabled(true);
//                }else {
//                    mButtonPlayGame.setEnabled(false);
//
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
