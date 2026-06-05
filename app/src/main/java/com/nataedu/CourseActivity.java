package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
    private TextView tvEmptyCourse;
    private CourseAdapter adapter;
    private List<Course> courseList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.course);

        db = FirebaseFirestore.getInstance();

        rvCourses = findViewById(R.id.rvCourses);
        tvEmptyCourse = findViewById(R.id.tvEmptyCourse);
        
        rvCourses.setLayoutManager(new LinearLayoutManager(this));
        courseList = new ArrayList<>();
        adapter = new CourseAdapter(courseList, course -> {
            // Handle course click
            if (course.getNama_course().toLowerCase().contains("coding")) {
                startActivity(new Intent(this, CodingCourseActivity.class));
            } else if (course.getNama_course().toLowerCase().contains("ui/ux")) {
                // Handle UI/UX or other courses
            }
        });
        rvCourses.setAdapter(adapter);

        // --- INISIALISASI NAVIGASI BAWAH ---
        LinearLayout navHome = findViewById(R.id.navHome);
        LinearLayout navCourse = findViewById(R.id.navCourse);
        LinearLayout navMentor = findViewById(R.id.navMentor);

        navHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        navCourse.setOnClickListener(v -> {
            // Sudah di halaman Course
        });

        navMentor.setOnClickListener(v -> {
            Intent intent = new Intent(this, MentorActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });

        // --- INISIALISASI TOMBOL BACK ---
        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        fetchCourses();
    }

    private void fetchCourses() {
        db.collection("courses")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        courseList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Course course = document.toObject(Course.class);
                            course.setId(document.getId());
                            courseList.add(course);
                        }
                        
                        if (courseList.isEmpty()) {
                            rvCourses.setVisibility(View.GONE);
                            tvEmptyCourse.setVisibility(View.VISIBLE);
                        } else {
                            rvCourses.setVisibility(View.VISIBLE);
                            tvEmptyCourse.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(this, "Error fetching courses", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}