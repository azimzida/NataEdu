package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentjavascript);

        // Load animasi mentul biar tombol interaktif saat diklik
        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.scale_button);

        // 1. Hubungkan semua komponen ID sesuai XML lu
        TextView tvPaymentStatusHeader = findViewById(R.id.tvPaymentStatusHeader);
        ImageView ivHeaderIcon = findViewById(R.id.ivHeaderIcon);
        LinearLayout layoutPaymentInfo = findViewById(R.id.layoutPaymentInfo);
        androidx.appcompat.widget.AppCompatButton btnViewPayment = findViewById(R.id.btnViewPayment);
        ImageView btnBack = findViewById(R.id.btnBack);

        // 2. Tombol Back Toolbar (Mundur ke halaman Detail Materi)
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 3. Logika Tombol View Payment & Continue
        if (btnViewPayment != null) {
            btnViewPayment.setText("View payment");

            btnViewPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Jalankan animasi mentul setiap tombol diklik
                    v.startAnimation(animScale);

                    String currentText = btnViewPayment.getText().toString().trim();

                    if (currentText.equalsIgnoreCase("View payment")) {
                        // Kasih delay dikit biar animasi selesaian pas perubahan status pertama
                        new Handler().postDelayed(() -> {
                            // 1. Ganti judul atas jadi Sukses
                            if (tvPaymentStatusHeader != null) {
                                tvPaymentStatusHeader.setText("Payment Successful");
                            }

                            // 2. Ganti ikon bulet atas jadi centang drawable lu
                            if (ivHeaderIcon != null) {
                                ivHeaderIcon.setImageResource(R.drawable.ic_check);
                            }

                            // 3. Sembunyikan detail harga & info kedaluwarsa QRIS
                            if (layoutPaymentInfo != null) {
                                layoutPaymentInfo.setVisibility(View.GONE);
                            }

                            // 4. Ganti teks tombolnya jadi "Continue"
                            btnViewPayment.setText("Continue");
                        }, 200);

                    } else if (currentText.equalsIgnoreCase("Continue")) {
                        // JANGAN finish() di sini, langsung gass pindah ke halaman Quiz & Certificate!
                        new Handler().postDelayed(() -> {
                            Intent intent = new Intent(PaymentActivity.this, JavascriptQuizCertificateActivity.class);
                            startActivity(intent);

                            // Tambahin finish() di bawah startActivity kalau lu mau halaman payment-nya
                            // langsung ditutup permanen (biar user ga bisa back ke halaman QRIS lagi).
                            finish();
                        }, 250); // Delay 0.25 detik biar animasinya beres mentul dulu
                    }
                }
            });
        }
    }
}