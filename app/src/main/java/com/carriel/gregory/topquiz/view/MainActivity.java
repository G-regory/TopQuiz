package com.carriel.gregory.topquiz.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.carriel.gregory.topquiz.R;
import com.carriel.gregory.topquiz.controler.Control;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewWelcome;
    private EditText mEditTextName;
    private Button mButtonPlayGame;
    private Control mControl;
    private SharedPreferences mSharedPreferences;
    private String mName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        controlNameInput();
        controlName();
    }

    /**
     * initialize views
     */
    private void init() {
        mTextViewWelcome=findViewById(R.id.activity_main_msg_welcom_txt);
        mEditTextName=findViewById(R.id.activity_main_name_editTxt);
        mButtonPlayGame=findViewById(R.id.activity_main_play_btn);
        mControl=Control.getInstance();
        mSharedPreferences=getPreferences(MODE_PRIVATE);
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

                mButtonPlayGame.setEnabled(s.length() > 3);
                changeActivity();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * change activity from MainActivity to GameActivity
     */
    private void changeActivity(){
        mButtonPlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName=mEditTextName.getText().toString();
                mControl.setName(mName );
                mSharedPreferences.edit().putString("name",mName).apply();
                Intent myIntent=new Intent(MainActivity.this, GameActivity.class);
                startActivity(myIntent);
            }
        });
    }

    /**
     * check if username is not empty
     * if not empty, show msg
     */
    private void controlName(){
        mName=mSharedPreferences.getString("name","What's your name?");
        if(mName==null){
            Toast.makeText(this, "Aucune session enregistré", Toast.LENGTH_SHORT).show();
        }else{
            mTextViewWelcome.setText("Welcome! "+mName+" Let's Play!!!!!");
            mEditTextName.setText(mName);
        }
    }
}
