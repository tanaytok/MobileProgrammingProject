package com.example.yemekdefteri;

public class Recipe {
    private int id;
    private String name;
    private String recipeDetails;
    private int calories;

    public Recipe(int id, String name, String recipeDetails, int calories) {
        this.id = id;
        this.name = name;
        this.recipeDetails = recipeDetails;
        this.calories = calories;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRecipeDetails() {
        return recipeDetails;
    }

    public int getCalories() {
        return calories;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRecipeDetails(String recipeDetails) {
        this.recipeDetails = recipeDetails;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}