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
            String cleanName = category.getName().replace(" Course", "");
            Intent intent = new Intent(this, CategoryMaterialsActivity.class);
            intent.putExtra("CATEGORY_NAME", cleanName);
            startActivity(intent);
        });

        rvCourses.setAdapter(adapter);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        
        // Navigasi Bawah
        findViewById(R.id.navHome).setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        findViewById(R.id.navMentor).setOnClickListener(v -> {
            Intent intent = new Intent(this, MentorActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });

        fetchCategoriesFromFirestore();
    }

    private void fetchCategoriesFromFirestore() {
        db.collection("categories")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        categoryList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.getString("name");
                            if (name != null) {
                                int imageRes = R.drawable.nataedu_icon; 
                                String desc = "Explore our " + name + " materials!";
                                
                                if (name.toLowerCase().contains("ui/ux")) {
                                    imageRes = R.drawable.ui_design_course_icon;
                                } else if (name.toLowerCase().contains("coding")) {
                                    imageRes = R.drawable.course_coding;
                                } else if (name.toLowerCase().contains("cyber")) {
                                    imageRes = R.drawable.course_cybersecurity;
                                }

                                categoryList.add(new Category(name + " Course", desc, imageRes));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Error fetching categories", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
