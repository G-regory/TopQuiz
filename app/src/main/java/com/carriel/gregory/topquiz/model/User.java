package com.carriel.gregory.topquiz.model;

public class User {
    private String mFirstname;
    private int mScoreUser;


    public int getScoreUser() {
        return mScoreUser;
    }

    public void setScoreUser(int mScoreUser) {
        this.mScoreUser = mScoreUser;
    }


    public String getFirstname() {
        return mFirstname;
    }

    public void setFirstname(String firstname) {
        mFirstname = firstname;
    }

    @Override
    public String toString() {
        return "User{" +
                "mFirstname='" + mFirstname + '\'' +
                ", mScoreUser=" + mScoreUser +
                '}';
    }
}
