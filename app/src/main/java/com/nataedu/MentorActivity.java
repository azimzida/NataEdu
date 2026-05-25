package com.nataedu;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout; // Tambahkan import ini
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
        // Jika root layout kamu tidak punya ID, kamu bisa pakai android.R.id.content
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        // 3. Fungsi Tombol Back (Kembali)
        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 4. Fungsi Navigasi Home di bagian bawah
        // Pastikan ID 'navHomeMentor' sudah kamu tambah di mentor.xml
        LinearLayout navHome = findViewById(R.id.navHomeMentor);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                finish(); // Menutup halaman mentor dan kembali ke Home
            });
        }
    }
}