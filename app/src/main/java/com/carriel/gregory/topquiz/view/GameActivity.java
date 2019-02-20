package com.carriel.gregory.topquiz.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.carriel.gregory.topquiz.R;
import com.carriel.gregory.topquiz.controler.Control;
import com.carriel.gregory.topquiz.model.QuestionBanks;
import com.carriel.gregory.topquiz.model.Question;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    TextView mTextViewQuestion;
    Button mButtonAnswer1;
    Button mButtonAnswer2;
    Button mButtonAnswer3;
    Button mButtonAnswer4;
    private Control mControl;
    Question mcurrentQuestion;
    QuestionBanks mQuestionBanks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        init();
    }

    private void init() {
        mTextViewQuestion=findViewById(R.id.game_activity_ask_console_txt);
        mButtonAnswer1=findViewById(R.id.game_activity_answer1_btn);
        mButtonAnswer2=findViewById(R.id.game_activity_answer2_btn);
        mButtonAnswer3=findViewById(R.id.game_activity_answer3_btn);
        mButtonAnswer4=findViewById(R.id.game_activity_answer4_btn);
        mControl=Control.getInstance();

        mButtonAnswer1.setTag(0);
        mButtonAnswer2.setTag(1);
        mButtonAnswer3.setTag(2);
        mButtonAnswer4.setTag(3);

        mButtonAnswer1.setOnClickListener(this);
        mButtonAnswer2.setOnClickListener(this);
        mButtonAnswer3.setOnClickListener(this);
        mButtonAnswer4.setOnClickListener(this);

        mQuestionBanks=mControl.generateQestions();
        mcurrentQuestion = mQuestionBanks.getQuestion();
        displayQuestion(mcurrentQuestion);

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
        if(reponseIndex ==mcurrentQuestion.getAnswerIndex()){
            Toast.makeText(this, "Good Answer ", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Bad Answer ", Toast.LENGTH_SHORT).show();
        }

    }
}
