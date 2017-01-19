package com.cicioflaviu.wikicar.wikicar.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cicioflaviu.wikicar.wikicar.DatabaseHelper;

public class CarProvider extends ContentProvider {
    //Operations for uri matcher
    private static final int CARS = 1;
    private static final int CARS_ID = 2;

    //UriMatcher
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(CarContract.CONTENT_AUTHORITY, CarContract.PATH_CARS, CARS);
        uriMatcher.addURI(CarContract.CONTENT_AUTHORITY, CarContract.PATH_CARS + "/#", CARS_ID);
    }

    private DatabaseHelper databaseHelper;

    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        int match = uriMatcher.match(uri);
        switch (match) {
            case CARS:
                cursor = db.rawQuery("SELECT rowid _id,* FROM " + CarContract.DATABASE_TABLE_NAME, null);
                break;
            case CARS_ID:
                selection = CarContract.DATABASE_COL_1 + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                db.query(CarContract.DATABASE_TABLE_NAME, projection, selection, selectionArgs, null, null, orderBy);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int match = uriMatcher.match(uri);
        switch (match) {
            case CARS:
                return insertCar(uri, values);
            default:
                throw new IllegalArgumentException("Unknown URI");
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match = uriMatcher.match(uri);
        switch (match) {
            case CARS:
                return deleteCar(uri, selection, selectionArgs);
            case CARS_ID:
                selection = CarContract.DATABASE_COL_1 + "=?";
                selectionArgs = new String[] {
                        String.valueOf(ContentUris.parseId(uri))
                };
                return deleteCar(uri, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Unknown URI");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int match = uriMatcher.match(uri);
        switch (match) {
            case CARS:
                return updateCar(uri, values, selection, selectionArgs);
            case CARS_ID:
                selection = CarContract.DATABASE_COL_1 + "=?";
                selectionArgs = new String[] {
                        String.valueOf(ContentUris.parseId(uri))
                };
                return updateCar(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Unknown URI");
        }
    }

    private Uri insertCar(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long id = db.insert(CarContract.DATABASE_TABLE_NAME, null, contentValues);
        if (id == -1) {
            Log.d("CarProvider", "Error insert " + uri);
            return null;
        } else return ContentUris.withAppendedId(uri, id);
    }

    private int updateCar(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long id = db.update(CarContract.DATABASE_TABLE_NAME, contentValues, selection, selectionArgs);
        if (id == 0) {
            Log.d("CarProvider", "Error update " + uri);
            return -1;
        } else return Integer.parseInt(String.valueOf(id));
    }

    private int deleteCar(Uri uri, String selection, String[] selectionArgs){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long id = db.delete(CarContract.DATABASE_TABLE_NAME, selection, selectionArgs);
        if (id == 0) {
            Log.d("CarProvider", "Error delete " + uri);
            return -1;
        } else return Integer.parseInt(String.valueOf(id));
    }

    private void clearCars(){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(CarContract.DATABASE_TABLE_NAME, null, null);
    }
}
