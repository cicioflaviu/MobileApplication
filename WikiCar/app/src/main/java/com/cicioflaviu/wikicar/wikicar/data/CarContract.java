package com.cicioflaviu.wikicar.wikicar.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class CarContract implements BaseColumns{
    public static final String CONTENT_AUTHORITY = "com.wikicar.cars.carsprovider";
    public static final String PATH_CARS = "cars";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final Uri CARS_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CARS);

    public static final String DATABASE_TABLE_NAME = "car_tbl";
    public static final String DATABASE_COL_1 = "_id";
    public static final String DATABASE_COL_2 = "make";
    public static final String DATABASE_COL_3 = "model";
    public static final String DATABASE_COL_4 = "description";
    public static final String ON_CREATE = "create table " + DATABASE_TABLE_NAME +
            "( " +
            DATABASE_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DATABASE_COL_2 + " TEXT, " +
            DATABASE_COL_3 + " TEXT, " +
            DATABASE_COL_4 + " TEXT " +
            ")";
}
