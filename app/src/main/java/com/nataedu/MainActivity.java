package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // 1. Set layout ke Landing Page
        setContentView(R.layout.landingpage);

        // 2. Hubungkan tombol Get Started ke LoginRegisterActivity
        Button btnGetStarted = findViewById(R.id.btnGetStarted);
        if (btnGetStarted != null) {
            btnGetStarted.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, LoginRegisterActivity.class);
                startActivity(intent);
            });
        }

        // --- Kode Firestore & UI Lama (Ditambahkan Null Check agar tidak crash) ---
        
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Button btnTest = findViewById(R.id.btnTestFirestore); // ID ini ada di home_page.xml

        if (btnTest != null) {
            btnTest.setOnClickListener(v -> {
                Map<String, Object> data = new HashMap<>();
                data.put("nama_aplikasi", "NataEdu");
                data.put("pesan", "Halo dari Android Studio!");
                data.put("timestamp", System.currentTimeMillis());

                db.collection("koneksi_test")
                        .add(data)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(MainActivity.this, "Berhasil Terhubung ke Firestore!", Toast.LENGTH_LONG).show();
                        })
                        .addOnFailureListener(e -> {
                            Log.e("FirestoreError", "Error: " + e.getMessage());
                            Toast.makeText(MainActivity.this, "Gagal: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        });
            });
        }

        if (findViewById(R.id.profileImage) != null) {
            findViewById(R.id.profileImage).setOnClickListener(v -> {
                Toast.makeText(this, "Silakan klik tombol merah di atas untuk tes Firestore", Toast.LENGTH_SHORT).show();
            });
        }

        // Penanganan Window Insets (Null check pada ID 'main')
        if (findViewById(R.id.main) != null) {
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
    }
}