package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class JavascriptQuizCertificateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // 1. Ini layout setelah payment yang ada menu Quiz & Certificate
        setContentView(R.layout.javascriptclass_quiz_certificate);

        // Load animasi mentul biar estetik
        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.scale_button);

        // 2. Tombol Back Toolbar
        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 3. Tombol Quiz (LinearLayout) biar bisa pindah ke QuizActivity kosongan kemarin
        LinearLayout btnQuiz = findViewById(R.id.btnQuiz);
        if (btnQuiz != null) {
            btnQuiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(animScale); // Efek mentul

                    // Delay dikit terus pindah ke halaman kuis kosongan
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(JavascriptQuizCertificateActivity.this, WelcomeQuizActivity.class);
                            startActivity(intent);
                        }
                    }, 250);
                }
            });
        }
    }
}