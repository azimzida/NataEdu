package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

public class CourseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.course);

        LinearLayout navHome = findViewById(R.id.navHome);
        LinearLayout navCourse = findViewById(R.id.navCourse);
        LinearLayout navMentor = findViewById(R.id.navMentor);

        // Inisialisasi Tombol Back
        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Opsional: Navigasi Bottom Bar di halaman Course
        // Pastikan di layout course.xml kamu sudah menambahkan ID pada bagian bottom nav-nya
        navHome.setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
        });

        navCourse.setOnClickListener(v -> {
            // udah di course
        });

        navMentor.setOnClickListener(v -> {
            startActivity(new Intent(this, MentorActivity.class));
        });
    }
}