package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class AdminUploadedActivity extends AppCompatActivity {

    private RecyclerView rvAdminMaterials;
    private AdminMaterialAdapter adapter;
    private List<Course> allMaterials, filteredList;
    private EditText etSearch;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_uploaded);

        db = FirebaseFirestore.getInstance();
        allMaterials = new ArrayList<>();
        filteredList = new ArrayList<>();

        rvAdminMaterials = findViewById(R.id.rvAdminMaterials);
        etSearch = findViewById(R.id.etSearch);

        rvAdminMaterials.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdminMaterialAdapter(filteredList, new AdminMaterialAdapter.OnMaterialActionListener() {
            @Override
            public void onEdit(Course course) {
                // Implementasi Edit (bisa dikembangkan nanti)
                Toast.makeText(AdminUploadedActivity.this, "Edit: " + course.getNama_course(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDelete(Course course) {
                showDeleteDialog(course);
            }
        });
        rvAdminMaterials.setAdapter(adapter);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Navigasi Bawah Admin
        findViewById(R.id.navAdminHome).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminActivity.class));
            finish();
        });

        findViewById(R.id.navAdminCourse).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminCourseActivity.class));
            finish();
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterMaterials(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        fetchMaterials();
    }

    private void fetchMaterials() {
        db.collection("courses").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                allMaterials.clear();
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    Course c = doc.toObject(Course.class);
                    c.setId(doc.getId());
                    allMaterials.add(c);
                }
                filterMaterials("");
            }
        });
    }

    private void filterMaterials(String query) {
        filteredList.clear();
        for (Course c : allMaterials) {
            if (c.getNama_course().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(c);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void showDeleteDialog(Course course) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Material")
                .setMessage("Are you sure you want to delete '" + course.getNama_course() + "'?")
                .setPositiveButton("Yes, Delete", (dialog, which) -> deleteMaterial(course))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteMaterial(Course course) {
        db.collection("courses").document(course.getId()).delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                    fetchMaterials(); // Refresh list
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show());
    }
}
