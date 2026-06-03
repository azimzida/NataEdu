package com.nataedu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Quiz3Activity extends AppCompatActivity {

    private TextView btnHeaderBack, txtWrongCount, txtCorrectCount, txtTotalScore;
    private Button btnSeeScore; // 👈 Ganti nama variabelnya biar serasi
    private String jawab1, jawab2, jawab3;
    private int correct = 0;
    private int wrong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz3);

        btnHeaderBack = findViewById(R.id.btnHeaderBack);
        txtWrongCount = findViewById(R.id.txtWrongCount);
        txtCorrectCount = findViewById(R.id.txtCorrectCount);

        // Ambil data kiriman teks pilihan user dari QuizActivity
        Intent intent = getIntent();
        jawab1 = intent.getStringExtra("JAWABAN_1");
        jawab2 = intent.getStringExtra("JAWABAN_2");
        jawab3 = intent.getStringExtra("JAWABAN_3");

        // Antisipasi jika ada data yang kosong (null)
        if (jawab1 == null) jawab1 = "";
        if (jawab2 == null) jawab2 = "";
        if (jawab3 == null) jawab3 = "";

        // Jalankan proses pencocokan nilai dan penampilan status
        hitungDanKoreksiSkor();

        // Aksi tombol kembali di header
        btnHeaderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUlang = new Intent(Quiz3Activity.this, QuizActivity.class);
                intentUlang.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentUlang);
                finish();
            }
        });
    }

    private void hitungDanKoreksiSkor() {
        // =======================================================================
        // --- KOREKSI SOAL 1 (Kunci: "to create a template for objects") ---
        // =======================================================================
        String kunci1 = "to create a template for objects";

        RadioButton rb1_a = findViewById(R.id.score1_a);
        RadioButton rb1_b = findViewById(R.id.score1_b); // Ini pilihan yang benar
        RadioButton rb1_c = findViewById(R.id.score1_c);
        RadioButton rb1_d = findViewById(R.id.score1_d);

        // Ikon centang hijau di jawaban yang benar selalu ditampilkan
        findViewById(R.id.status1_b).setVisibility(View.VISIBLE);

        koreksiDanCentang(jawab1, kunci1, rb1_a, rb1_b, rb1_c, rb1_d, rb1_b,
                R.id.status1_a, R.id.status1_b, R.id.status1_c, R.id.status1_d);

        // =======================================================================
        // --- KOREKSI SOAL 2 (Kunci: "constructor") ---
        // =======================================================================
        String kunci2 = "constructor";

        RadioButton rb2_a = findViewById(R.id.score2_a);
        RadioButton rb2_b = findViewById(R.id.score2_b);
        RadioButton rb2_c = findViewById(R.id.score2_c); // Ini pilihan yang benar
        RadioButton rb2_d = findViewById(R.id.score2_d);

        findViewById(R.id.status2_c).setVisibility(View.VISIBLE);

        koreksiDanCentang(jawab2, kunci2, rb2_a, rb2_b, rb2_c, rb2_d, rb2_c,
                R.id.status2_a, R.id.status2_b, R.id.status2_c, R.id.status2_d);

        // =======================================================================
        // --- KOREKSI SOAL 3 (Kunci: "new") ---
        // =======================================================================
        String kunci3 = "new";

        RadioButton rb3_a = findViewById(R.id.score3_a);
        RadioButton rb3_b = findViewById(R.id.score3_b);
        RadioButton rb3_c = findViewById(R.id.score3_c); // Ini pilihan yang benar
        RadioButton rb3_d = findViewById(R.id.score3_d);

        findViewById(R.id.status3_c).setVisibility(View.VISIBLE);

        koreksiDanCentang(jawab3, kunci3, rb3_a, rb3_b, rb3_c, rb3_d, rb3_c,
                R.id.status3_a, R.id.status3_b, R.id.status3_c, R.id.status3_d);

        // Update jumlah skor akhir di bagian atas layar
        txtWrongCount.setText("wrong : " + wrong);
        txtCorrectCount.setText("correct : " + correct);
    }

    // Fungsi pemroses otomatis untuk mencentang buletan, mewarnai teks, dan menampilkan ikon silang
    private void koreksiDanCentang(String jawabanUser, String kunciJawaban,
                                   RadioButton a, RadioButton b, RadioButton c, RadioButton d,
                                   RadioButton rbKunciYangBenar,
                                   int statusA, int statusB, int statusC, int statusD) {

        boolean isBenar = jawabanUser.equalsIgnoreCase(kunciJawaban);

        if (isBenar) {
            correct++;
            rbKunciYangBenar.setTextColor(Color.parseColor("#4CAF50")); // Teks kunci jadi hijau jika dipilih
        } else {
            wrong++;
        }

        // Cek teks mana yang dipilih user, lalu aktifkan buletannya (.setChecked(true))
        if (jawabanUser.equals(a.getText().toString())) {
            a.setChecked(true);
            if (!isBenar) {
                a.setTextColor(Color.RED);
                findViewById(statusA).setVisibility(View.VISIBLE); // Muncul ikon silang di pilihan A
            }
        } else if (jawabanUser.equals(b.getText().toString())) {
            b.setChecked(true);
            if (!isBenar) {
                b.setTextColor(Color.RED);
                findViewById(statusB).setVisibility(View.VISIBLE); // Muncul ikon silang di pilihan B
            }
        } else if (jawabanUser.equals(c.getText().toString())) {
            c.setChecked(true);
            if (!isBenar) {
                c.setTextColor(Color.RED);
                findViewById(statusC).setVisibility(View.VISIBLE); // Muncul ikon silang di pilihan C
            }
        } else if (jawabanUser.equals(d.getText().toString())) {
            d.setChecked(true);
            if (!isBenar) {
                d.setTextColor(Color.RED);
                findViewById(statusD).setVisibility(View.VISIBLE); // Muncul ikon silang di pilihan D
            }
        }
    }
}