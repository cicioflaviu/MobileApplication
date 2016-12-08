package com.cicioflaviu.wikicar.wikicar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ModifyCarActivity extends AppCompatActivity {
    Button saveBtn;
    EditText carNameEdt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_car);

        saveBtn = (Button)findViewById(R.id.save_button);
        carNameEdt = (EditText)findViewById(R.id.carNameEdt);

        final String carName = getIntent().getStringExtra("carName");
        carNameEdt.setText(carName);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                String entry = carNameEdt.getText().toString();
                returnIntent.putExtra("carName", entry);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
