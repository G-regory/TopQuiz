package com.carriel.gregory.topquiz.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.carriel.gregory.topquiz.R;
import com.carriel.gregory.topquiz.controller.sql.MySqlite;
import com.carriel.gregory.topquiz.model.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final String TAG="MainActivity";

    public static final int BUNDLE_CODE_REQUEST = 52;
    public static final String PREF_KEY_FIRSTNAME = "firstname";
    public static final String PREF_KEY_SCORE = "score";
    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton, mRankingButton;
    private User mUser;
    private MySqlite  mMySqlite;
    private List<User> listUsers;
    private boolean isBigger;  //si score est plus grand
    private boolean isEqual;  //si name es égale

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
                mPlayButton.setEnabled(s.toString().length() != 0); //active le bouton si 1 caractère est tapé
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strTMP = mNameInput.getText().toString();
                String firstName = strTMP.substring(0, 1).toUpperCase() + strTMP.substring(1);  //met la première lettre du nom est majuscule

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
                startActivity(rankingIntent);
            }

        });

    }

    private void init() {
        mUser = new User();
        mMySqlite= new MySqlite(this);
        isBigger=false;
        isEqual=false;

        listUsers= mMySqlite.restoreByNumber();  //récupère les données sur la table SQL

        mGreetingText =findViewById(R.id.activity_main_greeting_txt);
        mNameInput =  findViewById(R.id.activity_main_name_input);
        mPlayButton = findViewById(R.id.activity_main_play_btn);
        mRankingButton = findViewById(R.id.activity_main_classement);

        mPreferences=getPreferences(MODE_PRIVATE);

        mPlayButton.setEnabled(false);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
        isBigger=false;
        isEqual=false;

        for(User tempUser:listUsers){  //parcours la liste d'élément de la table SQL
            if(tempUser.getFirstname().equals(mUser.getFirstname())){ //si le me nom est déjà présent sur la table
                isEqual=true;
                if(mUser.getScoreUser() > tempUser.getScoreUser()){  // si le nouveau score obtenu est plus grand que le dernier, updateData()
                    isBigger=true;
                }
            }
        }

        if(isEqual && isBigger){
            if(isBigger) {
                mMySqlite.updateData(mUser);  //il s'agit d'une utilisateur déjà présent et qui à obtenu un meilleur score
            }
        }else if(!isEqual){
            mMySqlite.recordData(mUser);  //new user
        }

        mGreetingText.setText("Welcome back, "+mUser.getFirstname()+" !\nYour last score was "+score+", will you do better this time?");
        mNameInput.setText(firstName);
        mNameInput.setSelection(firstName.length());
        mPlayButton.setEnabled(true);

    }

    /**
     * à chaque lancement, vérifie si un score est déjà existant pour afficher le bouton TopScore
     */
    @Override
    protected void onResume() {
        listUsers= mMySqlite.restoreByNumber();
        if(listUsers.size()>0){
            mRankingButton.setVisibility(View.VISIBLE);
        }

        super.onResume();
    }
}

