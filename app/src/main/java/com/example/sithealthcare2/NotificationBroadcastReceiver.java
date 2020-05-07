package com.example.sithealthcare2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = "", text = "";
        int time = intent.getIntExtra("time", 0);

        if(time==1) {
            title = "Time for Morning Medicine";
            text = "Please consume your morning medicines";
        } else if(time == 2) {
            title = "Time for Afternoon Medicine";
            text = "Please consume your afternoon medicines";
        } else if(time == 3) {
            title = "Time for Night Medicine";
            text = "Please consume your night medicines";
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel1")
                .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText(text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(time, builder.build());
//
//        Intent i = new Intent();
//        i.setClassName("com.example.myapplication", "com.example.myapplication .Main2Activity");
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);
    }
}
