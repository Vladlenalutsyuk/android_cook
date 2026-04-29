package com.example.myapplication3

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val category: String,
    val area: String,
    val instructions: String,
    val ingredients: String,
    val imageUrl: String
)