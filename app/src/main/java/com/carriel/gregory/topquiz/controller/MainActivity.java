package com.carriel.gregory.topquiz.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.carriel.gregory.topquiz.R;
import com.carriel.gregory.topquiz.controller.sql.MySqlite;
import com.carriel.gregory.topquiz.model.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int BUNDLE_CODE_REQUEST = 52;
    public static final String PREF_KEY_FIRSTNAME = "firstname";
    public static final String PREF_KEY_SCORE = "score";
    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton, mRankingButton;
    private User mUser;
    private MySqlite  mMySqlite;
    private List<User> listUsers;
    boolean isBigger, isEqual;


    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPlayButton.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strTMP = mNameInput.getText().toString();
                String firstName = strTMP.substring(0, 1).toUpperCase() + strTMP.substring(1);

                mUser.setFirstname(firstName);
                mPreferences.edit().putString(PREF_KEY_FIRSTNAME,mUser.getFirstname()).apply();

                // User clicked the button
                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivityIntent, BUNDLE_CODE_REQUEST);

            }
        });

        mRankingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rankingIntent = new Intent(MainActivity.this, RankingActivity.class);
                rankingIntent.putExtra(PREF_KEY_FIRSTNAME,mUser.getFirstname());
                rankingIntent.putExtra(PREF_KEY_SCORE, mUser.getScoreUser() );
                Toast.makeText(MainActivity.this, "score "+mUser.getScoreUser(), Toast.LENGTH_SHORT).show();
                startActivity(rankingIntent);
            }

        });

    }

    private void init() {
        mUser = new User();
        mMySqlite= new MySqlite(this);
        isBigger=false;
        isEqual=false;

        listUsers= mMySqlite.restoreByNumber();

        mGreetingText =findViewById(R.id.activity_main_greeting_txt);
        mNameInput =  findViewById(R.id.activity_main_name_input);
        mPlayButton = findViewById(R.id.activity_main_play_btn);
        mRankingButton = findViewById(R.id.activity_main_classement);

        mPreferences=getPreferences(MODE_PRIVATE);

        mPlayButton.setEnabled(false);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(BUNDLE_CODE_REQUEST==requestCode && RESULT_OK==resultCode){
            int score=data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
            mPreferences.edit().putInt(PREF_KEY_SCORE, score).apply();

            greetUser();
        }

    }

    private void greetUser() {

        String firstName=mPreferences.getString(PREF_KEY_FIRSTNAME,null);
        mUser.setFirstname(firstName);
        int score = mPreferences.getInt(PREF_KEY_SCORE, 0);
        mUser.setScoreUser(score);
//        int counter = 0;
        isBigger=false;
        isEqual=false;
        for(User tempUser:listUsers){
            if(tempUser.getFirstname().equals(mUser.getFirstname())){
//                counter++;
                isEqual=true;
                if(mUser.getScoreUser() > tempUser.getScoreUser()){
                    isBigger=true;
                }
            }
        }

        if(isEqual && isBigger){
            Toast.makeText(this, "c'est le même nom", Toast.LENGTH_SHORT).show();
            if(isBigger) {
                mMySqlite.updateData(mUser);
            }
        }else {
            Toast.makeText(this, "ce n'est pas le même nom", Toast.LENGTH_SHORT).show();
            mMySqlite.recordData(mUser);
        }

        mGreetingText.setText("Welcome back, "+mUser.getFirstname()+" !\nYour last score was "+score+", will you do better this time?");
        mNameInput.setText(firstName);
        mNameInput.setSelection(firstName.length());
        mPlayButton.setEnabled(true);

    }

    @Override
    protected void onResume() {
        listUsers= mMySqlite.restoreByNumber();
        if(listUsers.size()>0){
            mRankingButton.setVisibility(View.VISIBLE);
        }

        super.onResume();
    }
}

