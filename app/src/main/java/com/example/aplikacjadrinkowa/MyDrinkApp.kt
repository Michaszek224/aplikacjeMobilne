package com.example.aplikacjadrinkowa

import android.app.Application
import com.example.aplikacjadrinkowa.data.DrinkRepository
import com.example.aplikacjadrinkowa.database.AppDatabase

class MyDrinkApp : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { DrinkRepository(drinks, database.favoriteDrinkDao()) }
}