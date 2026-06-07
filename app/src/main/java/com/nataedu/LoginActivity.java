package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private EditText etEmail, etPassword;
    private AppCompatButton btnLoginSubmit;
    private TextView tvRegisterLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLoginSubmit = findViewById(R.id.btnLoginSubmit);
        tvRegisterLink = findViewById(R.id.tvRegisterLink);
        ImageView btnBack = findViewById(R.id.btnBack);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        btnLoginSubmit.setOnClickListener(v -> {
            prosesLogin();
        });

        tvRegisterLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void prosesLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError("Email harus diisi");
            return;
        }
        if (password.isEmpty()) {
            etPassword.setError("Password harus diisi");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        checkAndSyncUser(user);
                    } else {
                        String pesanError = task.getException() != null ? task.getException().getMessage() : "Login Gagal";
                        Toast.makeText(LoginActivity.this, "Gagal: " + pesanError, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void checkAndSyncUser(FirebaseUser user) {
        if (user == null) return;

        db.collection("users").document(user.getUid()).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null && task.getResult().exists()) {
                            // Update last login
                            db.collection("users").document(user.getUid())
                                    .update("last_login", FieldValue.serverTimestamp());

                            // CEK ROLE USER UNTUK REDIRECTION
                            String role = task.getResult().getString("role");
                            if ("admin".equalsIgnoreCase(role)) {
                                Toast.makeText(LoginActivity.this, "Selamat Datang, Admin!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Selamat Datang!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                            finish();
                        } else {
                            // Data user tidak ada di Firestore, buatkan default 'user'
                            Map<String, Object> userMap = new HashMap<>();
                            userMap.put("id", user.getUid());
                            userMap.put("username", user.getEmail().split("@")[0]);
                            userMap.put("email", user.getEmail());
                            userMap.put("role", "user");
                            userMap.put("tanggal_daftar", FieldValue.serverTimestamp());
                            userMap.put("last_login", FieldValue.serverTimestamp());

                            db.collection("users").document(user.getUid()).set(userMap)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(LoginActivity.this, "Selamat Datang!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    });
                        }
                    } else {
                        Toast.makeText(this, "Gagal mengambil data user", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
