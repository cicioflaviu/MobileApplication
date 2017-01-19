//package com.cicioflaviu.wikicar.wikicar.sync;
//
//import android.content.ContentProvider;
//import android.content.ContentValues;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.net.Uri;
//import android.support.annotation.Nullable;
//
//import com.cicioflaviu.wikicar.wikicar.DatabaseHelper;
//
//public class CarContentProvider extends ContentProvider{
//    public static final String DATABASE_NAME = "car_db";
//
//    /*
//     * Defines a handle to the database helper object. The MainDatabaseHelper class is defined
//     * in a following snippet.
//     */
//    private DatabaseHelper databaseHelper;
//
//    // Holds the database object
//    private SQLiteDatabase db;
//
//    @Override
//    public boolean onCreate() {
//        databaseHelper = new DatabaseHelper(getContext(), DATABASE_NAME, null, 1);
//        return true;
//    }
//
//    @Nullable
//    @Override
//    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public String getType(Uri uri) {
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public Uri insert(Uri uri, ContentValues values) {
//        Uri mNewUri;
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DATABASE_COL_2, make);
//        contentValues.put(DATABASE_COL_3, model);
//        contentValues.put(DATABASE_COL_4, description);
//
//        db = databaseHelper.getWritableDatabase();
//
//
//        return null;
//    }
//
//    @Override
//    public int delete(Uri uri, String selection, String[] selectionArgs) {
//        return 0;
//    }
//
//    @Override
//    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
//        return 0;
//    }
//}
