package com.example.sithealthcare2;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationBroadcastReceiver extends BroadcastReceiver {

    DatabaseHelper databaseHelper;

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = "";
        String text = "";
        String period = "";
        StringBuilder bigText = new StringBuilder();
        int time = intent.getIntExtra("time", 0);

        if (time == 1) {
            title = "Time for Morning Medicine";
            text = "Please consume your morning medicines";
            period = "m";
        } else if (time == 2) {
            title = "Time for Afternoon Medicine";
            text = "Please consume your afternoon medicines";
            period = "a";
        } else if (time == 3) {
            title = "Time for Night Medicine";
            text = "Please consume your night medicines";
            period = "n";
        }

//        time *= 100;
        databaseHelper = new DatabaseHelper(context);

        Cursor c = databaseHelper.getData(period);
        if (c.getCount() != 0) {

            bigText = new StringBuilder(text);
            while (c.moveToNext()) {
                bigText.append("\n").append(c.getString(c.getColumnIndex("name")));
            }

            Log.i("id", String.valueOf(time));
            Intent intent1 = new Intent(context, UpdateBroadcastReceiver.class);
            intent1.putExtra("time", period);
            intent1.putExtra("id", time);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, time, intent1, 0);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel1")
                    .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(bigText.toString()))
                    .addAction(R.drawable.ic_alarm_black_24dp, "CONSUMED", pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(time, builder.build());

//            Intent i = new Intent();
//            i.setClassName("com.example.myapplication", "com.example.myapplication .Main2Activity");
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);
        }
    }
}
