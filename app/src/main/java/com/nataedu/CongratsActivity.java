package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CongratsActivity extends AppCompatActivity {

    private ImageButton btnBack, btnSeeDetail;
    private TextView txtScorePercent, txtSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congrats);

        btnBack = findViewById(R.id.btnBack);
        btnSeeDetail = findViewById(R.id.btnSeeDetail);
        txtScorePercent = findViewById(R.id.txtScorePercent);
        txtSummary = findViewById(R.id.txtSummary);

        int correct = getIntent().getIntExtra("CORRECT_ANSWERS", 0);
        int total = getIntent().getIntExtra("TOTAL_QUESTIONS", 3);
        
        // Menghitung skor (skala 100)
        int score = 0;
        if (total > 0) {
            score = (int) (((double) correct / total) * 100);
        }

        txtScorePercent.setText("Your Score " + score);
        txtSummary.setText("You attempt " + total + " questions and from that " + correct + " answer is correct.");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSeeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sesuai permintaan, ini bisa diarahkan kembali ke review atau lainnya
                finish();
            }
        });
    }
}
