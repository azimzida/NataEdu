package com.nataedu;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private EditText etUsername, etEmail, etPassword;
    private AppCompatButton btnSignUpSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.signup);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        etUsername = findViewById(R.id.etSignupUsername);
        etEmail = findViewById(R.id.etSignupEmail);
        etPassword = findViewById(R.id.etSignupPassword);
        btnSignUpSubmit = findViewById(R.id.btnSignUpSubmit);
        ImageView btnBack = findViewById(R.id.btnBack);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnSignUpSubmit.setOnClickListener(v -> prosesDaftar());
    }

    private void prosesDaftar() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            simpanDataUserKeFirestore(user.getUid(), username, email);
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "Gagal: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void simpanDataUserKeFirestore(String userId, String username, String email) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", userId);
        userMap.put("username", username);
        userMap.put("email", email);
        userMap.put("no hp", ""); // Sesuai nama kolom di gambar
        userMap.put("password_hash", "managed_by_firebase_auth");
        userMap.put("role", "user");
        userMap.put("level", "beginner");
        userMap.put("avatar_url", null);
        userMap.put("fcm_token", null);
        userMap.put("tanggal_daftar", FieldValue.serverTimestamp());
        userMap.put("last_login", FieldValue.serverTimestamp());

        db.collection("users").document(userId)
                .set(userMap)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(SignUpActivity.this, "Pendaftaran Berhasil!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(SignUpActivity.this, "Gagal Simpan Data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}