package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView rvHistory;
    private HistoryAdapter adapter;
    private List<History> historyList;
    private FirebaseFirestore db;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.history);

        db = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getUid();

        if (userId == null) {
            finish();
            return;
        }

        rvHistory = findViewById(R.id.rvHistory);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));

        historyList = new ArrayList<>();
        adapter = new HistoryAdapter(historyList);
        rvHistory.setAdapter(adapter);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Navigasi Bottom (Opsional jika ingin aktif)
        LinearLayout navHome = findViewById(R.id.navHome);
        navHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        listenToHistoryRealtime();
    }

    private void listenToHistoryRealtime() {
        // Snapshot Listener membuat halaman ini terupdate otomatis jika ada data baru masuk
        db.collection("users").document(userId).collection("history")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (value != null) {
                        historyList.clear();
                        historyList.addAll(value.toObjects(History.class));
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}