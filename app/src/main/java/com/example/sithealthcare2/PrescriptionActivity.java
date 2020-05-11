package com.example.sithealthcare2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PrescriptionActivity extends AppCompatActivity {

    private
    EditText nameText, numberText, unitText;
    CheckBox morningCheckBox, afternoonCheckBox, nightCheckBox;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        nameText = findViewById(R.id.name);
        numberText = findViewById(R.id.number);
        unitText = findViewById(R.id.unit);
        morningCheckBox = findViewById(R.id.morning);
        afternoonCheckBox = findViewById(R.id.afternoon);
        nightCheckBox = findViewById(R.id.night);

        databaseHelper = new DatabaseHelper(this);

//        databaseHelper.deleteAll();
        reset();

        Button add = findViewById(R.id.add);
        Button viewList = findViewById(R.id.viewList);

        viewList.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
        });

        add.setOnClickListener(v -> {
            if (!valid()) {
                toastMessage("Invalid data");
            } else {
                addData();
//                toastMessage("Added");
            }
        });
    }





    public void addData() {
        int m, a, n;
        if(morningCheckBox.isChecked()) m = 1;
        else m = 0;
        if(afternoonCheckBox.isChecked()) a = 1;
        else a = 0;
        if(nightCheckBox.isChecked()) n = 1;
        else n = 0;
        boolean status = databaseHelper.addDAta(
                nameText.getText().toString(),
                Float.parseFloat(numberText.getText().toString()),
                Float.parseFloat(unitText.getText().toString()),
                m, a, n
        );
        if (status) {
            toastMessage("Added successfully");
            reset();
        } else toastMessage("Tablet already exist");
    }

    private boolean valid() {
        String number = numberText.getText().toString();
        String unit = unitText.getText().toString();
        if (nameText.getText().toString().matches("")) return false;
        if (number.matches("")) return false;
        if (unit.matches("")) return false;
        if(Float.parseFloat(number)<Float.parseFloat(unit)) return false;
        return morningCheckBox.isChecked() || afternoonCheckBox.isChecked() || nightCheckBox.isChecked();
    }

    private void reset() {
        nameText.setText(null);
        numberText.setText(null);
        unitText.setText(null);
        if (morningCheckBox.isChecked()) morningCheckBox.toggle();
        if (afternoonCheckBox.isChecked()) afternoonCheckBox.toggle();
        if (nightCheckBox.isChecked()) nightCheckBox.toggle();
    }

    public void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
