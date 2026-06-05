package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class WelcomeQuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.quiz_welcome);

        String courseName = getIntent().getStringExtra("COURSE_NAME");
        String author = getIntent().getStringExtra("AUTHOR");

        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.scale_button);

        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        CardView btnStartQuiz = findViewById(R.id.btnStartQuiz);
        if (btnStartQuiz != null) {
            btnStartQuiz.setOnClickListener(v -> {
                v.startAnimation(animScale);
                new Handler().postDelayed(() -> {
                    Toast.makeText(WelcomeQuizActivity.this, "Quiz Started!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(WelcomeQuizActivity.this, QuizActivity.class);
                    intent.putExtra("COURSE_NAME", courseName);
                    intent.putExtra("AUTHOR", author);
                    startActivity(intent);
                }, 250);
            });
        }
    }
}
