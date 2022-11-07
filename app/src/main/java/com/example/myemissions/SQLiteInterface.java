package com.example.myemissions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.security.keystore.KeyGenParameterSpec;
import android.util.Xml;


import androidx.annotation.Nullable;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

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

        //hash password
        //byte[] salt = genSalt();
        String encodedPassword = hashEncode(password);

        insertValues.put("password", encodedPassword);
        //insertValues.put("salt", salt);
        long result = database.insert("users", null, insertValues);

        //Will return false on insertion error
        if(result == -1)
            return false;
        else
            return true;
    }

    //To be called when registering a user
    public boolean changePassword(String username, String password) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues insertValues = new ContentValues();

        //hash password
        //byte[] salt = genSalt();
        String encodedPassword = hashEncode(password);

        insertValues.put("password", encodedPassword);
        //insertValues.put("salt", salt);

        String[] selectionArg = {String.valueOf(username)};
        long result = database.update("users", insertValues, "username = ?", selectionArg);

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

        password = hashEncode(password);

        Cursor search = database.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(search.getCount() > 0)
            return true;
        else
            return false;
    }

    //hash function
    /*
        sources: https://developer.android.com/guide/topics/security/cryptography#java
        and https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
    */
    private String hashEncode(String s) {
        String res = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(s.getBytes());
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }

            res = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    //generate salt
//    private static byte[] genSalt() {
//        byte[] salt = null;
//        try {
//            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
//            salt = new byte[16];
//            sr.nextBytes(salt);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return salt;
//    }


}
