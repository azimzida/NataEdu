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
        setContentView(R.layout.paymentcourse);

        // Ambil data "estafet"
        String courseName = getIntent().getStringExtra("COURSE_NAME");
        String author = getIntent().getStringExtra("AUTHOR");
        String desc = getIntent().getStringExtra("DESC");
        String pdfUrl = getIntent().getStringExtra("PDF_URL");

        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.scale_button);

        TextView tvPaymentStatusHeader = findViewById(R.id.tvPaymentStatusHeader);
        ImageView ivHeaderIcon = findViewById(R.id.ivHeaderIcon);
        LinearLayout layoutPaymentInfo = findViewById(R.id.layoutPaymentInfo);
        androidx.appcompat.widget.AppCompatButton btnViewPayment = findViewById(R.id.btnViewPayment);
        ImageView btnBack = findViewById(R.id.btnBack);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        if (btnViewPayment != null) {
            btnViewPayment.setText("View payment");
            btnViewPayment.setOnClickListener(v -> {
                v.startAnimation(animScale);
                String currentText = btnViewPayment.getText().toString().trim();

                if (currentText.equalsIgnoreCase("View payment")) {
                    new Handler().postDelayed(() -> {
                        if (tvPaymentStatusHeader != null) tvPaymentStatusHeader.setText("Payment Successful");
                        if (ivHeaderIcon != null) ivHeaderIcon.setImageResource(R.drawable.ic_check);
                        if (layoutPaymentInfo != null) layoutPaymentInfo.setVisibility(View.GONE);
                        btnViewPayment.setText("Continue");
                    }, 200);

                } else if (currentText.equalsIgnoreCase("Continue")) {
                    new Handler().postDelayed(() -> {
                        // PAKAI CourseQuizCertificateActivity sesuai screenshot
                        Intent intent = new Intent(PaymentActivity.this, CourseQuizCertificateActivity.class);
                        intent.putExtra("COURSE_NAME", courseName);
                        intent.putExtra("AUTHOR", author);
                        intent.putExtra("DESC", desc);
                        intent.putExtra("PDF_URL", pdfUrl);
                        startActivity(intent);
                        finish();
                    }, 250);
                }
            });
        }
    }
}
