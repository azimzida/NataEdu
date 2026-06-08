package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class CourseMaterialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.course_materi);

        // Ambil data dari Intent
        String courseName = getIntent().getStringExtra("COURSE_NAME");
        String author = getIntent().getStringExtra("AUTHOR");
        String desc = getIntent().getStringExtra("DESC");
        String price = getIntent().getStringExtra("PRICE");
        String pdfUrl = getIntent().getStringExtra("PDF_URL");

        // Hubungkan ke UI
        TextView tvTitle = findViewById(R.id.tvCourseTitle);
        TextView tvAuthor = findViewById(R.id.tvAuthorName);
        TextView tvDesc = findViewById(R.id.tvDescription);
        TextView tvPrice = findViewById(R.id.tvPriceValue);
        ImageView imgBanner = findViewById(R.id.imgBanner);
        Button btnPayment = findViewById(R.id.btnPayment);
        ImageView btnBack = findViewById(R.id.btnBack);

        // Set data ke UI biar dinamis
        if (courseName != null) tvTitle.setText(courseName);
        if (author != null) tvAuthor.setText("by " + author);
        if (desc != null) tvDesc.setText(desc);
        if (price != null) tvPrice.setText("Rp " + price);

        // Set Banner Image Dinamis
        if (courseName != null) {
            if (courseName.toLowerCase().contains("ui/ux") || courseName.toLowerCase().contains("ui design")) {
                imgBanner.setImageResource(R.drawable.ui_design_course_icon);
            } else if (courseName.toLowerCase().contains("javascript")) {
                imgBanner.setImageResource(R.drawable.javascript_logo);
            } else {
                imgBanner.setImageResource(R.drawable.nataedu_icon);
            }
        }

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        if (btnPayment != null) {
            btnPayment.setOnClickListener(v -> {
                Intent intent = new Intent(this, PaymentActivity.class);
                intent.putExtra("COURSE_NAME", courseName);
                intent.putExtra("AUTHOR", author);
                intent.putExtra("DESC", desc);
                intent.putExtra("PRICE", price);
                intent.putExtra("PDF_URL", pdfUrl);
                startActivity(intent);
            });
        }

        // --- FIX CRASH: Simpan History dengan ID yang Aman ---
        String userId = com.google.firebase.auth.FirebaseAuth.getInstance().getUid();
        if (userId != null && courseName != null) {
            java.util.Map<String, Object> historyData = new java.util.HashMap<>();
            historyData.put("title", courseName);
            historyData.put("author", author);
            historyData.put("timestamp", com.google.firebase.firestore.FieldValue.serverTimestamp());

            // Ganti "/" dengan "-" agar Firestore tidak menganggapnya sebagai sub-koleksi (Penyebab Crash)
            String safeDocId = courseName.replace("/", "-");

            com.google.firebase.firestore.FirebaseFirestore.getInstance()
                    .collection("users").document(userId)
                    .collection("history").document(safeDocId)
                    .set(historyData)
                    .addOnFailureListener(e -> android.util.Log.e("HISTORY_ERR", e.getMessage()));
        }
    }
}