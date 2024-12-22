package com.example.yemekdefteri;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private ArrayList<Recipe> recipeList;
    private Context context;
    private OnRecipeClickListener listener;

    public interface OnRecipeClickListener {
        void onDeleteClick(int position);

        void onUpdateClick(int position);
    }

    public RecipeAdapter(ArrayList<Recipe> recipeList, Context context, OnRecipeClickListener listener) {
        this.recipeList = recipeList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.txtRecipeName.setText(recipe.getName());
        holder.txtCalories.setText("Kalori: " + recipe.getCalories());
        holder.txtDetails.setText(recipe.getRecipeDetails());
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }


    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView txtRecipeName, txtCalories, txtDetails;
        Button btnDelete, btnUpdate;

        public RecipeViewHolder(@NonNull View itemView, OnRecipeClickListener listener) {
            super(itemView);
            txtRecipeName = itemView.findViewById(R.id.txtRecipeName);
            txtCalories = itemView.findViewById(R.id.txtCalories);
            txtDetails = itemView.findViewById(R.id.txtDetails);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);

            btnDelete.setOnClickListener(v -> listener.onDeleteClick(getAdapterPosition()));
            btnUpdate.setOnClickListener(v -> listener.onUpdateClick(getAdapterPosition()));
        }
    }
}