package com.cicioflaviu.wikicar.wikicar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "car_db";
    public static final String DATABASE_TABLE_NAME = "car_tbl";
    public static final String DATABASE_COL_1 = "_id";
    public static final String DATABASE_COL_2 = "make";
    public static final String DATABASE_COL_3 = "model";
    public static final String DATABASE_COL_4 = "description";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DATABASE_TABLE_NAME +
                "( " +
                DATABASE_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DATABASE_COL_2 + " TEXT, " +
                DATABASE_COL_3 + " TEXT, " +
                DATABASE_COL_4 + " TEXT " +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertCar(String make, String model, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATABASE_COL_2, make);
        contentValues.put(DATABASE_COL_3, model);
        contentValues.put(DATABASE_COL_4, description);
        return db.insert(DATABASE_TABLE_NAME, null, contentValues) != -1;
    }

    public Cursor getAllCars() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT rowid _id,* FROM " + DATABASE_TABLE_NAME, null);
    }
}
