package com.example.sithealthcare2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button prescription = findViewById(R.id.prescription);
        Button time = findViewById(R.id.time);

        prescription.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, PrescriptionActivity.class));
        });

        time.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, NotificationActivity.class));
        });
    }
}
