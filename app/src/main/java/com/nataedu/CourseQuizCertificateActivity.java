package com.nataedu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class CourseQuizCertificateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.course_quiz_certificate);

        // Ambil data dari Intent
        String courseName = getIntent().getStringExtra("COURSE_NAME");
        String author = getIntent().getStringExtra("AUTHOR");
        String desc = getIntent().getStringExtra("DESC");
        String pdfUrl = getIntent().getStringExtra("PDF_URL");

        // Hubungkan ke UI
        TextView tvTitle = findViewById(R.id.tvCourseTitle);
        TextView tvAuthor = findViewById(R.id.tvAuthorName);
        TextView tvDesc = findViewById(R.id.tvDescription);
        ImageView imgBanner = findViewById(R.id.imgBanner);
        ImageView btnBack = findViewById(R.id.btnBack);

        // Set data ke UI biar dinamis
        if (courseName != null && tvTitle != null) tvTitle.setText(courseName);
        if (author != null && tvAuthor != null) tvAuthor.setText("by " + author);
        if (desc != null && tvDesc != null) tvDesc.setText(desc);

        // Set Banner Image Dinamis
        if (courseName != null && imgBanner != null) {
            if (courseName.toLowerCase().contains("ui/ux") || courseName.toLowerCase().contains("ui design")) {
                imgBanner.setImageResource(R.drawable.ui_design_course_icon);
            } else if (courseName.toLowerCase().contains("javascript")) {
                imgBanner.setImageResource(R.drawable.javascript_logo);
            } else {
                imgBanner.setImageResource(R.drawable.nataedu_icon);
            }
        }

        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.scale_button);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Tombol Modul
        LinearLayout btnModul = findViewById(R.id.btnModul);
        if (btnModul != null) {
            btnModul.setOnClickListener(v -> {
                v.startAnimation(animScale);
                if (pdfUrl != null && !pdfUrl.isEmpty()) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl));
                    startActivity(browserIntent);
                }
            });
        }

        // Tombol Quiz
        LinearLayout btnQuiz = findViewById(R.id.btnQuiz);
        if (btnQuiz != null) {
            btnQuiz.setOnClickListener(v -> {
                v.startAnimation(animScale);
                new Handler().postDelayed(() -> {
                    Intent intent = new Intent(CourseQuizCertificateActivity.this, WelcomeQuizActivity.class);
                    intent.putExtra("COURSE_NAME", courseName);
                    intent.putExtra("AUTHOR", author);
                    startActivity(intent);
                }, 250);
            });
        }
    }
}
