package com.carriel.gregory.topquiz.model;

import java.util.List;

public class Question {

    String mQuestion;
    List<String> mChoiselist;
    int mAnswerIndex;


    public Question(String question, List<String> choiselist, int answerIndex) {
        setQuestion(question);
        setChoiselist(choiselist);
        setAnswerIndex(answerIndex);
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public List<String> getChoiselist() {
        return mChoiselist;
    }

    public void setChoiselist(List<String> choiselist) {
        mChoiselist = choiselist;
    }

    public int getAnswerIndex() {
        return mAnswerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        mAnswerIndex = answerIndex;
    }
}
