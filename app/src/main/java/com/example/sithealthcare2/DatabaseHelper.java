package com.example.sithealthcare2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "medicines";

    public DatabaseHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (name text primary key, number real, unit real, m integer, a integer, n integer)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addDAta(
            String name,
            float number,
            float unit,
            int m,
            int a,
            int n
    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("number", number);
        contentValues.put("unit", unit);
        contentValues.put("m", m);
        contentValues.put("a", a);
        contentValues.put("n", n);


        Log.d("DatabaseHelper", "adding data to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        return !(result == -1);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public Cursor getData(String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + time + "=1", null);
    }

    public boolean updateData(String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = 0;
        Cursor c = getData(time);
        while (c.moveToNext()) {
            Float number = c.getFloat(c.getColumnIndex("number"));
            Float unit = c.getFloat(c.getColumnIndex("unit"));

            number -= unit;

            if (number <= 0) delete(c.getString(c.getColumnIndex("name")));
            else {
                ContentValues contentValues = new ContentValues();
                contentValues.put("number", number);
                result = db.update(TABLE_NAME, contentValues, "name=?", new String[]{c.getString(c.getColumnIndex("name"))});
            }
        }
        return !(result == -1);
    }

    public void delete(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE name = '" + name + "'");

    }


    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
