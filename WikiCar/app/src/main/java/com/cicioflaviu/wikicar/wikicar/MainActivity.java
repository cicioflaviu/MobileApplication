package com.cicioflaviu.wikicar.wikicar;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddCarDialogListener{
    Button addBtn;
    List<String> carArray = new ArrayList<>();
    ListView carList;
    ArrayAdapter<String> arrayAdapter;
    long pos = -1;

    DatabaseHelper carDatabase;
    CarCursorAdapter carCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DATABASE
        carDatabase = new DatabaseHelper(this);
        carCursorAdapter = new CarCursorAdapter(this, carDatabase.getAllCars());

        //TEST DATA
//        carDatabase.clear();
//        carDatabase.insertCar("Mercedes-Benz", "S65 AMG", "DRIVING PERFORMANCE IN ITS PERFECT FORM.");
//        carDatabase.insertCar("Nissan", "GT-R", "MORE THAN JUST MUSCLE");
//        carDatabase.insertCar("Tesla", "Model S", "Performance and safety refined.");


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
                    carDatabase.deleteCar(id);
                    carCursorAdapter.swapCursor(carDatabase.getAllCars());
                    carCursorAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Car with id " + id + " was deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Intent modifyActivity = new Intent(MainActivity.this, ModifyCarActivity.class);
                    modifyActivity.putExtra("carId", id);
                    modifyActivity.putExtra("carMake", ((Cursor)parent.getItemAtPosition(position)).getString(2));
                    modifyActivity.putExtra("carModel", ((Cursor)parent.getItemAtPosition(position)).getString(3));
                    modifyActivity.putExtra("carDescription", ((Cursor)parent.getItemAtPosition(position)).getString(4));
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
            carDatabase.updateCar(id, make, model, description);
            carCursorAdapter.swapCursor(carDatabase.getAllCars());
            carCursorAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addButtonClicked(String make, String model, String description) {
        carDatabase.insertCar(make, model, description);
        carCursorAdapter.swapCursor(carDatabase.getAllCars());
        carCursorAdapter.notifyDataSetChanged();
    }
}
