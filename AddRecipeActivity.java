package com.example.yemekdefteri;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddRecipeActivity extends AppCompatActivity {

    private EditText edtRecipeName, edtRecipeDetails, edtCalories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        edtRecipeName = findViewById(R.id.edtRecipeName);
        edtRecipeDetails = findViewById(R.id.edtRecipeDetails);
        edtCalories = findViewById(R.id.edtCalories);
        Button btnSaveRecipe = findViewById(R.id.btnSaveRecipe);

        btnSaveRecipe.setOnClickListener(v -> saveRecipe());
    }

    private void saveRecipe() {
        String name = edtRecipeName.getText().toString().trim();
        String details = edtRecipeDetails.getText().toString().trim();
        String caloriesStr = edtCalories.getText().toString().trim();

        if (name.isEmpty() || details.isEmpty() || caloriesStr.isEmpty()) {
            Toast.makeText(this, "Tüm alanları doldurun.", Toast.LENGTH_SHORT).show();
            return;
        }

        int calories = Integer.parseInt(caloriesStr);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_RECIPE, details);
        values.put(DatabaseHelper.COLUMN_CALORIES, calories);

        long result = db.insert(DatabaseHelper.TABLE_RECIPES, null, values);
        db.close();

        if (result != -1) {
            Toast.makeText(this, "Tarif başarıyla eklendi.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Tarif eklenirken hata oluştu.", Toast.LENGTH_SHORT).show();
        }
    }
}