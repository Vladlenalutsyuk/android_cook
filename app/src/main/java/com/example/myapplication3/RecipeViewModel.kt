package com.example.myapplication3

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val repository: RecipeRepository
) : ViewModel() {

    val recipe = MutableLiveData<RecipeEntity?>()
    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()

    fun loadRecipe(recipeName: String) {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null

            val result = repository.getRecipe(recipeName)

            if (result == null) {
                error.value = "Рецепт не найден"
            }

            recipe.value = result
            isLoading.value = false
        }
    }
}