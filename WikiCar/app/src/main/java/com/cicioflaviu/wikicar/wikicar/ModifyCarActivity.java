package com.cicioflaviu.wikicar.wikicar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModifyCarActivity extends AppCompatActivity {
    Button saveBtn;
    EditText carMakeEdt;
    EditText carModelEdt;
    EditText carDescriptionEdt;
    Long carId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_car);

        saveBtn = (Button)findViewById(R.id.save_button);
        carMakeEdt = (EditText)findViewById(R.id.carMakeEdt);
        carModelEdt = (EditText)findViewById(R.id.carModelEdt);
        carDescriptionEdt = (EditText)findViewById(R.id.carDescriptionEdt);

        final String carMake = getIntent().getStringExtra("carMake");
        final String carModel = getIntent().getStringExtra("carModel");
        final String carDescription = getIntent().getStringExtra("carDescription");

        carMakeEdt.setText(carMake);
        carModelEdt.setText(carModel);
        carDescriptionEdt.setText(carDescription);

        Random r = new Random();
        LineChart chart = (LineChart) findViewById(R.id.chart);
        List<Entry> entries = new ArrayList<Entry>();
        for (int i = 0; i<5; i++){
            entries.add(new Entry(i, (i+2)/2));
        }
        LineDataSet dataSet = new LineDataSet(entries, "Random Data");
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("carId", getIntent().getLongExtra("carId", -1));
                returnIntent.putExtra("carMake", carMakeEdt.getText().toString());
                returnIntent.putExtra("carModel", carModelEdt.getText().toString());
                returnIntent.putExtra("carDescription", carDescriptionEdt.getText().toString());
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
