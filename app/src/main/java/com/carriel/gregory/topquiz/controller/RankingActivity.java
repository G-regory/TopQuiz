package com.carriel.gregory.topquiz.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.carriel.gregory.topquiz.R;
import com.carriel.gregory.topquiz.controller.sql.MySqlite;
import com.carriel.gregory.topquiz.model.User;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends AppCompatActivity {
    String TAG="rankingmsg";

    private ListView listView ;
    private List<String> values;
    private User mUser;
    private MySqlite mySqlite;
    private Button mButtonOrderScore, mButtonOrderName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        listView= findViewById(R.id.activity_ranking_list_view);
        mButtonOrderScore= findViewById(R.id.activity_ranking_order_number_btn);
        mButtonOrderName= findViewById(R.id.activity_ranking_order_alphab_btn);


        mySqlite=new MySqlite(this);
        mUser=new User();
        values= new ArrayList<>();

        orderByNumber();


        mButtonOrderScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderByNumber();
            }
        });

        mButtonOrderName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderByAlphabetically();
            }
        });


    }

    private void orderByNumber() {
        List<User> users=new ArrayList<>(mySqlite.restoreByNumber());

        values.clear();
        if(users.size() >0){
            for(int i=0; i<users.size();i++) {
                values.add(users.get(i).getFirstname() + " :" + users.get(i).getScoreUser());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, values);
            listView.setAdapter(adapter);
        }
    }

    private void orderByAlphabetically() {

        List<User> users=new ArrayList<>(mySqlite.restoreByAlphabet());
        values.clear();

        if(users.size() >0){
            for(int i=0; i<users.size();i++) {
                values.add(users.get(i).getFirstname() + " :" + users.get(i).getScoreUser());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, values);
            listView.setAdapter(adapter);
        }

    }

}



