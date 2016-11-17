package com.cicioflaviu.wikicar.wikicar;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    Button addBtn;
    EditText carNameEdt;
    List<String> carArray = new ArrayList<>();
    ListView carList;
    ArrayAdapter<String> arrayAdapter;
    long pos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] array = {"Car1", "Car2", "Car3"};
        carArray.addAll((Arrays.asList(array)));

        addBtn = (Button) findViewById(R.id.addBtn);
        carList = (ListView) findViewById(R.id.carList);
        carNameEdt = (EditText) findViewById(R.id.carNameEdt);
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, carArray);
        carList.setAdapter(arrayAdapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carArray.add(carNameEdt.getText().toString());
                arrayAdapter.notifyDataSetChanged();
            }
        });

        carList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent modifyActivity = new Intent(MainActivity.this, ModifyCarActivity.class);
                modifyActivity.putExtra("carName", (String) parent.getItemAtPosition(position));
                System.out.println("Selected ITEM: " + parent.getItemAtPosition(position));
                startActivityForResult(modifyActivity, 0);
                pos = position;
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
            String result = data.getStringExtra("carName");
            carArray.set(Integer.parseInt(Long.toString(pos)), result);
            arrayAdapter.notifyDataSetChanged();
        }
    }
}
