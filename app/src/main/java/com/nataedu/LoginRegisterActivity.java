package com.nataedu;

import android.content.Intent; // Tambahkan import ini
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Aktifkan fitur Edge-to-Edge
        EdgeToEdge.enable(this);

        // 2. Set layout ke login_register.xml
        setContentView(R.layout.login_register);

        // 3. Inisialisasi tombol Login dan Sign Up
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        // 4. Logika klik tombol Login (Pindah ke LoginActivity)
        if (btnLogin != null) {
            btnLogin.setOnClickListener(v -> {
                Intent intent = new Intent(LoginRegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            });
        }

        // 5. Logika klik tombol Sign Up (Pindah ke SignUpActivity)
        if (btnSignUp != null) {
            btnSignUp.setOnClickListener(v -> {
                Intent intent = new Intent(LoginRegisterActivity.this, SignUpActivity.class);
                startActivity(intent);
            });
        }

        // 6. Atur Padding (Tetap biarkan kode insets kamu di sini)
        if (findViewById(R.id.main) != null) {
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

                // Pastikan ID layout_logo dan layout_buttons ada di XML anda
                if (findViewById(R.id.layout_logo) != null) {
                    findViewById(R.id.layout_logo).setPadding(0, systemBars.top, 0, 0);
                }
                if (findViewById(R.id.layout_buttons) != null) {
                    findViewById(R.id.layout_buttons).setPadding(0, 0, 0, systemBars.bottom);
                }
                return insets;
            });
        }
    }
}