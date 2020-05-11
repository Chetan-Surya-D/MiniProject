package com.example.sithealthcare2;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ListView;

import androidx.core.app.NotificationManagerCompat;

public class UpdateBroadcastReceiver extends BroadcastReceiver {

    DatabaseHelper databaseHelper;

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        String time = intent.getStringExtra("time");
        int id = intent.getIntExtra("id", 100);

        assert time != null;
        Log.i("time", time);
        Log.i("id", String.valueOf(id));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(id);

        databaseHelper = new DatabaseHelper(context);
        databaseHelper.updateData(time);
        context.sendBroadcast(new Intent("open"));
    }
}
