package com.nataedu;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

// Pastikan diisi "extends AppCompatActivity" biar fungsinya aktif!
public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // 1. Sambungkan ke layout XML kuis lu (pastikan nama file XML-nya sudah benar 'quiz')
        setContentView(R.layout.quiz);

        // Load animasi mentul biar pas tombol Start dipencet kelihatan interaktif
        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.scale_button);

        // 2. Logika untuk Tombol Back di pojok kiri atas
        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // Menutup halaman kuis, balik ke materi/sertifikat
                }
            });
        }

        // 3. Logika untuk Tombol Start Marun
        // Catatan: Pastikan di file quiz.xml lu, tombol marunnya sudah dikasih id: android:id="@+id/btnStartQuiz"
        CardView btnStartQuiz = findViewById(R.id.btnStartQuiz);
        if (btnStartQuiz != null) {
            btnStartQuiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Jalankan efek mentul pas ditekan
                    v.startAnimation(animScale);

                    // Kasih delay bentar biar animasinya selesai kelihatan mentul
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Sementara kasih notifikasi pop-up dulu biar tahu tombolnya berhasil aktif!
                            Toast.makeText(QuizActivity.this, "Quiz Started!", Toast.LENGTH_SHORT).show();

                            // Nanti kalau lu udah bikin halaman soalnya, tinggal pindah intent ke sini:
                            // Intent intent = new Intent(QuizActivity.this, SoalQuizActivity.class);
                            // startActivity(intent);
                        }
                    }, 250); // Delay 0.25 detik
                }
            });
        }
    }
}