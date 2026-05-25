package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Agar full screen
        EdgeToEdge.enable(this);

        setContentView(R.layout.home_page);

        // Tambahkan ini agar konten tidak tertutup status bar
        if (findViewById(R.id.main) != null) {
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(0, systemBars.top, 0, 0);
                return insets;
            });
        }

        // --- INISIALISASI TOMBOL NAVIGASI ---
        LinearLayout navHome = findViewById(R.id.navHome);
        LinearLayout navCourse = findViewById(R.id.navCourse);
        LinearLayout navMentor = findViewById(R.id.navMentor);

        // Klik Home (tetap di sini)
        navHome.setOnClickListener(v -> {
            // Sudah di Home
        });

        // Klik Course (pindah ke CourseActivity jika sudah ada)
        navCourse.setOnClickListener(v -> {
            // startActivity(new Intent(this, CourseActivity.class));
        });

        // KLIK MENTOR (Pindah ke MentorActivity)
        navMentor.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MentorActivity.class);
            startActivity(intent);
        });
    }
}