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

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {

    private TextView tvSeeAllHistory;
    private TextView tvUserName;
    private ImageView profileImage;
    private FirebaseFirestore db;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_page);

        tvSeeAllHistory = findViewById(R.id.tvSeeAllHistory);
        tvUserName = findViewById(R.id.tvUserName);
        profileImage = findViewById(R.id.profileImage);
        db = FirebaseFirestore.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId = user.getUid();
            loadUserData();
        }

        LinearLayout navHome = findViewById(R.id.navHome);
        LinearLayout navCourse = findViewById(R.id.navCourse);
        LinearLayout navMentor = findViewById(R.id.navMentor);

        navHome.setOnClickListener(v -> {
            // Sudah di home
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

        findViewById(R.id.tvSeeAllHistory).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        // --- NAVIGASI KE PROFILE ---
        if (profileImage != null) {
            profileImage.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            });
        }


    }

    // Update data setiap kali user kembali ke halaman ini
    @Override
    protected void onResume() {
        super.onResume();
        if (userId != null) {
            loadUserData();
        }
    }

    private void loadUserData() {
        db.collection("users").document(userId).get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        String username = doc.getString("username");
                        String avatarUrl = doc.getString("avatar_url");

                        if (username != null && !username.isEmpty()) {
                            tvUserName.setText(username);
                        }

                        if (avatarUrl != null && !avatarUrl.isEmpty()) {
                            Glide.with(this)
                                    .load(avatarUrl)
                                    .placeholder(R.drawable.fotoprofil) // Gambar default saat loading
                                    .circleCrop() // Agar foto bulat
                                    .into(profileImage);
                        }
                    }
                });
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
