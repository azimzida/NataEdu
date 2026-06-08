package com.nataedu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AdminEditCourseActivity extends AppCompatActivity {

    private EditText etCourseName, etDescription, etPrice, etAuthor;
    private TextView tvSelectedFileName;
    private Spinner spKategori;
    private Button btnUpdateCourse, btnPickPdf;
    private FirebaseFirestore db;

    private String courseId, existingPdfUrl;
    private Uri selectedPdfUri;
    private String selectedFileName;

    private final ActivityResultLauncher<Intent> pickPdfLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedPdfUri = result.getData().getData();
                    selectedFileName = getFileName(selectedPdfUri);
                    tvSelectedFileName.setText(selectedFileName);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_edit_course);

        db = FirebaseFirestore.getInstance();

        etCourseName = findViewById(R.id.etCourseName);
        etDescription = findViewById(R.id.etDescription);
        etPrice = findViewById(R.id.etPrice);
        etAuthor = findViewById(R.id.etAuthor);
        tvSelectedFileName = findViewById(R.id.tvSelectedFileName);
        spKategori = findViewById(R.id.spKategori);
        btnUpdateCourse = findViewById(R.id.btnUpdateCourse);
        btnPickPdf = findViewById(R.id.btnPickPdf);
        ImageView btnBack = findViewById(R.id.btnBack);

        // Ambil data dari Intent
        courseId = getIntent().getStringExtra("COURSE_ID");
        String name = getIntent().getStringExtra("COURSE_NAME");
        String author = getIntent().getStringExtra("COURSE_AUTHOR");
        String desc = getIntent().getStringExtra("COURSE_DESC");
        String price = getIntent().getStringExtra("COURSE_PRICE");
        String category = getIntent().getStringExtra("COURSE_CATEGORY");
        existingPdfUrl = getIntent().getStringExtra("COURSE_PDF");

        // Set data ke View
        etCourseName.setText(name);
        etAuthor.setText(author);
        etDescription.setText(desc);
        etPrice.setText(price);

        loadCategoriesFromFirestore(category);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        btnPickPdf.setOnClickListener(v -> pickPdfFile());
        btnUpdateCourse.setOnClickListener(v -> updateCourse());
    }

    private void loadCategoriesFromFirestore(String selectedCategory) {
        db.collection("categories").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                java.util.List<String> categories = new java.util.ArrayList<>();
                for (com.google.firebase.firestore.QueryDocumentSnapshot doc : task.getResult()) {
                    String catName = doc.getString("name");
                    if (catName != null) categories.add(catName);
                }
                if (categories.isEmpty()) categories.add("Coding");
                
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spKategori.setAdapter(adapter);

                // Pilih kategori yang sesuai
                if (selectedCategory != null) {
                    int pos = categories.indexOf(selectedCategory);
                    if (pos != -1) spKategori.setSelection(pos);
                }
            }
        });
    }

    private void pickPdfFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        pickPdfLauncher.launch(intent);
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (android.database.Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index != -1) result = cursor.getString(index);
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) result = result.substring(cut + 1);
        }
        return result;
    }

    private void updateCourse() {
        String name = etCourseName.getText().toString().trim();
        String desc = etDescription.getText().toString().trim();
        String author = etAuthor.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String category = spKategori.getSelectedItem() != null ? spKategori.getSelectedItem().toString() : "";

        if (name.isEmpty() || author.isEmpty()) {
            Toast.makeText(this, "Please fill required fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        btnUpdateCourse.setEnabled(false);

        if (selectedPdfUri != null) {
            // Jika pilih file baru, upload dulu
            btnUpdateCourse.setText("Uploading New PDF...");
            new Thread(() -> {
                String uploadedUrl = uploadToSupabase(selectedPdfUri, selectedFileName);
                runOnUiThread(() -> {
                    if (uploadedUrl != null) {
                        saveUpdateToFirestore(name, desc, author, price, category, uploadedUrl);
                    } else {
                        btnUpdateCourse.setEnabled(true);
                        btnUpdateCourse.setText("Update Course");
                        Toast.makeText(this, "Upload Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        } else {
            // Jika tidak pilih file baru, gunakan URL lama
            saveUpdateToFirestore(name, desc, author, price, category, existingPdfUrl);
        }
    }

    private void saveUpdateToFirestore(String name, String desc, String author, String price, String category, String url) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("nama_course", name);
        updates.put("deskripsi", desc);
        updates.put("author", author);
        updates.put("price", price);
        updates.put("kategori", category);
        updates.put("pdf_url", url);

        db.collection("courses").document(courseId)
                .update(updates)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Course Updated Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    btnUpdateCourse.setEnabled(true);
                    btnUpdateCourse.setText("Update Course");
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private String uploadToSupabase(Uri uri, String fileName) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            byte[] fileData = readAllBytes(inputStream);
            OkHttpClient client = new OkHttpClient();

            // Tambahkan timestamp agar nama file unik dan tidak error 409 Duplicate
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;

            String uploadUrl = SupabaseConfig.SUPABASE_URL + "/storage/v1/object/" + SupabaseConfig.BUCKET_NAME + "/" + uniqueFileName;
            
            RequestBody requestBody = RequestBody.create(fileData, MediaType.parse("application/pdf"));
            Request request = new Request.Builder()
                    .url(uploadUrl)
                    .addHeader("apikey", SupabaseConfig.SUPABASE_KEY)
                    .addHeader("Authorization", "Bearer " + SupabaseConfig.SUPABASE_KEY)
                    .post(requestBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String responseBody = response.body() != null ? response.body().string() : "Empty response";
                if (response.isSuccessful()) {
                    Log.d("SUPABASE_UPLOAD", "Success! Response: " + responseBody);
                    return SupabaseConfig.SUPABASE_URL + "/storage/v1/object/public/" + SupabaseConfig.BUCKET_NAME + "/" + uniqueFileName;
                } else {
                    Log.e("SUPABASE_UPLOAD", "Error Code: " + response.code());
                    Log.e("SUPABASE_UPLOAD", "Response Body: " + responseBody);
                }
            }
        } catch (Exception e) {
            Log.e("SUPABASE_UPLOAD", "Exception: " + e.getMessage());
        }
        return null;
    }

    private byte[] readAllBytes(InputStream inputStream) throws Exception {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}
