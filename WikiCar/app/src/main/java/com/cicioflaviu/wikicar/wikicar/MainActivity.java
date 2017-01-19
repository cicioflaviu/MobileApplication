package com.cicioflaviu.wikicar.wikicar;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cicioflaviu.wikicar.wikicar.data.CarContract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddCarDialogListener {
    Button addBtn;
    List<String> carArray = new ArrayList<>();
    ListView carList;
    ArrayAdapter<String> arrayAdapter;
    long pos = -1;

    CarCursorAdapter carCursorAdapter;
    ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DATABASE
        contentResolver = getContentResolver();
        carCursorAdapter = new CarCursorAdapter(this, readCars());
        Log.d("Cars count: ", String.valueOf(carCursorAdapter.getCount()));

//        clearCars();
//        initialiseCars();


        String[] array = {"Car1", "Car2", "Car3"};
        carArray.addAll((Arrays.asList(array)));

        addBtn = (Button) findViewById(R.id.addBtn);
        carList = (ListView) findViewById(R.id.carList);
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, carArray);
        carList.setAdapter(carCursorAdapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new AddCarDialog();
                newFragment.show(getSupportFragmentManager(), "missiles");
            }
        });

        final SwipeDetector swipeDetector = new SwipeDetector();

        carList.setOnTouchListener(swipeDetector);
        carList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (swipeDetector.swipeDetected()) {
                    deleteCar(id);
                    Toast.makeText(MainActivity.this, "Car with id " + id + " was deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Intent modifyActivity = new Intent(MainActivity.this, ModifyCarActivity.class);
                    modifyActivity.putExtra("carId", id);
                    modifyActivity.putExtra("carMake", ((Cursor) parent.getItemAtPosition(position)).getString(2));
                    modifyActivity.putExtra("carModel", ((Cursor) parent.getItemAtPosition(position)).getString(3));
                    modifyActivity.putExtra("carDescription", ((Cursor) parent.getItemAtPosition(position)).getString(4));
//                    System.out.println("Selected ITEM: " + parent.getItemAtPosition(position));
                    startActivityForResult(modifyActivity, 0);
                    pos = position;
                }
            }
        });

        carList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Checkout This Awesome Car!");
                intent.putExtra(Intent.EXTRA_TEXT, "Checkout the info for this awesome car: " + parent.getItemAtPosition(position));
                intent.setType("message/rfc822");

                Intent chooser = Intent.createChooser(intent, "Share car info");
                startActivity(chooser);

                return true;
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Long id = data.getLongExtra("carId", -1);
            String make = data.getStringExtra("carMake");
            String model = data.getStringExtra("carModel");
            String description = data.getStringExtra("carDescription");
            updateCar(id, make, model, description);
        }
    }

    @Override
    public void addButtonClicked(String make, String model, String description) {
        ContentValues values = new ContentValues();
        values.put(CarContract.DATABASE_COL_2, make);
        values.put(CarContract.DATABASE_COL_3, model);
        values.put(CarContract.DATABASE_COL_4, description);
        Uri uri = getContentResolver().insert(CarContract.CARS_CONTENT_URI, values);

        carCursorAdapter.swapCursor(readCars());
        carCursorAdapter.notifyDataSetChanged();
    }

    private void initialiseCars() {
        ContentValues values = new ContentValues();
        values.put(CarContract.DATABASE_COL_2, "Mercedes-Benz");
        values.put(CarContract.DATABASE_COL_3, "S65 AMG");
        values.put(CarContract.DATABASE_COL_4, "DRIVING PERFORMANCE IN ITS PERFECT FORM.");
        getContentResolver().insert(CarContract.CARS_CONTENT_URI, values);

        values.put(CarContract.DATABASE_COL_2, "Nissan");
        values.put(CarContract.DATABASE_COL_3, "GT-R");
        values.put(CarContract.DATABASE_COL_4, "MORE THAN JUST MUSCLE");
        getContentResolver().insert(CarContract.CARS_CONTENT_URI, values);

        values.put(CarContract.DATABASE_COL_2, "Tesla");
        values.put(CarContract.DATABASE_COL_3, "Model S");
        values.put(CarContract.DATABASE_COL_4, "Performance and safety refined.");
        getContentResolver().insert(CarContract.CARS_CONTENT_URI, values);

        carCursorAdapter.swapCursor(readCars());
        carCursorAdapter.notifyDataSetChanged();
    }

    private void deleteCar(long id) {
        String[] args = {String.valueOf(id)};
        int numRows = getContentResolver().delete(CarContract.CARS_CONTENT_URI, CarContract.DATABASE_COL_1 + " =?", args);
        Log.d("Delete Rows: ", String.valueOf(numRows));

        carCursorAdapter.swapCursor(readCars());
        carCursorAdapter.notifyDataSetChanged();
    }

    private Cursor readCars() {
        String[] projection = {
                "rowid _id",
                CarContract.DATABASE_COL_1,
                CarContract.DATABASE_COL_2,
                CarContract.DATABASE_COL_3,
                CarContract.DATABASE_COL_4
        };

        return getContentResolver().query(CarContract.CARS_CONTENT_URI, projection, null, null, null);
    }

    private void updateCar(Long id, String make, String model, String description) {
        String[] args = {String.valueOf(id)};
        ContentValues values = new ContentValues();
        values.put(CarContract.DATABASE_COL_1, id);
        values.put(CarContract.DATABASE_COL_2, make);
        values.put(CarContract.DATABASE_COL_3, model);
        values.put(CarContract.DATABASE_COL_4, description);
        getContentResolver().insert(CarContract.CARS_CONTENT_URI, values);
        int numRows = getContentResolver().update(CarContract.CARS_CONTENT_URI, values,
                CarContract.DATABASE_COL_1 + "=?", args);
        carCursorAdapter.swapCursor(readCars());
        carCursorAdapter.notifyDataSetChanged();
        Log.d("Update Rows ", String.valueOf(numRows));
    }

    private void clearCars() {
        getContentResolver().delete(CarContract.CARS_CONTENT_URI, null, null);
    }

    private void authenticate(){
        Intent modifyActivity = new Intent(MainActivity.this, AuthActivity.class);
        startActivityForResult(modifyActivity, 0);
    }

}
