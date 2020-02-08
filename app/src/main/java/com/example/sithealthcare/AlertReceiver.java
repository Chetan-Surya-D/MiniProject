package com.example.sithealthcare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Notification", "Message");
        Toast.makeText(context, "Time notification", Toast.LENGTH_LONG).show();
    }


}
