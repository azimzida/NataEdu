package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    private Button btnBackToClass, btnFinishQuiz;
    private RadioGroup rgSoal1, rgSoal2, rgSoal3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        rgSoal1 = findViewById(R.id.radioGroupSoal1);
        rgSoal2 = findViewById(R.id.radioGroupSoal2);
        rgSoal3 = findViewById(R.id.radioGroupSoal3);

        btnBackToClass = findViewById(R.id.btnBackToClass);
        btnFinishQuiz = findViewById(R.id.btnFinishQuiz);

        btnFinishQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Ambil ID RadioButton yang dipilih user
                int idJawab1 = rgSoal1.getCheckedRadioButtonId();
                int idJawab2 = rgSoal2.getCheckedRadioButtonId();
                int idJawab3 = rgSoal3.getCheckedRadioButtonId();

                // 2. Siapkan variabel untuk menyimpan teks jawaban
                String jawaban1 = "";
                String jawaban2 = "";
                String jawaban3 = "";

                // 3. Validasi & Ambil teks jawaban jika user sudah memilih
                if (idJawab1 != -1) {
                    RadioButton rb1 = findViewById(idJawab1);
                    jawaban1 = rb1.getText().toString();
                }
                if (idJawab2 != -1) {
                    RadioButton rb2 = findViewById(idJawab2);
                    jawaban2 = rb2.getText().toString();
                }
                if (idJawab3 != -1) {
                    RadioButton rb3 = findViewById(idJawab3);
                    jawaban3 = rb3.getText().toString();
                }

                // 4. Langsung pindah ke Quiz3Activity (Halaman Nilai) bawa data teks jawaban
                Intent intent = new Intent(QuizActivity.this, Quiz3Activity.class);
                intent.putExtra("JAWABAN_1", jawaban1);
                intent.putExtra("JAWABAN_2", jawaban2);
                intent.putExtra("JAWABAN_3", jawaban3);
                startActivity(intent);
            }
        });

        btnBackToClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Menutup halaman, kembali ke kelas
            }
        });
    }
}