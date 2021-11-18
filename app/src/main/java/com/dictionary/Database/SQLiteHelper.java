package com.dictionary.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.dictionary.Model.Word;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    Context context;
    private static String DB_NAME ="word.db";
    SQLiteDatabase myDB;
    public SQLiteHelper(@Nullable Context context){
        super(context,DB_NAME,null,1);
        this.context = context;
    }
    public void openDB() throws SQLException {
        if(myDB == null)
            myDB = getWritableDatabase();
    }
    public void closeDB(){
        if(myDB != null)
            myDB.close();
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public void createTable(){
        //myDB.execSQL("DROP TABLE IF EXISTS Account");
        String query = "create table if not exists Word (engkey text PRIMARY KEY, engmean text," +
                "viekey text, viemean text, topic text)";
        myDB.execSQL(query);
    }
    public void insert(Word w){
        ContentValues contentValues = new ContentValues();
        contentValues.put("engkey",w.getEngKey().toString());
        contentValues.put("engmean",w.getEngMean().toString());
        contentValues.put("viekey",w.getVieKey().toString());
        contentValues.put("viemean",w.getVieMean().toString());
        contentValues.put("topic",w.getTopic().toString());

        myDB.insert("Word",null,contentValues);
    }
    public void update(Word w){
        ContentValues contentValues = new ContentValues();
        contentValues.put("engkey",w.getEngKey().toString());
        contentValues.put("engmean",w.getEngMean().toString());
        contentValues.put("viekey",w.getVieKey().toString());
        contentValues.put("viemean",w.getVieMean().toString());
        contentValues.put("topic",w.getTopic().toString());
        myDB.update("Word",contentValues,("engkey = ?"),new
                String[]{String.valueOf(w.getEngKey())});
    }
    public ArrayList<Word> getAll(){
        ArrayList<Word> L = new ArrayList<>();
        String query = "select * from Word";
        Cursor cursor = myDB.rawQuery(query,null);
        while (cursor.moveToNext()){
            @SuppressLint("Range") String engkey = cursor.getString(cursor.getColumnIndex("engkey"));
            @SuppressLint("Range") String engmean = cursor.getString(cursor.getColumnIndex("engmean"));
            @SuppressLint("Range") String viekey = cursor.getString(cursor.getColumnIndex("viekey"));
            @SuppressLint("Range") String viemean = cursor.getString(cursor.getColumnIndex("viemean"));
            @SuppressLint("Range") String topic = cursor.getString(cursor.getColumnIndex("topic"));

            Word w = new Word(engkey,engmean,viekey,viemean,topic);
            L.add(w);
        }
        return L;
    }

    public boolean IsExist(Word w) {
        String query = "select * from word where engkey ='"+w.getEngKey().toString()+"'";
        Cursor cursor = myDB.rawQuery(query,null);
        return (cursor.getCount() > 0) ? true : false;
    }
}
