package com.example.sithealthcare2;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NotificationActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        createNotificationChannel();

        Button alarm1 = findViewById(R.id.morningButton);
        Button alarm2 = findViewById(R.id.afternoonButton);
        Button alarm3 = findViewById(R.id.nightButton);

        TextView text1 = findViewById(R.id.morningText);
        TextView text2 = findViewById(R.id.afternoonText);
        TextView text3 = findViewById(R.id.nightText);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.sithealthcare2", Context.MODE_PRIVATE);

        text1.setText(sharedPreferences.getString("time1", "Morning not set"));
        text2.setText(sharedPreferences.getString("time2", "Afternoon not set"));
        text3.setText(sharedPreferences.getString("time3", "Night not set"));

        alarm1.setOnClickListener(v -> {
            time = 1;
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "Time Picker");
        });

        alarm2.setOnClickListener(v -> {
            time = 2;
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "Time Picker");
        });

        alarm3.setOnClickListener(v -> {
            time = 3;
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "Time Picker");
        });
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "NotificationChannel";
            String description =  "Sample Notification Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel1", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        updateTimeText(calendar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            startAlarm(calendar);
        }
    }

    private void updateTimeText(Calendar calendar) {
        TextView alarmTime = null;
        String text = "", text1 = "";
        if(time==1) {
            text = "Morning: ";
            text1 = "time1";
            alarmTime = findViewById(R.id.morningText);
        }
        else if(time==2) {
            text = "Afternoon: ";
            text1 = "time2";
            alarmTime = findViewById(R.id.afternoonText);
        }
        else {
            text = "Night: ";
            text1 = "time3";
            alarmTime = findViewById(R.id.nightText);
        }
//        String alarmTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(c);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        alarmTime.setText(text + simpleDateFormat.format(calendar.getTime()));
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.sithealthcare2", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(text1, text + simpleDateFormat.format(calendar.getTime())).apply();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationBroadcastReceiver.class);
        intent.putExtra("time", time);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, time, intent, 0);
        assert alarmManager != null;
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000*60*60*24, pendingIntent);
    }

}
