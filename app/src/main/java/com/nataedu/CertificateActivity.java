package com.nataedu;

import android.content.Intent; // Pastikan import ini ditambahkan
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class CertificateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certificate);

        // Tombol Back (Kembali ke halaman sebelumnya)
        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Tombol Back To Home (Pindah langsung ke halaman Home)
        CardView btnBackToHome = findViewById(R.id.btnBackToHome);
        if (btnBackToHome != null) {
            btnBackToHome.setOnClickListener(v -> {
                // Menggunakan Intent untuk pindah ke HomeActivity
                Intent intent = new Intent(CertificateActivity.this, HomeActivity.class);

                // Flag ini opsional, gunanya untuk membersihkan tumpukan halaman (backstack)
                // agar ketika di halaman Home, jika user menekan tombol back di HP, aplikasi langsung keluar
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(intent);
                finish(); // Menutup halaman sertifikat ini
            });
        }
    }
}