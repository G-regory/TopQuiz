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

import com.carriel.gregory.topquiz.R;
import com.carriel.gregory.topquiz.model.User;

import static java.lang.System.out;

public class MainActivity extends AppCompatActivity {


    public static final int BUNDLE_CODE_REQUEST = 52;
    public static final String PREF_KEY_FIRSTNAME = "prenom";
    public static final String PREF_KEY_SCORE = "score";
    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton, mClassementButton;
    private User mUser;

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        greetUser();


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

                String firstName = mNameInput.getText().toString();
                mUser.setFirstname(firstName);
                mPreferences.edit().putString(PREF_KEY_FIRSTNAME,mUser.getFirstname()).apply();

                // User clicked the button
                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivityIntent, BUNDLE_CODE_REQUEST);


            }
        });

    }

    private void init() {
        mUser = new User();

        mGreetingText =findViewById(R.id.activity_main_greeting_txt);
        mNameInput =  findViewById(R.id.activity_main_name_input);
        mPlayButton = findViewById(R.id.activity_main_play_btn);
        mClassementButton= findViewById(R.id.activity_main_classement);

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


        String firstName=mPreferences.getString("prenom",null);
        int score= mPreferences.getInt(PREF_KEY_SCORE, 0);
        mGreetingText.setText("Welcom back, "+firstName+"!\nYour last score was "+score+", will you do better this time?");
        mNameInput.setText(firstName);
        mNameInput.setSelection(firstName.length());
        mPlayButton.setEnabled(true);

    }


    @Override
    protected void onStart() {
        super.onStart();

        out.println("MainActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        out.println("MainActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        out.println("MainActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        out.println("MainActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        out.println("MainActivity::onDestroy()");
    }

    }

