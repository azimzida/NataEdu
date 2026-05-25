package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.cardview.widget.CardView; // Tambahkan import ini

public class CourseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.course);

        // --- INISIALISASI NAVIGASI BAWAH ---
        LinearLayout navHome = findViewById(R.id.navHome);
        LinearLayout navCourse = findViewById(R.id.navCourse);
        LinearLayout navMentor = findViewById(R.id.navMentor);

        navHome.setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
        });

        navCourse.setOnClickListener(v -> {
            // Sudah di halaman Course
        });

        navMentor.setOnClickListener(v -> {
            startActivity(new Intent(this, MentorActivity.class));
        });

        // --- INISIALISASI TOMBOL BACK ---
        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // --- REVISI: KLIK SATU KOTAK CARD CODING ---
        // Mencari CardView berdasarkan ID yang sudah kita buat di course.xml
        CardView cardCodingCourse = findViewById(R.id.cardCodingCourse);
        if (cardCodingCourse != null) {
            cardCodingCourse.setOnClickListener(v -> {
                Intent intent = new Intent(CourseActivity.this, CodingCourseActivity.class);
                startActivity(intent);
            });
        }
    }
}