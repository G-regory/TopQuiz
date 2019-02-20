package com.carriel.gregory.topquiz.controler;

public class Control {
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
        return instance;
    }
}
