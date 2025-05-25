package com.example.aplikacjadrinkowa.data

import com.example.aplikacjadrinkowa.Drink
import com.example.aplikacjadrinkowa.database.FavoriteDrink
import com.example.aplikacjadrinkowa.database.FavoriteDrinkDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DrinkRepository(
    private val staticDrinks: List<Drink>,
    private val favoriteDrinkDao: FavoriteDrinkDao
) {

    fun getAllDrinks(): Flow<List<Drink>> {
        return favoriteDrinkDao.getFavoriteDrinks().map { favorites ->
            staticDrinks.map { drink ->
                drink.copy(isFavorite = favorites.any { it.drinkName == drink.name })
            }
        }
    }

    fun getFavoriteDrinksOnly(): Flow<List<Drink>> {
        return favoriteDrinkDao.getFavoriteDrinks().map { favorites ->
            staticDrinks.filter { drink ->
                favorites.any { it.drinkName == drink.name }
            }.map { it.copy(isFavorite = true) }
        }
    }

    suspend fun addToFavorites(drinkName: String) {
        favoriteDrinkDao.addToFavorites(FavoriteDrink(drinkName))
    }

    suspend fun removeFromFavorites(drinkName: String) {
        favoriteDrinkDao.removeFromFavorites(FavoriteDrink(drinkName))
    }

    fun isDrinkFavorite(drinkName: String): Flow<Boolean> {
        return favoriteDrinkDao.isFavorite(drinkName)
    }
}