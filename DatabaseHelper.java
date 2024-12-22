package com.example.yemekdefteri;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Recipes.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_RECIPES = "recipes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_RECIPE = "details";
    public static final String COLUMN_CALORIES = "calories";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_RECIPES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_RECIPE + " TEXT, "
                + COLUMN_CALORIES + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(db);
    }

    public ArrayList<Recipe> getAllRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_RECIPES;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String details = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE));
                int calories = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CALORIES));

                recipes.add(new Recipe(id, name, details, calories));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return recipes;
    }

    public void deleteRecipe(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECIPES, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateRecipe(int id, String name, String details, int calories) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_RECIPE, details);
        values.put(COLUMN_CALORIES, calories);

        db.update(TABLE_RECIPES, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}