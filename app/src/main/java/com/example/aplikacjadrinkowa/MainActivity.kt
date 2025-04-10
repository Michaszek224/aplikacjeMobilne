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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val drinks = listOf(
                Drink(
                    "Margarita",
                    33,
                    "Klasyczny koktajl na bazie tequili, likieru pomarańczowego i soku z limonki. Orzeźwiający, kwaskowaty i doskonały na gorące dni.",
                    R.drawable.rum,
                    listOf("40 ml tequili", "20 ml likieru pomarańczowego", "20 ml soku z limonki", "Sól", "Lód")
                ),
                Drink(
                    "Mojito",
                    14,
                    "Kubański drink łączący rum, miętę, limonkę, cukier i wodę gazowaną. Delikatny, lekko słodki i niezwykle orzeźwiający, idealny na lato.",
                    R.drawable.rum,
                    listOf("40 ml białego rumu", "½ limonki", "2 łyżeczki cukru", "Kilka listków mięty", "Woda gazowana", "Lód kruszony")
                ),
                Drink(
                    "Old Fashioned",
                    40,
                    "Jeden z najstarszych klasycznych koktajli, składający się z bourbonu, cukru, bitteru i lodu. Mocny, aromatyczny i pełen charakteru.",
                    R.drawable.rum,
                    listOf("50 ml bourbonu", "1 kostka cukru", "Kilka kropel angostury", "Skórka pomarańczowa", "Lód")
                ),
                Drink(
                    "Negroni",
                    24,
                    "Włoski aperitif na bazie ginu, Campari i słodkiego wermutu. Gorzki, intensywny i elegancki, idealny jako drink przed kolacją.",
                    R.drawable.rum,
                    listOf("30 ml ginu", "30 ml Campari", "30 ml czerwonego wermutu", "Plaster pomarańczy", "Lód")
                ),
                Drink(
                    "Pina Colada",
                    13,
                    "Tropikalny koktajl z rumem, mlekiem kokosowym i sokiem ananasowym. Kremowy, słodki i niezwykle przyjemny, kojarzący się z wakacjami.",
                    R.drawable.rum,
                    listOf("40 ml białego rumu", "40 ml mleka kokosowego", "100 ml soku ananasowego", "Kostki lodu", "Plaster ananasa")
                ),
                Drink(
                    "Manhattan",
                    30,
                    "Klasyczny drink z whisky żytniej, słodkiego wermutu i bitteru. Mocny, wyrafinowany i często serwowany z wiśnią koktajlową.",
                    R.drawable.rum,
                    listOf("50 ml whisky żytniej", "20 ml czerwonego wermutu", "Kilka kropel angostury", "Wiśnia koktajlowa", "Lód")
                ),
                Drink(
                    "Mai Tai",
                    26,
                    "Egzotyczny drink tiki, składający się z rumu, soku limonkowego, likieru pomarańczowego i syropu migdałowego. Bogaty w smak, intensywny i owocowy.",
                    R.drawable.rum,
                    listOf("30 ml jasnego rumu", "30 ml ciemnego rumu", "15 ml likieru pomarańczowego", "15 ml syropu migdałowego (orgeat)", "20 ml soku z limonki", "Lód", "Plaster limonki")
                ),
                Drink(
                    "Cosmopolitan",
                    27,
                    "Stylowy koktajl z wódki, likieru pomarańczowego, soku żurawinowego i limonkowego. Orzeźwiający, lekko słodki i kwaskowaty, popularny wśród kobiet.",
                    R.drawable.rum,
                    listOf("40 ml wódki cytrynowej", "20 ml likieru pomarańczowego", "30 ml soku żurawinowego", "10 ml soku z limonki", "Lód", "Skórka z limonki")
                ),
                Drink(
                    "Long Island Iced Tea",
                    22,
                    "Potężna mieszanka pięciu alkoholi: wódki, rumu, ginu, tequili i likieru pomarańczowego, dopełniona colą. Zaskakująco łagodny, ale bardzo mocny.",
                    R.drawable.rum,
                    listOf("15 ml wódki", "15 ml ginu", "15 ml białego rumu", "15 ml tequili", "15 ml likieru pomarańczowego", "20 ml soku z cytryny", "Top colą", "Lód", "Plaster cytryny")
                ),
                Drink(
                    "Zombie",
                    40,
                    "Tiki drink z różnymi rodzajami rumu, sokami owocowymi i przyprawami. Bardzo mocny, tropikalny i niezwykle aromatyczny, podawany z dekoracją.",
                    R.drawable.rum,
                    listOf("30 ml jasnego rumu", "30 ml ciemnego rumu", "30 ml overproof rumu", "15 ml likieru morelowego", "20 ml soku limonkowego", "40 ml soku ananasowego", "15 ml syropu grenadyna", "Angostura", "Lód", "Mięta lub owoce do dekoracji")
                )
            )

            ListaDrinkow(drinks)
        }
    }
}




data class Drink(val nazwa: String, val procenty: Int, val opis: String, val imageRes: Int, val skladniki: List<String>)

@Composable
fun ListaDrinkow(drinki: List<Drink>) {
    var selectedDrink by remember { mutableStateOf<Drink?>(null) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
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
    var showRecipe by remember { mutableStateOf(false) }

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
                Text(text = drink.opis)
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Button(onClick = onDismiss, modifier = Modifier.weight(1f)) {
                        Text("Zamknij")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { showRecipe = true }, modifier = Modifier.weight(1f)) {
                        Text("Przepis na drink")
                    }
                }
            }
        }
    }

    if (showRecipe) {
        DrinkRecipeDialog(drink = drink, onDismiss = { showRecipe = false })
    }
}

@Composable
fun DrinkRecipeDialog(drink: Drink, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier.padding(16.dp)) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Przepis na ${drink.nazwa}",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                drink.skladniki.forEach { skladnik ->
                    Text(text = "• $skladnik", style = MaterialTheme.typography.bodyMedium)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onDismiss) {
                    Text("Zamknij przepis")
                }
            }
        }
    }
}
