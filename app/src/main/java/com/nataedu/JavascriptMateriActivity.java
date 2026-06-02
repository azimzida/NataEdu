package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView; // Pastikan import ini ada

public class JavascriptMateriActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.javascript_materi);

        // Load animasi
        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.scale_button);

        // 1. TOMBOL BACK
        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        // 2. TOMBOL MODUL (Pindah ke DetailModulActivity)
        CardView btnModul = findViewById(R.id.btnModul);
        if (btnModul != null) {
            btnModul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Jalankan animasi klik
                    v.startAnimation(animScale);

                    // Beri sedikit delay agar animasi terlihat sebelum pindah halaman
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(JavascriptMateriActivity.this, DetailModulActivity.class);
                            startActivity(intent);
                        }
                    }, 250);
                }
            });
        }

        // 3. TOMBOL PROCEED TO PAYMENT
        Button btnPayment = findViewById(R.id.btnPayment);
        if (btnPayment != null) {
            btnPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(animScale);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(JavascriptMateriActivity.this, PaymentActivity.class);
                            startActivity(intent);
                        }
                    }, 250);
                }
            });
        }
    }
}