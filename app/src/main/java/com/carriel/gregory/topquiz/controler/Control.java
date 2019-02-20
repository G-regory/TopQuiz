package com.carriel.gregory.topquiz.controler;

import com.carriel.gregory.topquiz.model.User;

public class Control {

    private static User mUser;

    private static Control instance;


    /**
     * constructor private
     */
    private Control(){}

    /**
     * create instance or return current instance
     * @return instance
     */
    public static Control getInstance() {
        if(instance ==null){
            instance =new Control();
        }
        mUser=new User();
        return instance;
    }

    /**
     * setup name of user
     */
    public void setName(String pName){
        mUser.setName(pName);
    }

    /**
     *
     */
    public void getName(){
        mUser.getName();
    }
}
