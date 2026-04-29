package com.example.myapplication3

class RecipeRepository(
    private val recipeDao: RecipeDao
) {

    suspend fun getRecipe(recipeName: String): RecipeEntity? {
        return try {
            val response = RetrofitClient.api.searchRecipe(recipeName)

            if (response.isSuccessful) {
                val meal = response.body()?.meals?.firstOrNull()

                if (meal != null) {
                    val recipe = meal.toRecipeEntity()
                    recipeDao.insertRecipe(recipe)
                    recipe
                } else {
                    recipeDao.getRecipeByName(recipeName)
                }
            } else {
                recipeDao.getRecipeByName(recipeName)
            }
        } catch (e: Exception) {
            recipeDao.getRecipeByName(recipeName)
        }
    }

    private fun MealDto.toRecipeEntity(): RecipeEntity {
        val ingredients = listOf(
            ingredient1 to measure1,
            ingredient2 to measure2,
            ingredient3 to measure3,
            ingredient4 to measure4,
            ingredient5 to measure5
        )
            .filter { !it.first.isNullOrBlank() }
            .joinToString("\n") { pair ->
                "• ${pair.first.orEmpty()} — ${pair.second.orEmpty()}"
            }

        return RecipeEntity(
            id = id,
            title = title.orEmpty(),
            category = category.orEmpty(),
            area = area.orEmpty(),
            instructions = instructions.orEmpty(),
            ingredients = ingredients,
            imageUrl = imageUrl.orEmpty()
        )
    }
}