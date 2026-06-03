package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CongratsActivity extends AppCompatActivity {

    private ImageButton btnBack, btnSeeCertificate;
    private TextView txtScorePercent, txtSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congrats);

        btnBack = findViewById(R.id.btnBack);
        btnSeeCertificate = findViewById(R.id.btnSeeCertificate);
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

        btnSeeCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CongratsActivity.this, CertificateActivity.class);
                startActivity(intent);
            }
        });
    }
}
