package com.nataedu; // Ganti dengan nama package kamu

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentjavascript); // Pastikan nama layout xml payment lu bener

        // 1. Hubungkan semua komponen ID sesuai XML baru lu
        TextView tvPaymentStatusHeader = findViewById(R.id.tvPaymentStatusHeader);
        ImageView ivHeaderIcon = findViewById(R.id.ivHeaderIcon);
        LinearLayout layoutPaymentInfo = findViewById(R.id.layoutPaymentInfo);
        androidx.appcompat.widget.AppCompatButton btnViewPayment = findViewById(R.id.btnViewPayment);
        ImageView btnBack = findViewById(R.id.btnBack);

        // 2. Tombol Back Toolbar (Mundur ke halaman Detail Materi)
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 3. Logika Tombol View Payment (Anti-Mental Balik)
        if (btnViewPayment != null) {
            // Kita set teks awalnya lewat Java dulu biar AMAN dan PASTI kebaca "View payment"
            btnViewPayment.setText("View payment");

            btnViewPayment.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String currentText = btnViewPayment.getText().toString().trim();

                    // Pake equalsIgnoreCase biar akurat ngebaca teksnya
                    if (currentText.equalsIgnoreCase("View payment")) {

                        // 1. Ganti judul atas jadi Sukses
                        if (tvPaymentStatusHeader != null) {
                            tvPaymentStatusHeader.setText("Payment Successful");
                        }

                        // 2. Ganti ikon bulet atas jadi centang bawaan android biar ga error nyari drawable
                        if (ivHeaderIcon != null) {
                            ivHeaderIcon.setImageResource(R.drawable.ic_check);
                        }

                        // 3. Sembunyikan detail harga & info kedaluwarsa QRIS
                        if (layoutPaymentInfo != null) {
                            layoutPaymentInfo.setVisibility(View.GONE);
                        }

                        // 4. Ganti teks tombolnya jadi "Continue"
                        btnViewPayment.setText("Continue");

                    } else if (currentText.equalsIgnoreCase("Continue")) {
                        // Pas teksnya udah berubah jadi "Continue" dan diklik lagi,
                        // baru lu arahin mau ke mana. Misal balik ke halaman utama/materi.
                        finish();
                    }
                }
            });
        }
    }}