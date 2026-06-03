package com.nataedu;

import android.content.Intent; // Tambahkan import ini
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MentorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Agar tampilan full screen
        EdgeToEdge.enable(this);

        setContentView(R.layout.mentor);

        // 2. Memperbaiki jarak agar tidak menabrak status bar (jam)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        // 3. Fungsi Tombol Back (Kembali ke halaman sebelumnya)
        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 4. Navigasi HOME di bagian bawah
        LinearLayout navHome = findViewById(R.id.navHomeMentor);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                Intent intent = new Intent(this, HomeActivity.class);
                // FLAG: Kembali ke Home dan tutup semua activity lain yang terbuka di atasnya
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            });
        }

        // 5. Navigasi COURSE di bagian bawah
        LinearLayout navCourse = findViewById(R.id.navCourseMentor);
        if (navCourse != null) {
            navCourse.setOnClickListener(v -> {
                Intent intent = new Intent(this, CourseActivity.class);
                // FLAG: Jika halaman Course sudah terbuka, cukup bawa ke depan (jangan buat duplikat)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            });
        }
    }
}