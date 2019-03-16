package com.carriel.gregory.topquiz.controller.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.carriel.gregory.topquiz.model.User;

import java.util.ArrayList;
import java.util.List;

public class MySqlite extends SQLiteOpenHelper {
    public static final String TAG="messagetopquiz";

    SQLiteDatabase db;

    public static final String DB_NAME = "history_top_qui.db";
    public static final String TABLE_NAME = "ranking";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_SCORE_USER = "score";
    public static final int DB_VERSION = 1;
    private final String CREATE_TABLE= "create table  "
            + TABLE_NAME + " (" + COLUMN_ID + " integer primary key autoincrement,"
            + COLUMN_FIRST_NAME + " text,"+ COLUMN_SCORE_USER + " Integer) ";


    public MySqlite(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String strSQL = "DROP TABLE " + TABLE_NAME;
        db.execSQL(strSQL);
    }

    public void recordData(User pUSER){
        db=getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FIRST_NAME, pUSER.getFirstname());
        contentValues.put(COLUMN_SCORE_USER, pUSER.getScoreUser());
        db.insert(TABLE_NAME,null,contentValues);
        Log.d(TAG, "recordData: ok"+pUSER.toString());
    }

    public List<User> restore(){
        List<User>users = new ArrayList<>();

        String stSQL="select DISTINCT "+COLUMN_FIRST_NAME+", "+COLUMN_SCORE_USER+" from "+TABLE_NAME+ " ORDER BY "+COLUMN_SCORE_USER+" DESC limit 5";

        db=getReadableDatabase();
        Cursor cursor = db.rawQuery(stSQL,null);
            for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
                User user=new User();
                user.setScoreUser(cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE_USER)));
                user.setFirstname(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
                Log.d(TAG, "restore: "+user.toString());
                users.add(user);
            }
        cursor.close();

        return users;
    }

    public void updateData(User pUser){

        String where=COLUMN_FIRST_NAME+"= '" + pUser.getFirstname()+"'";
        db=getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_FIRST_NAME, pUser.getFirstname());
        contentValues.put(COLUMN_SCORE_USER, pUser.getScoreUser());
        try{
            db.update(TABLE_NAME,contentValues, where,null );
            Log.d(TAG, "updateData: ok"+pUser.toString());

        }catch (SQLException e){
            Log.d(TAG, "updateData: failed");
        }
    }
}
