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

public class JavascriptMateriActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.javascript_materi);

        // Load animasi ditaruh di sini (setelah setContentView biar aman dan ga crash)
        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.scale_button);

        // 1. TOMBOL BACK (Mundur ke halaman list course)
        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // Menutup halaman detail materi
                }
            });
        }

        // 2. TOMBOL PROCEED TO PAYMENT (Maju ke halaman QRIS dengan delay animasi)
        Button btnPayment = findViewById(R.id.btnPayment);
        if (btnPayment != null) {
            btnPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 1. Jalankan animasi mantul pas diklik
                    v.startAnimation(animScale);

                    // 2. Kasih jeda waktu 250 milidetik biar animasinya selesai kelihatan mentul
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 3. Setelah animasi kelar, baru pindah ke halaman QRIS
                            Intent intent = new Intent(JavascriptMateriActivity.this, PaymentActivity.class);
                            startActivity(intent);
                        }
                    }, 250); // Jeda 0.25 detik
                }
            });
        }
    }
}