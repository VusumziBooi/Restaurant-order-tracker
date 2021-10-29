package com.example.rot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.TextureView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Customer.db";
    public static final String TABLE_NAME = "Customer_table";
    public static final String c0 = "ID";
    public static final String c1 = "StaffName";
    public static final String c2 = "OrderCreated";
    public static final String c3 = "OrderItems";
    public static final String c4 = "Username";


    public DataBaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, StaffName TEXT ,OrderCreated TEXT,OrderItems TEXT, Username TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);

    }

    public boolean insertData(String StaffName, String OrderCreated, String OrderItems, String Username){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(c1,StaffName);
        contentValues.put(c2,OrderCreated);
        contentValues.put(c3,OrderItems);
        contentValues.put(c4,Username);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public Cursor getAllData(String User){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_NAME + " WHERE Username = ? ", new String[]{User});
        return res;

    }
}
