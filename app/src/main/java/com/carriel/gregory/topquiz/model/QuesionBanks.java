package com.carriel.gregory.topquiz.model;

import java.util.Collections;
import java.util.List;

public class QuesionBanks {

    List<Question>mQuestionList;
    int mNextQuestionIndex;

    public QuesionBanks(List<Question> questionList) {
        mQuestionList = questionList;
        mNextQuestionIndex = 0;


        Collections.shuffle(mQuestionList);
    }

    public Question getQuestion(){
        //ensure we loop over the questions
        if(mNextQuestionIndex == mQuestionList.size()){
            mNextQuestionIndex =0;
        }

        return mQuestionList.get(mNextQuestionIndex++);
    }

}
