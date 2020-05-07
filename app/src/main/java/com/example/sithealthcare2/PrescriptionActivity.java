package com.example.sithealthcare2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class PrescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        Button add = findViewById(R.id.add);
        add.setOnClickListener(v -> {

            EditText nameText = findViewById(R.id.name);
            String name = nameText.getText().toString();
            EditText numberText = findViewById(R.id.number);
//            float number = numberText.getText().pars

            SQLiteDatabase database = this.openOrCreateDatabase("Medicines", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS medicines (name VARCHAR, number REAL, unit REAL,morning INT, afternoon INT, night INT)");
//            database.execSQL("INSERT INTO medicines values ("+string+")");
            Cursor cursor = database.rawQuery("SELECT * FROM medicines", null);

//            int name = cursor.getColumnIndex("name");
//            int number = cursor.getColumnIndex("number");
//            int unit = cursor.getColumnIndex("unit");
//            int m = cursor.getColumnIndex("morning");
//            int a = cursor.getColumnIndex("afternoon");
//            int n = cursor.getColumnIndex("night");

//            cursor.moveToFirst();
//            while (cursor != null) {
//
//                Log.i("name: ", cursor.getString(name));
//                Log.i("number: ", cursor.getString(number));
//                Log.i("name: ", cursor.getString(m));
//
//                cursor.moveToNext();
        });
    }
}
