package com.example.yemekdefteri;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private ArrayList<Recipe> recipeList;
    private DatabaseHelper dbHelper;
    private Button buttonListRecipes;
    private Button buttonAddRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonAddRecipe = findViewById(R.id.buttonAddRecipe);
        buttonListRecipes = findViewById(R.id.buttonListRecipes);

        dbHelper = new DatabaseHelper(this);

        buttonAddRecipe.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
            startActivity(intent);
        });

        buttonListRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListRecipesActivity.class);
            startActivity(intent);
        });


        // Tarifleri yükle
        loadRecipes();
    }

    private void loadRecipes() {
        recipeList = dbHelper.getAllRecipes();
        recipeAdapter = new RecipeAdapter(recipeList, this, new RecipeAdapter.OnRecipeClickListener() {
            @Override
            public void onDeleteClick(int position) {
                Recipe recipeToDelete = recipeList.get(position);
                dbHelper.deleteRecipe(recipeToDelete.getId());
                recipeList.remove(position);
                recipeAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onUpdateClick(int position) {
                Recipe recipeToUpdate = recipeList.get(position);
                Intent intent = new Intent(MainActivity.this, UpdateRecipeActivity.class);
                intent.putExtra("RECIPE_ID", recipeToUpdate.getId());
                intent.putExtra("RECIPE_NAME", recipeToUpdate.getName());
                intent.putExtra("RECIPE_DETAILS", recipeToUpdate.getRecipeDetails());
                intent.putExtra("RECIPE_CALORIES", recipeToUpdate.getCalories());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(recipeAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecipes(); // Aktiviteye döndüğümüzde listeyi yeniler.
    }
}