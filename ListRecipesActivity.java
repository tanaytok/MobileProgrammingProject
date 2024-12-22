package com.example.yemekdefteri;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private ArrayList<Recipe> recipeList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recipes);

        recyclerView = findViewById(R.id.recyclerViewRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);
        recipeList = dbHelper.getAllRecipes();

        adapter = new RecipeAdapter(recipeList, this, new RecipeAdapter.OnRecipeClickListener() {
            @Override
            public void onDeleteClick(int position) {
                Recipe recipe = recipeList.get(position);
                dbHelper.deleteRecipe(recipe.getId());
                recipeList.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(ListRecipesActivity.this, "Tarif silindi.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpdateClick(int position) {
                Recipe recipe = recipeList.get(position);
                Intent intent = new Intent(ListRecipesActivity.this, UpdateRecipeActivity.class);
                intent.putExtra("RECIPE_ID", recipe.getId());
                intent.putExtra("RECIPE_NAME", recipe.getName());
                intent.putExtra("RECIPE_DETAILS", recipe.getRecipeDetails());
                intent.putExtra("RECIPE_CALORIES", recipe.getCalories());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recipeList.clear();
        recipeList.addAll(dbHelper.getAllRecipes());
        adapter.notifyDataSetChanged();
    }
}