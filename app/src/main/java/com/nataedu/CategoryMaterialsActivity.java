package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class CategoryMaterialsActivity extends AppCompatActivity {

    private RecyclerView rvMaterials;
    private MaterialAdapter adapter;
    private List<Course> allMaterials, filteredList;
    private EditText etSearch;
    private TextView tvHeaderTitle;
    private FirebaseFirestore db;
    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.category_materials);

        db = FirebaseFirestore.getInstance();
        categoryName = getIntent().getStringExtra("CATEGORY_NAME");

        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        if (categoryName != null) tvHeaderTitle.setText(categoryName);

        rvMaterials = findViewById(R.id.rvMaterials);
        etSearch = findViewById(R.id.etSearch);

        allMaterials = new ArrayList<>();
        filteredList = new ArrayList<>();

        rvMaterials.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MaterialAdapter(filteredList, this::onMaterialClick);
        rvMaterials.setAdapter(adapter);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

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

    private void onMaterialClick(Course material) {
        Intent intent = new Intent(this, CourseMaterialActivity.class);
        intent.putExtra("COURSE_NAME", material.getNama_course());
        intent.putExtra("AUTHOR", material.getAuthor());
        intent.putExtra("DESC", material.getDeskripsi());
        intent.putExtra("PRICE", material.getPrice());
        intent.putExtra("PDF_URL", material.getPdf_url());
        startActivity(intent);
    }

    private void fetchMaterials() {
        db.collection("courses")
                .whereEqualTo("kategori", categoryName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        allMaterials.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Course material = document.toObject(Course.class);
                            material.setId(document.getId());
                            allMaterials.add(material);
                        }
                        filterMaterials(""); 
                    } else {
                        Toast.makeText(this, "Error fetching materials", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void filterMaterials(String query) {
        filteredList.clear();
        for (Course material : allMaterials) {
            if (material.getNama_course().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(material);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
