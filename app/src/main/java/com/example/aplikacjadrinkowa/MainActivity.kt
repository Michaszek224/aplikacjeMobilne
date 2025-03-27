package com.example.aplikacjadrinkowa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val drinks = listOf(
                Drink("Woda", 0),
                Drink("Wóda", 40),
                Drink("Piwo", 5),
                Drink("Whisky", 43),
                Drink("Rum", 37)
            )
            ListaDrinkow(drinks)
        }
    }
}

data class Drink(val nazwa: String, val procenty: Int)

@Composable
fun ListaDrinkow(drinki: List<Drink>) {
    val spaceSize = (40 - drinki.size * 5).coerceIn(4, 24).dp

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(spaceSize)
    ) {
        items(drinki) { drink ->
            MojDrink(drink)
        }
    }
}

@Composable
fun MojDrink(drink: Drink) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = drink.nazwa)
        Text(text = "${drink.procenty}%")
    }
}
