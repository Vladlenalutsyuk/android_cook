package com.example.myapplication3

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {

    @GET("api/json/v1/1/search.php")
    suspend fun searchRecipe(
        @Query("s") recipeName: String
    ): Response<MealResponse>
}