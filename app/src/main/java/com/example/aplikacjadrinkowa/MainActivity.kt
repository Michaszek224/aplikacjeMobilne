package com.example.aplikacjadrinkowa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val drinks = listOf(
                Drink("Margarita", 33, "Klasyczny koktajl na bazie tequili, likieru pomarańczowego i soku z limonki. Orzeźwiający, kwaskowaty i doskonały na gorące dni.", R.drawable.rum),
                Drink("Mojito", 14, "Kubański drink łączący rum, miętę, limonkę, cukier i wodę gazowaną. Delikatny, lekko słodki i niezwykle orzeźwiający, idealny na lato.", R.drawable.rum),
                Drink("Old Fashioned", 40, "Jeden z najstarszych klasycznych koktajli, składający się z bourbonu, cukru, bitteru i lodu. Mocny, aromatyczny i pełen charakteru.", R.drawable.rum),
                Drink("Negroni", 24, "Włoski aperitif na bazie ginu, Campari i słodkiego wermutu. Gorzki, intensywny i elegancki, idealny jako drink przed kolacją.", R.drawable.rum),
                Drink("Pina Colada", 13, "Tropikalny koktajl z rumem, mlekiem kokosowym i sokiem ananasowym. Kremowy, słodki i niezwykle przyjemny, kojarzący się z wakacjami.", R.drawable.rum),
                Drink("Manhattan", 30, "Klasyczny drink z whisky żytniej, słodkiego wermutu i bitteru. Mocny, wyrafinowany i często serwowany z wiśnią koktajlową.", R.drawable.rum),
                Drink("Mai Tai", 26, "Egzotyczny drink tiki, składający się z rumu, soku limonkowego, likieru pomarańczowego i syropu migdałowego. Bogaty w smak, intensywny i owocowy.", R.drawable.rum),
                Drink("Cosmopolitan", 27, "Stylowy koktajl z wódki, likieru pomarańczowego, soku żurawinowego i limonkowego. Orzeźwiający, lekko słodki i kwaskowaty, popularny wśród kobiet.", R.drawable.rum),
                Drink("Long Island Iced Tea", 22, "Potężna mieszanka pięciu alkoholi: wódki, rumu, ginu, tequili i likieru pomarańczowego, dopełniona colą. Zaskakująco łagodny, ale bardzo mocny.", R.drawable.rum),
                Drink("Zombie", 40, "Tiki drink z różnymi rodzajami rumu, sokami owocowymi i przyprawami. Bardzo mocny, tropikalny i niezwykle aromatyczny, podawany z dekoracją.", R.drawable.rum)

            )
            ListaDrinkow(drinks)
        }
    }
}

data class Drink(val nazwa: String, val procenty: Int, val opis: String, val imageRes: Int)

@Composable
fun ListaDrinkow(drinki: List<Drink>) {
    var selectedDrink by remember { mutableStateOf<Drink?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally // Wyśrodkowanie poziome
    ) {
        drinki.forEach { drink ->
            MojDrink(drink) { selectedDrink = drink }
        }
    }

    selectedDrink?.let { drink ->
        DrinkDetailsDialog(drink) { selectedDrink = null }
    }
}

@Composable
fun MojDrink(drink: Drink, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val image: Painter = painterResource(id = drink.imageRes)
        Image(
            painter = image,
            contentDescription = drink.nazwa,
            modifier = Modifier.size(128.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = drink.nazwa, style = MaterialTheme.typography.displayMedium)
    }
}


@Composable
fun DrinkDetailsDialog(drink: Drink, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier.padding(16.dp)) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val image: Painter = painterResource(id = drink.imageRes)
                Image(
                    painter = image,
                    contentDescription = drink.nazwa,
                    modifier = Modifier.size(128.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = drink.nazwa, style = MaterialTheme.typography.headlineMedium)
                Text(text = "${drink.procenty}% alkoholu", style = MaterialTheme.typography.bodyMedium)
                Text(text= drink.opis)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onDismiss) { Text("Zamknij") }
            }
        }
    }
}
