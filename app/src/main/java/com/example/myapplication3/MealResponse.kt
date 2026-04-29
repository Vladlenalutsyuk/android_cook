package com.example.myapplication3

import com.google.gson.annotations.SerializedName

data class MealResponse(
    val meals: List<MealDto>?
)

data class MealDto(
    @SerializedName("idMeal")
    val id: String,

    @SerializedName("strMeal")
    val title: String?,

    @SerializedName("strCategory")
    val category: String?,

    @SerializedName("strArea")
    val area: String?,

    @SerializedName("strInstructions")
    val instructions: String?,

    @SerializedName("strMealThumb")
    val imageUrl: String?,

    @SerializedName("strIngredient1") val ingredient1: String?,
    @SerializedName("strIngredient2") val ingredient2: String?,
    @SerializedName("strIngredient3") val ingredient3: String?,
    @SerializedName("strIngredient4") val ingredient4: String?,
    @SerializedName("strIngredient5") val ingredient5: String?,

    @SerializedName("strMeasure1") val measure1: String?,
    @SerializedName("strMeasure2") val measure2: String?,
    @SerializedName("strMeasure3") val measure3: String?,
    @SerializedName("strMeasure4") val measure4: String?,
    @SerializedName("strMeasure5") val measure5: String?
)