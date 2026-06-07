package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard);

        // 1. Header Components
        ImageView adminProfileImage = findViewById(R.id.adminProfileImage);
        TextView tvAdminName = findViewById(R.id.tvAdminName);
        
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.getEmail() != null) {
            tvAdminName.setText(user.getEmail().split("@")[0]);
        }

        // 2. Banner Button
        findViewById(R.id.btnUploadMateriBanner).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminAddCourseActivity.class));
        });

        // 3. Middle Buttons
        findViewById(R.id.btnAddCategoryAdmin).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminAddCategoryActivity.class));
        });

        findViewById(R.id.btnAddQuizAdmin).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminAddQuizActivity.class));
        });

        // 4. Bottom Navigation Admin
        findViewById(R.id.navAdminHome).setOnClickListener(v -> {
            // Already here
        });

        findViewById(R.id.navAdminCourse).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminCourseActivity.class));
        });

        findViewById(R.id.navAdminUploaded).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminUploadedActivity.class));
        });

        // --- LOGIKA LOGOUT (KLIK FOTO PROFIL DI KANAN ATAS) ---
        if (adminProfileImage != null) {
            adminProfileImage.setOnClickListener(v -> showLogoutDialog());
        }
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout", (dialog, which) -> {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
