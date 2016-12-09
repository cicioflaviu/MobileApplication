package com.cicioflaviu.wikicar.wikicar;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.List;

public class DataContentProvider extends ContentProvider {
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String AUTHORITY = "com.cicioflaviu.wikicar.provider";
    private static final int CAR_LIST_ID = 1;
    static {
        uriMatcher.addURI(AUTHORITY, "cars#", CAR_LIST_ID);
    }

    private DbHelper dbHelper;


    @Override
    public boolean onCreate() {
        db = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        List segments = uri.getPathSegments();
        switch (uriMatcher.match(uri)){
            case CAR_LIST_ID:
                return db.getAllCars();
            default:
                throw new RuntimeException("No content provider URI match.");
        }

    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        //TODO
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
