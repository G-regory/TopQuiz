package com.carriel.gregory.topquiz.controler;

import com.carriel.gregory.topquiz.model.QuestionBanks;
import com.carriel.gregory.topquiz.model.Question;
import com.carriel.gregory.topquiz.model.User;

import java.util.Arrays;

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

    /**
     * generate a question
     */
    public QuestionBanks generateQestions(){
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

        Question question9 = new Question("What is the house number of The Simpsons?",
                Arrays.asList("42", "101", "666", "742"),
                3);

        Question question10 = new Question("WHat's the name of your Prophet",
                Arrays.asList("Issa", "Moussa", "Mohammed", "a fourmi"),
                2 );


        return new QuestionBanks(Arrays.asList(question1,
                question2,
                question3,
                question4,
                question5,
                question6,
                question7,
                question8,
                question9,
                question10));
    }
}
