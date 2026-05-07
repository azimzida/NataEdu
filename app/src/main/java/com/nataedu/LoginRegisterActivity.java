package com.nataedu;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Aktifkan fitur Edge-to-Edge agar gambar full screen menembus status bar
        EdgeToEdge.enable(this);

        // 2. Set layout ke login_register.xml
        setContentView(R.layout.login_register);

        // 3. Inisialisasi tombol Login dan Sign Up
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        // 4. Logika klik tombol Login
        if (btnLogin != null) {
            btnLogin.setOnClickListener(v -> {
                Toast.makeText(LoginRegisterActivity.this, "Login button clicked", Toast.LENGTH_SHORT).show();
            });
        }

        // 5. Logika klik tombol Sign Up
        if (btnSignUp != null) {
            btnSignUp.setOnClickListener(v -> {
                Toast.makeText(LoginRegisterActivity.this, "Sign Up button clicked", Toast.LENGTH_SHORT).show();
            });
        }

        // 6. Atur Padding otomatis agar konten tidak tertutup navigasi bawah (Pill Bar)
        // Pastikan di login_register.xml root layout-nya memiliki android:id="@+id/main"
        if (findViewById(R.id.main) != null) {
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
    }
}