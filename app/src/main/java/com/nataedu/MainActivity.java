package com.nataedu;

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

        // ✅ WAJIB ADA
        setContentView(R.layout.home_page);

        // 1. Inisialisasi Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // 2. Tombol Firestore
        Button btnTest = findViewById(R.id.btnTestFirestore);

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

        // Klik profile
        if (findViewById(R.id.profileImage) != null) {
            findViewById(R.id.profileImage).setOnClickListener(v -> {
                Toast.makeText(this, "Silakan klik tombol merah di atas untuk tes Firestore", Toast.LENGTH_SHORT).show();
            });
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}