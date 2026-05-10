package com.nataedu;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page); // Memanggil layout home_page.xml

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Button btnTest = findViewById(R.id.btnTestFirestore);

        if (btnTest != null) {
            btnTest.setOnClickListener(v -> {
                Map<String, Object> data = new HashMap<>();
                data.put("nama_aplikasi", "NataEdu");
                data.put("pesan", "Berhasil kirim dari Home!");
                data.put("timestamp", System.currentTimeMillis());

                db.collection("koneksi_test")
                        .add(data)
                        .addOnSuccessListener(doc -> Toast.makeText(this, "Data Masuk Firestore!", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Log.e("Error", e.getMessage()));
            });
        }
    }
}