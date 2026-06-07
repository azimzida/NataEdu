package com.nataedu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_page);

        TextView tvUserName = findViewById(R.id.tvUserName);
        ImageView profileImage = findViewById(R.id.profileImage);
        
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.getEmail() != null) {
            tvUserName.setText(user.getEmail().split("@")[0]);
        }

        LinearLayout navHome = findViewById(R.id.navHome);
        LinearLayout navCourse = findViewById(R.id.navCourse);
        LinearLayout navMentor = findViewById(R.id.navMentor);

        navHome.setOnClickListener(v -> {
            // udah di home
        });

        navCourse.setOnClickListener(v -> {
            Intent intent = new Intent(this, CourseActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });

        navMentor.setOnClickListener(v -> {
            Intent intent = new Intent(this, MentorActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });

        // --- LOGIKA LOGOUT (KLIK FOTO PROFIL DI KANAN ATAS) ---
        if (profileImage != null) {
            profileImage.setOnClickListener(v -> showLogoutDialog());
        }
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout", (dialog, which) -> {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
