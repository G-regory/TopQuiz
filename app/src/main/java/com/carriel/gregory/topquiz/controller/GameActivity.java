package com.carriel.gregory.topquiz.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.carriel.gregory.topquiz.R;
import com.carriel.gregory.topquiz.model.Question;
import com.carriel.gregory.topquiz.model.QuestionBank;
import com.carriel.gregory.topquiz.model.User;

import java.util.Arrays;


public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_NUMBER_QUESTION = "current number question";
    public static final String BUNDLE_STATE_SCORE = "current score";

    private User mUser;


    private TextView mQuestionTextView;
    private Button mAnswerButton1;
    private Button mAnswerButton2;
    private Button mAnswerButton3;
    private Button mAnswerButton4;

    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;

    private int mScore;
    private int mNumberOfQuestions;

    private String mQuestionDisplay;
    private String mChoose1;
    private String mChoose2;
    private String mChoose3;
    private String mChoose4;


    private boolean mEnableTouchEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        System.out.println("GameActivity::onCreate()");

        init();

        if (savedInstanceState!=null){  //
            mScore=savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions=savedInstanceState.getInt(BUNDLE_STATE_NUMBER_QUESTION);

        }else {
            mScore=0;  //default score user
            mNumberOfQuestions=8; // number max of question
        }

        //lance les questions
        playGame();
    }

    private void init() {
        mQuestionBank =this.generateQuestions();



        // Wire widgets
        mQuestionTextView = findViewById(R.id.activity_game_question_text);
        mAnswerButton1 = findViewById(R.id.activity_game_answer1_btn);
        mAnswerButton2 = findViewById(R.id.activity_game_answer2_btn);
        mAnswerButton3 = findViewById(R.id.activity_game_answer3_btn);
        mAnswerButton4 = findViewById(R.id.activity_game_answer4_btn);

        mUser=new User();

        mAnswerButton1.setTag(0);
        mAnswerButton2.setTag(1);
        mAnswerButton3.setTag(2);
        mAnswerButton4.setTag(3);

        mAnswerButton1.setOnClickListener(this);
        mAnswerButton2.setOnClickListener(this);
        mAnswerButton3.setOnClickListener(this);
        mAnswerButton4.setOnClickListener(this);

        mEnableTouchEvent=true;
    }

    private void displayQuestion(Question mCurrentQuestion) {
        mQuestionDisplay=mCurrentQuestion.getQuestion();
        mChoose1=mCurrentQuestion.getChoiceList().get(0);
        mChoose2=mCurrentQuestion.getChoiceList().get(1);
        mChoose3=mCurrentQuestion.getChoiceList().get(2);
        mChoose4=mCurrentQuestion.getChoiceList().get(3);

        mQuestionTextView.setText(mQuestionDisplay);
        mAnswerButton1.setText(mChoose1);
        mAnswerButton2.setText(mChoose2);
        mAnswerButton3.setText(mChoose3);
        mAnswerButton4.setText(mChoose4);
    }

    private QuestionBank generateQuestions() {
        Question question1 = new Question("What is the name of the current french president?",
                Arrays.asList("François Hollande", "Emmanuel Macron", "Jacques Chirac", "François Mitterand"),
                1);

        Question question2 = new Question("How many countries are there in the European Union?",
                Arrays.asList("15", "24", "28", "32"),
                2);

        Question question3 = new Question("Who is the creator of the Android operating system?",
                Arrays.asList("Andy Rubin", "Steve Wozniak", "Jake Wharton", "Paul Smith"),
                0);

        Question question4 = new Question("When did the first man land on the moon?",
                Arrays.asList("1958", "1962", "1967", "1969"),
                3);

        Question question5 = new Question("What is the capital of Romania?",
                Arrays.asList("Bucarest", "Warsaw", "Budapest", "Berlin"),
                0);

        Question question6 = new Question("Who did the Mona Lisa paint?",
                Arrays.asList("Michelangelo", "Leonardo Da Vinci", "Raphael", "Carravagio"),
                1);

        Question question7 = new Question("In which city is the composer Frédéric Chopin buried?",
                Arrays.asList("Strasbourg", "Warsaw", "Paris", "Moscow"),
                2);

        Question question8 = new Question("What is the country top-level domain of Belgium?",
                Arrays.asList(".bg", ".bm", ".bl", ".be"),
                3);

        Question question9 = new Question("What is the size of the Eiffel Tower?",
                Arrays.asList("300m", "101m", "324m", "742m"),
                3);

        return new QuestionBank(Arrays.asList(question1,
                question2,
                question3,
                question4,
                question5,
                question6,
                question7,
                question8,
                question9));
    }

    /**
     * bloque l'écran temporairement
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvent && super.dispatchTouchEvent(ev);
    }

    /**
     * lors du clique sur un bouton
     * récupère son tag pour identifier le choix de l'utilisateur
     * comparer avec la bonne réponse et retourner un message Toast
     * @param v
     */
    @Override
    public void onClick(View v) {
        int reponseIndex = (int)v.getTag();

        if(reponseIndex == mCurrentQuestion.getAnswerIndex()){
            Toast.makeText(this, "great reponse", Toast.LENGTH_SHORT).show();
            mScore++;

        }else {
            Toast.makeText(this, "bad reponse", Toast.LENGTH_SHORT).show();
        }

        mEnableTouchEvent=false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvent=true;
                if(--mNumberOfQuestions<=0){
                    endGame();

                }else {
                    playGame();
                }
            }
        },2000);  //LENGTH_SHORT

    }


    /**
     * lance les questions
     */
    private void playGame() {
        mCurrentQuestion = mQuestionBank.getQuestion();
        displayQuestion(mCurrentQuestion);
    }

    /**
     * après le nombre max de question atteint
     * propose à l'utilisateur de rejouer ou d'arrêter la partie
     */
    private void endGame() {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Well Done")
                .setMessage("Votre score: "+mScore+"\n\n" +
                        "Une autre partie?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        playGame();
                        mNumberOfQuestions=8;
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent = new Intent();
                        myIntent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, myIntent);
                        mUser.setScoreUser(mScore);
                        finish();

                    }
                }).create()
                .show();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt(BUNDLE_STATE_NUMBER_QUESTION, mNumberOfQuestions);
        outState.putInt(BUNDLE_STATE_SCORE, mScore);

        super.onSaveInstanceState(outState);
    }

}
