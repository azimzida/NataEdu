package com.nataedu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class Quiz3Activity extends AppCompatActivity {

    private TextView txtWrongCount, txtCorrectCount, tvCourseTitle, tvAuthor;
    private LinearLayout containerReview;
    private Button btnSeeScore;
    private int correct = 0;
    private int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz3);

        txtWrongCount = findViewById(R.id.txtWrongCount);
        txtCorrectCount = findViewById(R.id.txtCorrectCount);
        tvCourseTitle = findViewById(R.id.tvCourseTitle);
        tvAuthor = findViewById(R.id.tvAuthor);
        containerReview = findViewById(R.id.containerReview);
        btnSeeScore = findViewById(R.id.btnSeeScore);

        // Ambil data kiriman
        Intent intent = getIntent();
        String courseName = intent.getStringExtra("COURSE_NAME");
        String author = intent.getStringExtra("AUTHOR");
        ArrayList<String> questions = intent.getStringArrayListExtra("QUESTIONS");
        ArrayList<String> userAnswers = intent.getStringArrayListExtra("USER_ANSWERS");
        ArrayList<String> correctAnswers = intent.getStringArrayListExtra("CORRECT_ANSWERS");
        ArrayList<ArrayList<String>> options = (ArrayList<ArrayList<String>>) intent.getSerializableExtra("OPTIONS");

        if (courseName != null) tvCourseTitle.setText(courseName);
        if (author != null) tvAuthor.setText("by " + author);

        if (questions != null && userAnswers != null && correctAnswers != null) {
            total = questions.size();
            for (int i = 0; i < total; i++) {
                boolean isCorrect = userAnswers.get(i).equalsIgnoreCase(correctAnswers.get(i));
                if (isCorrect) correct++;
                
                addReviewItem(i + 1, questions.get(i), userAnswers.get(i), correctAnswers.get(i), options.get(i));
            }
        }

        txtCorrectCount.setText("correct : " + correct);
        txtWrongCount.setText("wrong : " + (total - correct));

        btnSeeScore.setOnClickListener(v -> {
            Intent intentScore = new Intent(Quiz3Activity.this, CongratsActivity.class);
            intentScore.putExtra("CORRECT_ANSWERS", correct);
            intentScore.putExtra("TOTAL_QUESTIONS", total);
            startActivity(intentScore);
            finish();
        });

        findViewById(R.id.btnHeaderBack).setOnClickListener(v -> finish());
    }

    private void addReviewItem(int num, String question, String userAns, String correctAns, ArrayList<String> opts) {
        TextView tvQ = new TextView(this);
        tvQ.setText(num + ". " + question);
        tvQ.setTextColor(Color.parseColor("#4C1D3D"));
        tvQ.setTextSize(15);
        tvQ.setTypeface(null, android.graphics.Typeface.BOLD);
        containerReview.addView(tvQ);

        RadioGroup rg = new RadioGroup(this);
        rg.setEnabled(false); // Supaya ga bisa diklik lagi

        for (String opt : opts) {
            RadioButton rb = new RadioButton(this);
            rb.setText(opt);
            rb.setClickable(false);
            
            if (opt.equalsIgnoreCase(userAns)) {
                rb.setChecked(true);
                if (!userAns.equalsIgnoreCase(correctAns)) {
                    rb.setTextColor(Color.RED);
                } else {
                    rb.setTextColor(Color.parseColor("#4CAF50"));
                }
            }
            
            if (opt.equalsIgnoreCase(correctAns)) {
                // Selalu warnai hijau buat jawaban yang bener
                rb.setTextColor(Color.parseColor("#4CAF50"));
                // Tambahin teks (Correct) di sebelahnya kalo mau
            }
            
            rg.addView(rb);
        }

        containerReview.addView(rg);
        
        // Kasih keterangan tambahan
        TextView tvStatus = new TextView(this);
        if (userAns.equalsIgnoreCase(correctAns)) {
            tvStatus.setText("✅ Correct");
            tvStatus.setTextColor(Color.parseColor("#4CAF50"));
        } else {
            tvStatus.setText("❌ Wrong (Correct: " + correctAns + ")");
            tvStatus.setTextColor(Color.RED);
        }
        tvStatus.setPadding(0, 4, 0, 24);
        containerReview.addView(tvStatus);
    }
}
