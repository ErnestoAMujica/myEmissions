package com.example.myemissions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//TODO:   Implement keystore authentication either here or in the login functions
//        to store hashed passwords instead of plaintext ones

public class SQLiteInterface extends SQLiteOpenHelper {

    public static final String DATABASENAME = "Users.db";

    public SQLiteInterface(Context context) {
        super(context, DATABASENAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("create Table users(username TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL("drop Table if exists users");
    }

    //To be called when registering a user
    public boolean insertData(String username, String password) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues insertValues = new ContentValues();
        insertValues.put("username", username);
        insertValues.put("password", password);
        long result = database.insert("users", null, insertValues);

        //Will return false on insertion error
        if(result == -1)
            return false;
        else
            return true;
    }

    //Checks if a username already exists
    public Boolean checkUsername(String username) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor search = database.rawQuery("Select * from users where username = ?", new String[] {username});

        if(search.getCount() > 0)
            return true;
        else
            return false;
    }

    //Checks for a matching username and password combination
    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor search = database.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(search.getCount() > 0)
            return true;
        else
            return false;
    }
}
