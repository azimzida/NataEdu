package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class AdminAddCourseActivity extends AppCompatActivity {

    private EditText etCourseName, etDescription, etPrice, etAuthor, etPdfUrl;
    private Spinner spKategori;
    private Button btnSaveCourse;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_course);

        db = FirebaseFirestore.getInstance();

        etCourseName = findViewById(R.id.etCourseName);
        etDescription = findViewById(R.id.etDescription);
        etPrice = findViewById(R.id.etPrice);
        etAuthor = findViewById(R.id.etAuthor);
        etPdfUrl = findViewById(R.id.etPdfUrl);
        spKategori = findViewById(R.id.spKategori);
        btnSaveCourse = findViewById(R.id.btnSaveCourse);
        ImageView btnBack = findViewById(R.id.btnBack);

        loadCategoriesFromFirestore();

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        btnSaveCourse.setOnClickListener(v -> saveCourseToFirestore());
    }

    private void loadCategoriesFromFirestore() {
        db.collection("categories").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                java.util.List<String> categories = new java.util.ArrayList<>();
                for (com.google.firebase.firestore.QueryDocumentSnapshot doc : task.getResult()) {
                    String catName = doc.getString("name");
                    if (catName != null) categories.add(catName);
                }
                if (categories.isEmpty()) categories.add("Coding");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spKategori.setAdapter(adapter);
            }
        });
    }

    private void saveCourseToFirestore() {
        String name = etCourseName.getText().toString().trim();
        String desc = etDescription.getText().toString().trim();
        String author = etAuthor.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String url = etPdfUrl.getText().toString().trim();
        
        if (spKategori.getSelectedItem() == null || name.isEmpty() || author.isEmpty()) {
            Toast.makeText(this, "Please fill required fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        String category = spKategori.getSelectedItem().toString();

        Map<String, Object> course = new HashMap<>();
        course.put("nama_course", name);
        course.put("deskripsi", desc);
        course.put("author", author);
        course.put("price", price);
        course.put("kategori", category);
        course.put("pdf_url", url); // Link manual dari Google Drive dll
        course.put("created_at", Timestamp.now());

        btnSaveCourse.setEnabled(false);
        db.collection("courses").add(course)
                .addOnSuccessListener(doc -> {
                    Toast.makeText(this, "Course Added Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    btnSaveCourse.setEnabled(true);
                    Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
