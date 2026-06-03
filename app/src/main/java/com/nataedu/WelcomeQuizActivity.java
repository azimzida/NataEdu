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

        // Menyambungkan ke layout welcome screen kuis
        setContentView(R.layout.quiz_welcome);

        // Load animasi tombol
        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.scale_button);

        // Logika untuk Tombol Back di pojok kiri atas
        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // Menutup halaman, balik ke halaman sebelumnya
                }
            });
        }

        // Logika untuk Tombol Start Marun
        CardView btnStartQuiz = findViewById(R.id.btnStartQuiz);
        if (btnStartQuiz != null) {
            btnStartQuiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Jalankan efek mentul pas ditekan
                    v.startAnimation(animScale);

                    // Delay sebentar agar animasi mentul selesai terlihat
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(WelcomeQuizActivity.this, "Quiz Started!", Toast.LENGTH_SHORT).show();

                            // PINDAH INTENT: Masuk ke halaman pengerjaan soal pertama (Quiz1Activity)
                            Intent intent = new Intent(WelcomeQuizActivity.this, QuizActivity.class);
                            startActivity(intent);

                            // Opsional: panggil finish() jika kamu tidak ingin user bisa back lagi ke halaman welcome setelah kuis dimulai
                            // finish();
                        }
                    }, 250); // Delay 0.25 detik
                }
            });
        }
    }
}