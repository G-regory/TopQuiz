package com.carriel.gregory.topquiz.model;

import java.util.Collections;
import java.util.List;

public class QuestionBanks {
    private List<Question> mQuestionList;
    private int mNextQuestionIndex;

    public QuestionBanks(List<Question> questionList) {
        mQuestionList = questionList;

        // Shuffle the question list
        Collections.shuffle(mQuestionList);

        mNextQuestionIndex = 0;
    }

    public Question getQuestion() {
        // Ensure we loop over the questions
        if (mNextQuestionIndex == mQuestionList.size()) {
            mNextQuestionIndex = 0;
        }

        // Please note the post-incrementation
        return mQuestionList.get(mNextQuestionIndex++);
    }

}
