package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class SelectCityActivity extends AppCompatActivity {
    List<String> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        final OpenDBHelper database = new OpenDBHelper(this);
        database.addCity("tehran");
        arr = database.getExistingCities();

        final AutoCompleteTextView edtName = findViewById(R.id.autoCompleteTextView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.select_dialog_item, arr);

        edtName.setThreshold(1);
        edtName.setAdapter(adapter);
        edtName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edtName.showDropDown();
                return false;
            }
        });
        Button btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.addCity(edtName.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("city",edtName.getText().toString());
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });


    }
}
