package com.nataedu;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileActivity extends AppCompatActivity {

    private EditText etUsername, etPhone, etDob;
    private TextView tvEmail, tvProfileName;
    private ImageView profileDetailImage;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userId;
    private Uri selectedImageUri;

    // Launcher untuk pilih gambar dari galeri
    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    profileDetailImage.setImageURI(selectedImageUri); // Preview lokal
                    uploadProfilePicture(); // Langsung upload
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) { finish(); return; }
        userId = user.getUid();

        etUsername = findViewById(R.id.etProfileUsername);
        etPhone = findViewById(R.id.etProfilePhone);
        etDob = findViewById(R.id.etProfileDob);
        tvEmail = findViewById(R.id.tvProfileEmail);
        tvProfileName = findViewById(R.id.tvProfileName);
        profileDetailImage = findViewById(R.id.profileDetailImage);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        etDob.setOnClickListener(v -> showDatePicker());
        findViewById(R.id.btnSaveProfile).setOnClickListener(v -> saveProfileData());

        // Klik foto profil untuk ganti
        profileDetailImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            pickImageLauncher.launch(intent);
        });

        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        loadUserData();
    }

    private void loadUserData() {
        db.collection("users").document(userId).get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        String username = doc.getString("username");
                        String email = doc.getString("email");
                        String phone = doc.getString("no hp");
                        String dob = doc.getString("tanggal_lahir");
                        String avatarUrl = doc.getString("avatar_url");

                        tvProfileName.setText(username != null ? username : "User");
                        etUsername.setText(username);
                        tvEmail.setText(email);
                        etPhone.setText(phone);
                        etDob.setText(dob);

                        // Muat foto profil pakai Glide
                        if (avatarUrl != null && !avatarUrl.isEmpty()) {
                            Glide.with(this).load(avatarUrl).into(profileDetailImage);
                        }
                    }
                });
    }

    private void uploadProfilePicture() {
        if (selectedImageUri == null) return;
        Toast.makeText(this, "Uploading photo...", Toast.LENGTH_SHORT).show();

        new Thread(() -> {
            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                byte[] fileData = readAllBytes(inputStream);
                String fileName = "avatar_" + userId + ".jpg";

                OkHttpClient client = new OkHttpClient();
                String uploadUrl = SupabaseConfig.SUPABASE_URL + "/storage/v1/object/avatars/" + fileName;

                RequestBody requestBody = RequestBody.create(fileData, MediaType.parse("image/jpeg"));
                Request request = new Request.Builder()
                        .url(uploadUrl)
                        .addHeader("apikey", SupabaseConfig.SUPABASE_KEY)
                        .addHeader("Authorization", "Bearer " + SupabaseConfig.SUPABASE_KEY)
                        .post(requestBody).build();

                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful() || response.code() == 409) { // 409 means already exists, we overwrite
                        String publicUrl = SupabaseConfig.SUPABASE_URL + "/storage/v1/object/public/avatars/" + fileName + "?t=" + System.currentTimeMillis();

                        runOnUiThread(() -> {
                            db.collection("users").document(userId).update("avatar_url", publicUrl)
                                    .addOnSuccessListener(aVoid -> Toast.makeText(this, "Photo Updated!", Toast.LENGTH_SHORT).show());
                        });
                    }
                }
            } catch (Exception e) {
                Log.e("UPLOAD_ERR", e.getMessage());
            }
        }).start();
    }

    private void saveProfileData() {
        String username = etUsername.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String dob = etDob.getText().toString().trim();
        if (username.isEmpty()) return;

        Map<String, Object> updates = new HashMap<>();
        updates.put("username", username);
        updates.put("no hp", phone);
        updates.put("tanggal_lahir", dob);

        db.collection("users").document(userId).update(updates)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Profile Saved!", Toast.LENGTH_SHORT).show();
                    tvProfileName.setText(username);
                });
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, day) -> {
            String date = day + "/" + (month + 1) + "/" + year;
            etDob.setText(date);
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private byte[] readAllBytes(InputStream in) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) != -1) out.write(buffer, 0, len);
        return out.toByteArray();
    }
}