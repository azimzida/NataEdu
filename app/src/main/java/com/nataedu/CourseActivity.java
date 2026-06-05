package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity {

    private RecyclerView rvCourses;
    private CategoryAdapter adapter;
    private List<Category> categoryList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.course);

        db = FirebaseFirestore.getInstance();

        rvCourses = findViewById(R.id.rvCourses);
        rvCourses.setLayoutManager(new LinearLayoutManager(this));

        categoryList = new ArrayList<>();
        adapter = new CategoryAdapter(categoryList, category -> {
            // Pindah ke halaman materi berdasarkan kategori yang dipilih
            // Kita bersihkan nama kategori (buang tulisan " Course") agar pas sama database
            String cleanName = category.getName().replace(" Course", "");
            Intent intent = new Intent(this, CategoryMaterialsActivity.class);
            intent.putExtra("CATEGORY_NAME", cleanName);
            startActivity(intent);
        });

        rvCourses.setAdapter(adapter);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        
        // Navigasi Bawah
        findViewById(R.id.navHome).setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });

        fetchCategoriesFromFirestore();
    }

    private void fetchCategoriesFromFirestore() {
        db.collection("categories")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        categoryList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Ambil nama kategori dari field "name"
                            String name = document.getString("name");
                            if (name != null) {
                                // Kita buat objek Category secara dinamis
                                // Untuk gambar, kita kasih default atau sesuaikan berdasarkan nama
                                int imageRes = R.drawable.course_coding; // Default
                                String desc = "Explore our " + name + " materials!";
                                
                                if (name.toLowerCase().contains("ui/ux")) {
                                    imageRes = R.drawable.course_uiux;
                                } else if (name.toLowerCase().contains("cyber")) {
                                    imageRes = R.drawable.course_cybersecurity;
                                }

                                categoryList.add(new Category(name + " Course", desc, imageRes));
                            }
                        }
                        adapter.notifyDataSetChanged();
                        
                        if (categoryList.isEmpty()) {
                            Toast.makeText(this, "No categories found. Add them as Admin!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Error fetching categories", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
