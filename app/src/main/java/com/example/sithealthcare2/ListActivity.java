package com.example.sithealthcare2;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> number = new ArrayList<String>();
    ArrayList<String> details = new ArrayList<String>();

    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        registerReceiver(broadcastReceiver, new IntentFilter("open"));

        databaseHelper = new DatabaseHelper(this);

        updateList();
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateList();
        }
    };


    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return name.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint({"InflateParams", "ViewHolder"})
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.custom_layout, null);

            TextView nameList = convertView.findViewById(R.id.nameList);
            TextView numberList = convertView.findViewById(R.id.numberList);
            TextView descriptionList = convertView.findViewById(R.id.descriptionList);

            Button remove = convertView.findViewById(R.id.remove);

            nameList.setText(name.get(position));
            numberList.setText(number.get(position));
            descriptionList.setText(details.get(position));

            remove.setOnClickListener(v -> {
                databaseHelper.delete(name.get(position));
                updateList();
            });

            Log.i("position", Integer.toString(position));
            return convertView;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    public void updateList() {
        Cursor c = databaseHelper.getAllData();
        TextView message = findViewById(R.id.message);
        name.clear();
        number.clear();
        details.clear();

        if(c.getCount() == 0) {
            message.setText("No items in list");
        } else {
            message.setText(null);
            while (c.moveToNext()) {
                String detail = "To be consumed at ";
                int f = 1;
                if (c.getInt(c.getColumnIndex("m"))==1) {
                    detail += "morning";
                    f = 0;
                }
                if (c.getInt(c.getColumnIndex("a"))==1) {
                    if (f == 1) detail += "afternoon";
                    else detail += ", afternoon";
                    f = 0;
                }
                if (c.getInt(c.getColumnIndex("n"))==1) {
                    if (f == 1) detail += "night";
                    else detail += ", night";
                    f = 0;
                }
                detail += ".";
                name.add(c.getString(c.getColumnIndex("name")));
                number.add(c.getString(c.getColumnIndex("number")) + " of tablets still left");
                details.add(detail);
            }
        }

//        Cursor c = databaseHelper.getData();
//        Log.i("name: ", Integer.toString(c.getColumnIndex("name")));
//        Log.i("number: ", Integer.toString(c.getColumnIndex("number")));
//        Log.i("unit: ", Integer.toString(c.getColumnIndex("unit")));
//        Log.i("m: ", Integer.toString(c.getColumnIndex("m")));
//        Log.i("a: ", Integer.toString(c.getColumnIndex("a")));
//        Log.i("n: ", Integer.toString(c.getColumnIndex("n")));

        ListView listView = findViewById(R.id.listLayout);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
    }

    public void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
