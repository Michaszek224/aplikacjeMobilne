package com.example.aplikacjadrinkowa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFDDFFEE)),
            )   {
                val drinks = listOf(
                    Drink(
                        "Margarita",
                        33,
                        "Klasyczny koktajl na bazie tequili z likierem pomarańczowym i limonką. Symbol meksykańskiej fiesty.",
                        R.drawable.marg,
                        listOf(
                            "Zwilż brzeg kieliszka limonką, obtocz solą.",
                            "W shakerze z lodem zmieszaj tequilę, likier i sok z limonki.",
                            "Wstrząśnij, przelej do kieliszka z lodem.",
                            "Dekoruj limonką."
                        )
                    ),
                    Drink(
                        "Mojito",
                        14,
                        "Orzeźwiający kubański drink z białym rumem, miętą i limonką. Idealny na gorące dni.",
                        R.drawable.moijto,
                        listOf(
                            "W szklance ugnieć limonkę z miętą i cukrem.",
                            "Dodaj lód, rum i dopełnij wodą gazowaną.",
                            "Zamieszaj, udekoruj miętą i limonką."
                        )
                    ),
                    Drink(
                        "Old Fashioned",
                        40,
                        "Klasyczny koktajl z bourbonu (lub whisky żytniej), cukru i angostury bitters. Dla koneserów głębi smaku.",
                        R.drawable.old,
                        listOf(
                            "W szklance rozpuść cukier z bittersem i wodą.",
                            "Dodaj lód i bourbon.",
                            "Mieszaj, udekoruj skórką pomarańczy."
                        )
                    ),
                    Drink(
                        "Negroni",
                        24,
                        "Włoski aperitif o gorzko-ziołowym smaku z ginu, Campari i czerwonego wermutu. Pobudza apetyt.",
                        R.drawable.negr,
                        listOf(
                            "W szklance z lodem zmieszaj gin, Campari i wermut.",
                            "Delikatnie zamieszaj.",
                            "Dekoruj plasterkiem pomarańczy."
                        )
                    ),
                    Drink(
                        "Pina Colada",
                        13,
                        "Słodki tropikalny koktajl z białego rumu, mleka kokosowego i soku ananasowego. Wakacyjny relaks.",
                        R.drawable.pina,
                        listOf(
                            "Zblenduj lód, rum, mleko kokosowe i sok ananasowy.",
                            "Przelej do szklanki.",
                            "Dekoruj ananasem i wisienką."
                        )
                    ),
                    Drink(
                        "Manhattan",
                        30,
                        "Wyrafinowany drink z whisky żytniej, czerwonego wermutu i angostury bitters. Symbol elegancji.",
                        R.drawable.manh,
                        listOf(
                            "W szklance z lodem zmieszaj whisky, wermut i bitters.",
                            "Mieszaj, przelej do kieliszka.",
                            "Dekoruj wisienką."
                        )
                    ),
                    Drink(
                        "Mai Tai",
                        26,
                        "Egzotyczny drink tiki z ciemnego rumu, limonki, likieru pomarańczowego i syropu migdałowego. Uczta dla zmysłów.",
                        R.drawable.maintai,
                        listOf(
                            "W shakerze z lodem zmieszaj rum, sok z limonki, likier i syrop.",
                            "Wstrząśnij, przelej na kruszony lód.",
                            "Dekoruj owocami i miętą."
                        )
                    ),
                    Drink(
                        "Cosmopolitan",
                        27,
                        "Stylowy koktajl z wódki cytrynowej, likieru pomarańczowego, żurawiny i limonki. Ikona kultury koktajlowej.",
                        R.drawable.cosmo,
                        listOf(
                            "Opcjonalnie obtocz brzeg kieliszka cukrem.",
                            "W shakerze z lodem zmieszaj wódkę, likier, sok żurawinowy i limonkę.",
                            "Wstrząśnij, przelej do kieliszka.",
                            "Dekoruj skórką pomarańczy."
                        )
                    ),
                    Drink(
                        "Long Island Iced Tea",
                        22,
                        "Mocny koktajl bez herbaty z wódki, rumu, ginu, tequili, likieru pomarańczowego i coli. Pić z umiarem.",
                        R.drawable.icetea,
                        listOf(
                            "W wysokiej szklance z lodem zmieszaj wódkę, rum, gin i tequilę.",
                            "Dodaj likier pomarańczowy i dopełnij colą.",
                            "Delikatnie zamieszaj, udekoruj cytryną."
                        )
                    ),
                    Drink(
                        "Zombie",
                        40,
                        "Legendarny, bardzo mocny drink tiki z różnymi rumami, likierami i sokami owocowymi. Dla poszukiwaczy wrażeń.",
                        R.drawable.zombie,
                        listOf(
                            "W shakerze z lodem zmieszaj rumy, sok z limonki i pomarańczowy, grenadinę i cynamon.",
                            "Wstrząśnij, przelej na kruszony lód.",
                            "Dekoruj owocami i miętą."
                        )
                    )
                )
                ListaDrinkow(drinks)
            }
        }
    }
}

data class Drink(
    val nazwa: String,
    val procenty: Int,
    val opis: String,
    val imageRes: Int,
    val skladniki: List<String>
)

@Composable
fun ListaDrinkow(drinki: List<Drink>) {
    var selectedDrink by remember { mutableStateOf<Drink?>(null) }
    var showRecipe by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(drinki.size) { index ->
                MojDrink(drinki[index]) {
                    selectedDrink = drinki[index]
                    showRecipe = false
                }
            }
        }
    }

    selectedDrink?.let { drink ->
        DrinkDetailsDialog(drink, onDismiss = { selectedDrink = null }, onShowRecipe = { showRecipe = true })
    }

    if (showRecipe) {
        selectedDrink?.let { drink ->
            PrzepisDialog(drink) { showRecipe = false }
        }
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
fun DrinkDetailsDialog(drink: Drink, onDismiss: () -> Unit, onShowRecipe: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFDDEEDD))
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val image: Painter = painterResource(id = drink.imageRes)
                Image(
                    painter = image,
                    contentDescription = drink.nazwa,
                    modifier = Modifier.size(128.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = drink.nazwa, style = MaterialTheme.typography.headlineLarge)
                Text(text = "${drink.procenty}% alkoholu", style = MaterialTheme.typography.headlineMedium)
                Text(text = drink.opis, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.weight(1f))
                Row(verticalAlignment = Alignment.Bottom) {
                    Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7BC678)) ) { Text("Zamknij") }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = onShowRecipe, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7BC678)) ){ Text("Przepis na drink") }
                }
            }
        }
    }
}

@Composable
fun PrzepisDialog(drink: Drink, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFDDEEDD)),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Text("Składniki:", style = MaterialTheme.typography.headlineMedium)
                drink.skladniki.forEach { skladnik ->
                    Text("- $skladnik", style = MaterialTheme.typography.headlineSmall)
                }
                Spacer(modifier = Modifier.weight(1f))
                Minutnik()
                Spacer(modifier = Modifier.weight(1f))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7BC678))
                    ) {
                        Text("Zamknij")
                    }
                }
            }
        }
    }
}

@Composable
fun Minutnik() {
    var czas by rememberSaveable { mutableIntStateOf(0) }
    var isRunning by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(1000)
            czas++
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Czas: ${czas}s", style = MaterialTheme.typography.headlineSmall)
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { isRunning = true }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7BC678)) ) { Text("Start") }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { isRunning = false }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7BC678)) ) { Text("Stop") }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                isRunning = false
                czas = 0
            }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7BC678)) ) { Text("Reset") }
        }
    }
}
