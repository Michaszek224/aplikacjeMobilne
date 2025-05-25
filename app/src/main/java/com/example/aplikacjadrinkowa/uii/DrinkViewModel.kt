package com.example.aplikacjadrinkowa.uii

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.aplikacjadrinkowa.Drink
import com.example.aplikacjadrinkowa.data.DrinkRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class DrinkListType {
    ALL, FAVORITES
}

data class DrinkUiState(
    val drinks: List<Drink> = emptyList(),
    val displayedDrinks: List<Drink> = emptyList(),
    val favoriteDrinks: List<Drink> = emptyList(),
    val currentListType: DrinkListType = DrinkListType.ALL,
    val searchQuery: String = ""
)

class DrinkViewModel(private val repository: DrinkRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(DrinkUiState())
    val uiState: StateFlow<DrinkUiState> = _uiState.asStateFlow()

    init {
        observeAllDrinks()
        observeFavoriteDrinks()
    }

    private fun observeAllDrinks() {
        viewModelScope.launch {
            repository.getAllDrinks().collect { allDrinks ->
                _uiState.update { currentState ->
                    currentState.copy(
                        drinks = allDrinks,
                        displayedDrinks = filterDrinks(
                            if (currentState.currentListType == DrinkListType.ALL) allDrinks
                            else currentState.favoriteDrinks,
                            currentState.searchQuery
                        )
                    )
                }
            }
        }
    }

    private fun observeFavoriteDrinks() {
        viewModelScope.launch {
            repository.getFavoriteDrinksOnly().collect { favDrinks ->
                _uiState.update { currentState ->
                    val newDisplayedDrinks = if (currentState.currentListType == DrinkListType.FAVORITES) {
                        filterDrinks(favDrinks, currentState.searchQuery)
                    } else {
                        filterDrinks(currentState.drinks, currentState.searchQuery)
                    }
                    currentState.copy(
                        favoriteDrinks = favDrinks,
                        displayedDrinks = newDisplayedDrinks
                    )
                }
            }
        }
    }

    fun toggleFavorite(drink: Drink) {
        viewModelScope.launch {
            if (drink.isFavorite) {
                repository.removeFromFavorites(drink.name)
            } else {
                repository.addToFavorites(drink.name)
            }
        }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { currentState ->
            currentState.copy(
                searchQuery = query,
                displayedDrinks = filterDrinks(
                    if (currentState.currentListType == DrinkListType.ALL) currentState.drinks
                    else currentState.favoriteDrinks,
                    query
                )
            )
        }
    }

    fun showAllDrinks() {
        _uiState.update { currentState ->
            currentState.copy(
                currentListType = DrinkListType.ALL,
                displayedDrinks = filterDrinks(currentState.drinks, currentState.searchQuery)
            )
        }
    }

    fun showFavoriteDrinks() {
        _uiState.update { currentState ->
            currentState.copy(
                currentListType = DrinkListType.FAVORITES,
                displayedDrinks = filterDrinks(currentState.favoriteDrinks, currentState.searchQuery)
            )
        }
    }

    private fun filterDrinks(drinks: List<Drink>, query: String): List<Drink> {
        return if (query.isBlank()) {
            drinks
        } else {
            drinks.filter { it.name.contains(query, ignoreCase = true) }
        }
    }
}

class DrinkViewModelFactory(private val repository: DrinkRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DrinkViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DrinkViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}