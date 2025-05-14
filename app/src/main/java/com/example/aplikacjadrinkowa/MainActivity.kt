package com.example.aplikacjadrinkowa


import androidx.compose.material3.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrinkAppTheme {
                DrinkAppContent()
            }
        }
    }
}

@Composable
private fun DrinkAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF1EB980),
            secondary = Color(0xFF98FF98),
            tertiary = Color(0xFF7BC678),
            background = Color(0xFFDDFFEE)
        ),
        content = content
    )
}

@Composable
private fun DrinkAppContent() {
    var selectedDrink by remember { mutableStateOf<Drink?>(null) }
    var showRecipe by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(searchQuery, { searchQuery = it })
        DrinkList(
            drinks = drinks.filter { it.name.contains(searchQuery, true) },
            onDrinkClick = { selectedDrink = it }
        )
    }

    selectedDrink?.let { drink ->
        DrinkDetailsDialog(
            drink = drink,
            onDismiss = { selectedDrink = null },
            onShowRecipe = { showRecipe = true }
        )
    }

    if (showRecipe) {
        selectedDrink?.let { drink ->
            RecipeDialog(
                drink = drink,
                onDismiss = { showRecipe = false }
            )
        }
    }
}

@Composable
private fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        leadingIcon = { Icon(Icons.Default.Search, "Search") },
        placeholder = { Text("Wyszukaj drinka...") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
private fun DrinkList(drinks: List<Drink>, onDrinkClick: (Drink) -> Unit) {
    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
        items(drinks) { drink ->
            DrinkListItem(drink) { onDrinkClick(drink) }
        }
    }
}

@Composable
private fun DrinkListItem(drink: Drink, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = drink.imageRes),
                contentDescription = drink.name,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = drink.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "${drink.percent}% · ${drink.description.take(40)}...",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun DrinkDetailsDialog(drink: Drink, onDismiss: () -> Unit, onShowRecipe: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(drink.name) },
        text = {
            Column {
                Image(
                    painter = painterResource(id = drink.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("${drink.percent}% alkoholu")
                Spacer(modifier = Modifier.height(8.dp))
                Text(drink.description)
            }
        },
        confirmButton = {
            Button(onClick = onShowRecipe) {
                Text("Pokaż przepis")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Zamknij")
            }
        }
    )
}

@Composable
private fun RecipeDialog(drink: Drink, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Przepis na ${drink.name}") },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                drink.ingredients.forEachIndexed { index, step ->
                    Text(
                        text = "${index + 1}. $step",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                TimerComponent()
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Gotowe!")
            }
        }
    )
}

@Composable
private fun TimerComponent() {
    var time by remember { mutableIntStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(1000)
            time++
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Czas: ${time}s", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { isRunning = !isRunning },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(if (isRunning) "Pauza" else "Start")
            }
            Button(
                onClick = { time = 0 },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Icon(Icons.Default.Refresh, null)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Reset")
            }
        }
    }
}

data class Drink(
    val name: String,
    val percent: Int,
    val description: String,
    val imageRes: Int,
    val ingredients: List<String>
)

private val drinks = listOf(
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