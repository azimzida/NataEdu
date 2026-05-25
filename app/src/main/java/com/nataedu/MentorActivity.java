package com.nataedu;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge; // 1. Pastikan ada import ini
import androidx.appcompat.app.AppCompatActivity;

public class MentorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // 2. Tambahkan ini sebelum setContentView
        setContentView(R.layout.mentor);

        // Kode untuk tombol back yang tadi
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            finish();
        });
    }
}