package com.example.aplikacjadrinkowa.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_drinks")
data class FavoriteDrink(
    @PrimaryKey val drinkName: String
)