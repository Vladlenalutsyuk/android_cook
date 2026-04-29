package com.example.myapplication3

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Query("SELECT * FROM recipes WHERE title LIKE '%' || :name || '%' LIMIT 1")
    suspend fun getRecipeByName(name: String): RecipeEntity?
}