package com.example.aplikacjadrinkowa.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDrinkDao {
    @Query("SELECT * FROM favorite_drinks")
    fun getFavoriteDrinks(): Flow<List<FavoriteDrink>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(favoriteDrink: FavoriteDrink)

    @Delete
    suspend fun removeFromFavorites(favoriteDrink: FavoriteDrink)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_drinks WHERE drinkName = :drinkName LIMIT 1)")
    fun isFavorite(drinkName: String): Flow<Boolean>
}