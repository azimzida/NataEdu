package com.nataedu;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class AdminAddCategoryActivity extends AppCompatActivity {

    private EditText etCategoryName;
    private Button btnSaveCategory;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_category);

        db = FirebaseFirestore.getInstance();

        etCategoryName = findViewById(R.id.etCategoryName);
        btnSaveCategory = findViewById(R.id.btnSaveCategory);
        ImageView btnBack = findViewById(R.id.btnBack);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        btnSaveCategory.setOnClickListener(v -> {
            String name = etCategoryName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> cat = new HashMap<>();
            cat.put("name", name);

            db.collection("categories").add(cat)
                    .addOnSuccessListener(doc -> {
                        Toast.makeText(this, "Category Added!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });
    }
}
