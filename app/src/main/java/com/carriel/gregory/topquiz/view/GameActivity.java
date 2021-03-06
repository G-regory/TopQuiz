package com.carriel.gregory.topquiz.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.carriel.gregory.topquiz.R;
import com.carriel.gregory.topquiz.controler.Control;
import com.carriel.gregory.topquiz.model.QuestionBanks;
import com.carriel.gregory.topquiz.model.Question;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    //recover score from GameActivity
    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    private TextView mTextViewQuestion;
    private Button mButtonAnswer1;
    private Button mButtonAnswer2;
    private Button mButtonAnswer3;
    private Button mButtonAnswer4;
    private Control mControl;
    private Question mCurrentQuestion;
    private QuestionBanks mQuestionBanks;
    private int mScore;
    private int mNumberOfQuestion;
    private boolean mEnableTouchEvents;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        init();
        displayQuestion(mCurrentQuestion);
    }

    private void init() {
        mTextViewQuestion=findViewById(R.id.game_activity_ask_console_txt);
        mButtonAnswer1=findViewById(R.id.game_activity_answer1_btn);
        mButtonAnswer2=findViewById(R.id.game_activity_answer2_btn);
        mButtonAnswer3=findViewById(R.id.game_activity_answer3_btn);
        mButtonAnswer4=findViewById(R.id.game_activity_answer4_btn);
        mControl=Control.getInstance();
        mScore=0;
        mNumberOfQuestion=4;
        mEnableTouchEvents=true;

        mButtonAnswer1.setTag(0);
        mButtonAnswer2.setTag(1);
        mButtonAnswer3.setTag(2);
        mButtonAnswer4.setTag(3);

        mButtonAnswer1.setOnClickListener(this);
        mButtonAnswer2.setOnClickListener(this);
        mButtonAnswer3.setOnClickListener(this);
        mButtonAnswer4.setOnClickListener(this);

        //all Question is store here
        mQuestionBanks=mControl.generateQuestions();

        mCurrentQuestion = mQuestionBanks.getQuestion();
    }

    private void displayQuestion(Question pQuestion) {
        mTextViewQuestion.setText(pQuestion.getQuestion());
        mButtonAnswer1.setText(pQuestion.getChoiselist().get(0));
        mButtonAnswer2.setText(pQuestion.getChoiselist().get(1));
        mButtonAnswer3.setText(pQuestion.getChoiselist().get(2));
        mButtonAnswer4.setText(pQuestion.getChoiselist().get(3));

    }

    @Override
    public void onClick(View v) {
        int reponseIndex= (int) v.getTag();
        if(reponseIndex == mCurrentQuestion.getAnswerIndex()){
            Toast.makeText(this, "Good Answer ", Toast.LENGTH_SHORT).show();
            mScore++;
        }else{
            Toast.makeText(this, "Bad Answer ", Toast.LENGTH_SHORT).show();
        }
        mEnableTouchEvents=false;


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents=true;
                startQuestion();
            }
        },2000);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    private void startQuestion() {
        if(--mNumberOfQuestion == 0){ //if last question end game
            endGame();
        }else {  //next question
            mCurrentQuestion=mQuestionBanks.getQuestion();
            displayQuestion(mCurrentQuestion);
        }
    }

    private void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Fin de la partie")
                .setMessage("End of Game, your score is "+mScore)

                .setPositiveButton("New Game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mNumberOfQuestion=4;
                        mScore=0;

                    }
                })
                .setNegativeButton("End Game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent mainActivityIntent = new Intent(GameActivity.this, MainActivity.class);
                        mainActivityIntent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        //communique to the activity from call
                        setResult(RESULT_OK, mainActivityIntent);
                        finish();
                    }
                })
                .create()
                .show();
    }
}
