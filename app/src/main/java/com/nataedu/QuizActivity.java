package com.nataedu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private Button btnBackToClass, btnFinishQuiz;
    private LinearLayout containerSoal;
    private TextView tvCourseTitle, tvAuthorName;
    private ImageView imgCourse;
    private FirebaseFirestore db;
    private List<QuizQuestion> quizList;
    private List<RadioGroup> radioGroups;
    private String courseName, author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        db = FirebaseFirestore.getInstance();
        quizList = new ArrayList<>();
        radioGroups = new ArrayList<>();

        containerSoal = findViewById(R.id.containerSoal);
        tvCourseTitle = findViewById(R.id.tvCourseTitle);
        tvAuthorName = findViewById(R.id.tvAuthorName);
        imgCourse = findViewById(R.id.imgCourse);
        btnBackToClass = findViewById(R.id.btnBackToClass);
        btnFinishQuiz = findViewById(R.id.btnFinishQuiz);

        courseName = getIntent().getStringExtra("COURSE_NAME");
        author = getIntent().getStringExtra("AUTHOR");

        if (courseName != null) tvCourseTitle.setText(courseName);
        if (author != null) tvAuthorName.setText("by " + author);

        if (courseName != null && imgCourse != null) {
            if (courseName.toLowerCase().contains("ui/ux") || courseName.toLowerCase().contains("ui design")) {
                imgCourse.setImageResource(R.drawable.ui_design_course_icon);
            } else if (courseName.toLowerCase().contains("javascript")) {
                imgCourse.setImageResource(R.drawable.javascript_logo);
            } else {
                imgCourse.setImageResource(R.drawable.nataedu_icon);
            }
        }

        loadQuizzesByCourse();

        btnFinishQuiz.setOnClickListener(v -> finishQuiz());
        btnBackToClass.setOnClickListener(v -> finish());
    }

    private void loadQuizzesByCourse() {
        if (courseName == null) return;

        db.collection("quizzes")
                .whereEqualTo("target_course", courseName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        quizList.clear();
                        containerSoal.removeAllViews();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            QuizQuestion question = document.toObject(QuizQuestion.class);
                            quizList.add(question);
                            addQuestionToUI(question);
                        }
                    } else {
                        String error = task.getException() != null ? task.getException().getMessage() : "Unknown Error";
                        Toast.makeText(this, "Failed: " + error, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void addQuestionToUI(QuizQuestion q) {
        TextView tvQuestion = new TextView(this);
        tvQuestion.setText(String.format("%d. %s", quizList.indexOf(q) + 1, q.getQuestion()));
        tvQuestion.setTextColor(Color.parseColor("#4C1D3D"));
        tvQuestion.setTextSize(15);
        tvQuestion.setTypeface(null, android.graphics.Typeface.BOLD);
        
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 20, 0, 8);
        tvQuestion.setLayoutParams(params);
        containerSoal.addView(tvQuestion);

        RadioGroup rg = new RadioGroup(this);
        rg.setOrientation(RadioGroup.VERTICAL);
        
        RadioButton rbA = new RadioButton(this);
        rbA.setText(q.getOption_a());
        rg.addView(rbA);

        RadioButton rbB = new RadioButton(this);
        rbB.setText(q.getOption_b());
        rg.addView(rbB);

        RadioButton rbC = new RadioButton(this);
        rbC.setText(q.getOption_c());
        rg.addView(rbC);

        RadioButton rbD = new RadioButton(this);
        rbD.setText(q.getOption_d());
        rg.addView(rbD);

        containerSoal.addView(rg);
        radioGroups.add(rg);
    }

    private void finishQuiz() {
        ArrayList<String> questions = new ArrayList<>();
        ArrayList<String> userAnswers = new ArrayList<>();
        ArrayList<String> correctAnswersText = new ArrayList<>();
        ArrayList<ArrayList<String>> options = new ArrayList<>();

        for (int i = 0; i < quizList.size(); i++) {
            QuizQuestion q = quizList.get(i);
            RadioGroup rg = radioGroups.get(i);
            int selectedId = rg.getCheckedRadioButtonId();
            
            String uAns = "No Answer";
            if (selectedId != -1) {
                RadioButton rb = rg.findViewById(selectedId);
                uAns = rb.getText().toString();
            }

            String cAnsText = "";
            if (q.getCorrect_answer().equals("A")) cAnsText = q.getOption_a();
            else if (q.getCorrect_answer().equals("B")) cAnsText = q.getOption_b();
            else if (q.getCorrect_answer().equals("C")) cAnsText = q.getOption_c();
            else if (q.getCorrect_answer().equals("D")) cAnsText = q.getOption_d();

            questions.add(q.getQuestion());
            userAnswers.add(uAns);
            correctAnswersText.add(cAnsText);

            ArrayList<String> opts = new ArrayList<>();
            opts.add(q.getOption_a());
            opts.add(q.getOption_b());
            opts.add(q.getOption_c());
            opts.add(q.getOption_d());
            options.add(opts);
        }

        Intent intent = new Intent(QuizActivity.this, Quiz3Activity.class);
        intent.putExtra("COURSE_NAME", courseName);
        intent.putExtra("AUTHOR", author);
        intent.putStringArrayListExtra("QUESTIONS", questions);
        intent.putStringArrayListExtra("USER_ANSWERS", userAnswers);
        intent.putStringArrayListExtra("CORRECT_ANSWERS", correctAnswersText);
        intent.putExtra("OPTIONS", options);
        startActivity(intent);
        finish();
    }
}
