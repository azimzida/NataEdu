package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        LinearLayout navHome = findViewById(R.id.navHome);
        LinearLayout navCourse = findViewById(R.id.navCourse);
        LinearLayout navMentor = findViewById(R.id.navMentor);

        navHome.setOnClickListener(v -> {
            // udah di home
        });



        navMentor.setOnClickListener(v -> {
            startActivity(new Intent(this, MentorActivity.class));
        });
    }
}