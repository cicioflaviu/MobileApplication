package com.cicioflaviu.wikicar.wikicar;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CarCursorAdapter extends CursorAdapter {
    public CarCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_car, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView carId = (TextView) view.findViewById(R.id.carId);
        TextView carMake = (TextView) view.findViewById(R.id.carMake);
        TextView carModel = (TextView) view.findViewById(R.id.carModel);
        // Extract properties from cursor
        String idStr = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.DATABASE_COL_1));
        String makeStr = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.DATABASE_COL_2));
        String modelStr = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.DATABASE_COL_3));
        // Populate fields with extracted properties
        carId.setText(idStr);
        carMake.setText(makeStr);
        carModel.setText(modelStr);
    }
}
