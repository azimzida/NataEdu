package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_page);

        LinearLayout navHome = findViewById(R.id.navHome);
        LinearLayout navCourse = findViewById(R.id.navCourse);
        LinearLayout navMentor = findViewById(R.id.navMentor);

        navHome.setOnClickListener(v -> {
            // udah di home
        });

        // Ganti bagian navigasi di dalam onCreate menjadi begini:

        navCourse.setOnClickListener(v -> {
            Intent intent = new Intent(this, CourseActivity.class);
            // Flag ini gunanya: "Jika halaman Course sudah ada, bawa ke depan. Jangan buat baru."
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });

        navMentor.setOnClickListener(v -> {
            Intent intent = new Intent(this, MentorActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });
    }
}