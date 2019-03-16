package com.carriel.gregory.topquiz.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        listView= findViewById(R.id.activity_ranking_list_view);
        mySqlite=new MySqlite(this);
        mUser=new User();
        values= new ArrayList<>();

        List<User> users=new ArrayList<>(mySqlite.restore());

        if(users.size() >0){
            for(int i=0; i<users.size();i++) {
                values.add(users.get(i).getFirstname() + " :" + users.get(i).getScoreUser());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, values);
            listView.setAdapter(adapter);
        }

        for(int i=0;i<users.size();i++){


            for(int j=0;j<users.size();j++){

                try {
                    Log.d(TAG, "onCreate: compare position: "+i+" avec "+j+" :"+users.get(i).getFirstname().compareTo(users.get(users.size()-j).getFirstname()));
                }catch (RuntimeException e){

                    Log.d(TAG, "onCreate: prob");
                }
            }



        }


    }


}
