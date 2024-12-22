package com.example.yemekdefteri;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateRecipeActivity extends AppCompatActivity {

    private EditText edtUpdateName, edtUpdateDetails, edtUpdateCalories;
    private int recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recipe);

        edtUpdateName = findViewById(R.id.edtUpdateName);
        edtUpdateDetails = findViewById(R.id.edtUpdateDetails);
        edtUpdateCalories = findViewById(R.id.edtUpdateCalories);
        Button btnUpdateRecipe = findViewById(R.id.btnUpdateRecipe);

        recipeId = getIntent().getIntExtra("RECIPE_ID", -1);
        String recipeName = getIntent().getStringExtra("RECIPE_NAME");
        String recipeDetails = getIntent().getStringExtra("RECIPE_DETAILS");
        int recipeCalories = getIntent().getIntExtra("RECIPE_CALORIES", 0);

        edtUpdateName.setText(recipeName);
        edtUpdateDetails.setText(recipeDetails);
        edtUpdateCalories.setText(String.valueOf(recipeCalories));

        btnUpdateRecipe.setOnClickListener(v ->             updateRecipeInDatabase());
    }

    private void updateRecipeInDatabase() {
        String updatedName = edtUpdateName.getText().toString().trim();
        String updatedDetails = edtUpdateDetails.getText().toString().trim();
        String updatedCaloriesStr = edtUpdateCalories.getText().toString().trim();

        if (updatedName.isEmpty() || updatedDetails.isEmpty() || updatedCaloriesStr.isEmpty()) {
            Toast.makeText(this, "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show();
            return;
        }

        int updatedCalories;
        try {
            updatedCalories = Integer.parseInt(updatedCaloriesStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Kalori bir sayı olmalı!", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.updateRecipe(recipeId, updatedName, updatedDetails, updatedCalories);

        Toast.makeText(this, "Tarif başarıyla güncellendi!", Toast.LENGTH_SHORT).show();
        finish();
    }
}