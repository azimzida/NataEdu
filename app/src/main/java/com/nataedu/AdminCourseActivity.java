package com.nataedu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminCourseActivity extends AppCompatActivity {

    private LinearLayout dynamicCategoryContainer;
    private EditText etSearch;
    private FirebaseFirestore db;
    private List<Course> allMaterials = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_course);

        db = FirebaseFirestore.getInstance();
        dynamicCategoryContainer = findViewById(R.id.dynamicCategoryContainer);
        etSearch = findViewById(R.id.etSearch);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        
        // Bottom Nav Admin
        findViewById(R.id.navAdminHome).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminActivity.class));
            finish();
        });

        findViewById(R.id.navAdminUploaded).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminUploadedActivity.class));
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
        db.collection("courses")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        allMaterials.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Course c = document.toObject(Course.class);
                            c.setId(document.getId());
                            allMaterials.add(c);
                        }
                        setupDynamicCategories(""); 
                    }
                });
    }

    private void setupDynamicCategories(String query) {
        dynamicCategoryContainer.removeAllViews();

        // Grouping materials by category
        Map<String, List<Course>> grouped = new HashMap<>();
        for (Course c : allMaterials) {
            if (c.getNama_course().toLowerCase().contains(query.toLowerCase())) {
                String cat = c.getKategori() != null ? c.getKategori() : "Uncategorized";
                if (!grouped.containsKey(cat)) {
                    grouped.put(cat, new ArrayList<>());
                }
                grouped.get(cat).add(c);
            }
        }

        // Create UI for each category found (Matches the reference image)
        for (String categoryName : grouped.keySet()) {
            addCategorySectionToUI(categoryName, grouped.get(categoryName));
        }
    }

    private void addCategorySectionToUI(String name, List<Course> materials) {
        // Category Header (e.g. "Coding")
        TextView tvTitle = new TextView(this);
        tvTitle.setText(name);
        tvTitle.setTextColor(Color.parseColor("#5D3E3E"));
        tvTitle.setTextSize(18);
        tvTitle.setTypeface(null, android.graphics.Typeface.BOLD);
        tvTitle.setPadding(0, 24, 0, 8);
        dynamicCategoryContainer.addView(tvTitle);

        // Thin Divider Line
        View line = new View(this);
        line.setBackgroundColor(Color.parseColor("#EEEEEE"));
        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 3);
        lineParams.setMargins(0, 4, 0, 24);
        dynamicCategoryContainer.addView(line, lineParams);

        // RecyclerView with 2-column Grid for Materials
        RecyclerView rv = new RecyclerView(this);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        MaterialAdapter adapter = new MaterialAdapter(materials, this::onMaterialClick);
        rv.setAdapter(adapter);
        rv.setNestedScrollingEnabled(false); 
        dynamicCategoryContainer.addView(rv);
    }

    private void filterMaterials(String query) {
        setupDynamicCategories(query);
    }

    private void onMaterialClick(Course material) {
        Intent intent = new Intent(this, CourseMaterialActivity.class);
        intent.putExtra("COURSE_NAME", material.getNama_course());
        intent.putExtra("AUTHOR", material.getAuthor());
        intent.putExtra("DESC", material.getDeskripsi());
        intent.putExtra("PRICE", material.getPrice());
        intent.putExtra("PDF_URL", material.getPdf_url());
        startActivity(intent);
    }
}
