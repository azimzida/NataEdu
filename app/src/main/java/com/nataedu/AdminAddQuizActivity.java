package com.nataedu;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminAddQuizActivity extends AppCompatActivity {

    private EditText etQuestion, etOptionA, etOptionB, etOptionC, etOptionD, etCorrectAnswer;
    private Spinner spCourse;
    private Button btnSaveQuiz;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_quiz);

        db = FirebaseFirestore.getInstance();

        spCourse = findViewById(R.id.spCourse); // Pastikan ID ini ada di XML
        etQuestion = findViewById(R.id.etQuestion);
        etOptionA = findViewById(R.id.etOptionA);
        etOptionB = findViewById(R.id.etOptionB);
        etOptionC = findViewById(R.id.etOptionC);
        etOptionD = findViewById(R.id.etOptionD);
        etCorrectAnswer = findViewById(R.id.etCorrectAnswer);
        btnSaveQuiz = findViewById(R.id.btnSaveQuiz);
        ImageView btnBack = findViewById(R.id.btnBack);

        loadCoursesForSpinner();

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        btnSaveQuiz.setOnClickListener(v -> saveQuizToFirestore());
    }

    private void loadCoursesForSpinner() {
        db.collection("courses").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<String> courseNames = new ArrayList<>();
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    String name = doc.getString("nama_course");
                    if (name != null) courseNames.add(name);
                }
                if (courseNames.isEmpty()) courseNames.add("No Course Available");
                
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCourse.setAdapter(adapter);
            }
        });
    }

    private void saveQuizToFirestore() {
        String question = etQuestion.getText().toString().trim();
        String optA = etOptionA.getText().toString().trim();
        String optB = etOptionB.getText().toString().trim();
        String optC = etOptionC.getText().toString().trim();
        String optD = etOptionD.getText().toString().trim();
        String correct = etCorrectAnswer.getText().toString().trim().toUpperCase();
        
        if (spCourse.getSelectedItem() == null) return;
        String targetCourse = spCourse.getSelectedItem().toString();

        if (question.isEmpty() || optA.isEmpty() || correct.isEmpty()) {
            Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> quiz = new HashMap<>();
        quiz.put("target_course", targetCourse); // KUNCI UTAMA SINKRONISASI
        quiz.put("question", question);
        quiz.put("option_a", optA);
        quiz.put("option_b", optB);
        quiz.put("option_c", optC);
        quiz.put("option_d", optD);
        quiz.put("correct_answer", correct);
        quiz.put("created_at", com.google.firebase.Timestamp.now());

        db.collection("quizzes").add(quiz)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Quiz Added to " + targetCourse, Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show());
    }
}
