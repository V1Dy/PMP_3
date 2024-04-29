package com.example.pmp_3.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.pmp_3.Model.TableModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static  final String DATABASE_NAME = "TODO_DATABASE";
    private static  final String TABLE_NAME = "TODO_TABLE";
    private static  final String COL_1 = "ID";
    private static  final String COL_2 = "TASK";
    private static  final String COL_3 = "STATUS";

    private static  final String COL_4 = "BEER_COUNT";


    public DataBaseHelper(@Nullable Context context ) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , TASK TEXT , STATUS INTEGER, BEER_COUNT INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
         onCreate(db);
    }

    public void insertTable(TableModel model){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2 , model.getTable());
        values.put(COL_3 , 0);
        values.put(COL_4 , 0);
        db.insert(TABLE_NAME , null , values);
    }

    public void updateTable(int id , String table){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2 , table);

        db.update(TABLE_NAME , values , "ID=?" , new String[]{String.valueOf(id)});
    }

    public void updateStatus(int id , int status){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_3 , status);
        db.update(TABLE_NAME , values , "ID=?" , new String[]{String.valueOf(id)});
    }

    public void deleteTable(int id){
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME , "ID=?" , new String[]{String.valueOf(id)});
    }

    public void updateBeerCount(int id, int beerCount) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_4, beerCount);
        db.update(TABLE_NAME, values, "ID=?" , new String[]{String.valueOf(id)});
    }

    public List<TableModel> getAllTables(){

        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<TableModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try {
            cursor = db.query(TABLE_NAME , null , null , null , null , null , null);
            if (cursor !=null){
                if (cursor.moveToFirst()){
                    do {
                        TableModel table = new TableModel();
                        table.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
                        table.setTable(cursor.getString(cursor.getColumnIndex(COL_2)));
                        table.setStatus(cursor.getInt(cursor.getColumnIndex(COL_3)));
                        table.setBeerCount(cursor.getInt(cursor.getColumnIndex(COL_4)));
                        modelList.add(table);
                    }while (cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cursor.close();
        }
        return modelList;
    }

}







