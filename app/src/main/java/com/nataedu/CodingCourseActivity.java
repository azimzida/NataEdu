package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView; // Tambahkan import ini

public class CodingCourseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.coding_course);

        // --- TOMBOL BACK ---
        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // --- STEP 3: KLIK KARTU JAVASCRIPT ---
        // Pastikan di coding_course.xml, CardView JavaScript sudah diberi android:id="@+id/cardJavascript"
        CardView cardJavascript = findViewById(R.id.cardJavascript);
        if (cardJavascript != null) {
            cardJavascript.setOnClickListener(v -> {
                Intent intent = new Intent(CodingCourseActivity.this, JavascriptMateriActivity.class);
                startActivity(intent);
            });
        }

        // --- NAVIGASI BAWAH ---
        LinearLayout navHome = findViewById(R.id.navHomeCoding);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                startActivity(new Intent(this, HomeActivity.class));
            });
        }

        LinearLayout navCourse = findViewById(R.id.navCourseCoding);
        if (navCourse != null) {
            navCourse.setOnClickListener(v -> {
                // Sudah di halaman Course
            });
        }

        LinearLayout navMentor = findViewById(R.id.navMentorCoding);
        if (navMentor != null) {
            navMentor.setOnClickListener(v -> {
                startActivity(new Intent(this, MentorActivity.class));
            });
        }
    }
}