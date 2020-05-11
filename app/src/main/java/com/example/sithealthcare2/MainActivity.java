package com.example.sithealthcare2;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView name = findViewById(R.id.name);
        Button prescription = findViewById(R.id.prescription);
        Button time = findViewById(R.id.time);

        name.animate().alpha(1).setDuration(1500).setListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                name.animate().alpha(0).setDuration(1500).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        prescription.animate().alpha(1).setDuration(500);
                        time.animate().alpha(1).setDuration(500);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        prescription.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, PrescriptionActivity.class));
        });

        time.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, NotificationActivity.class));
        });
    }
}
