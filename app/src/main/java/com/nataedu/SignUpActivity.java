package com.nataedu;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton; // Tambahkan ini
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Import Firebase
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    // 1. Deklarasi Firebase dan View
    private FirebaseAuth mAuth;
    private EditText etUsername, etEmail, etPassword;
    private AppCompatButton btnSignUpSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Aktifkan fitur Edge-to-Edge
        EdgeToEdge.enable(this);
        setContentView(R.layout.signup);

        // 2. Inisialisasi Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // 3. Inisialisasi View berdasarkan ID di signup.xml
        etUsername = findViewById(R.id.etSignupUsername);
        etEmail = findViewById(R.id.etSignupEmail);
        etPassword = findViewById(R.id.etSignupPassword);
        btnSignUpSubmit = findViewById(R.id.btnSignUpSubmit);
        ImageView btnBack = findViewById(R.id.btnBack);

        // Logika agar padding otomatis menyesuaikan status bar/nav bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 4. Logika tombol back
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                finish();
            });
        }

        // 5. Logika Tombol Sign Up
        btnSignUpSubmit.setOnClickListener(v -> {
            prosesDaftar();
        });
    }

    private void prosesDaftar() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validasi Sederhana
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password minimal 6 karakter");
            return;
        }

        // 6. Proses Kirim Data ke Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Jika Berhasil
                        Toast.makeText(SignUpActivity.this, "Pendaftaran Berhasil!", Toast.LENGTH_SHORT).show();
                        finish(); // Kembali ke halaman Login
                    } else {
                        // Jika Gagal (misal email sudah pernah terdaftar)
                        String pesanError = task.getException() != null ? task.getException().getMessage() : "Gagal Daftar";
                        Toast.makeText(SignUpActivity.this, "Kesalahan: " + pesanError, Toast.LENGTH_LONG).show();
                    }
                });
    }
}