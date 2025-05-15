package com.example.aplikacjadrinkowa

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.compose.material3.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.runtime.saveable.rememberSaveable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrinkAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background // ⬅️ Tło aplikacji
                ) {
                    DrinkAppContent()
                }
            }
        }
    }
}

private val PrimaryColor = Color(0xFFFF9F0D)      // główny pomarańcz
private val OnPrimaryColor = Color.White          // biały tekst na przyciskach
private val SecondaryColor = Color(0xFFC77600)    // ciemniejszy pomarańcz / bursztyn
private val TertiaryColor = Color(0xFFFFE0B2)      // ciepły beż / złoty akcent
private val BackgroundColor = Color(0xFFFFF8F0)    // bardzo jasny ciepły krem
private val SurfaceColor = Color.White             // biel dla kart/dialogów
private val OnSurfaceColor = Color(0xFF333333)     // ciemny szary tekst

@Composable
private fun DrinkAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    //ciemny motyw:
    val darkColors = darkColorScheme(
        primary   = PrimaryColor,   // zachowujemy pomarańcz
        onPrimary = OnPrimaryColor,
        secondary = SecondaryColor,
        tertiary  = TertiaryColor,
        background = Color(0xFF1E1E1E),
        surface    = Color(0xFF1E1E1E),
        onSurface  = Color(0xFFEEEEEE),
    )
    //jasny motyw:
    val lightColors = lightColorScheme(
        primary   = PrimaryColor,
        onPrimary = OnPrimaryColor,
        secondary = SecondaryColor,
        tertiary  = TertiaryColor,
        background = BackgroundColor,
        surface    = SurfaceColor,
        onSurface  = OnSurfaceColor
    )

    MaterialTheme(
        colorScheme = if (darkTheme) darkColors else lightColors,
        typography = Typography(
            titleLarge = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            bodyMedium = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp
            )
        ),
        content = content
    )
}

@Composable
private fun DrinkAppContent() {
    var selectedDrinkName by rememberSaveable { mutableStateOf<String?>(null) }
    var showRecipe by rememberSaveable { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val selectedDrink = drinks.firstOrNull { it.name == selectedDrinkName }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(searchQuery, { searchQuery = it })
        DrinkList(
            drinks = drinks.filter { it.name.contains(searchQuery, true) },
            onDrinkClick = { selectedDrinkName = it.name }
        )
    }

    selectedDrink?.let { drink ->
        DrinkDetailsDialog(
            drink = drink,
            onDismiss = { selectedDrinkName = null },
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
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                "Search",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        placeholder = { Text("Szukaj drinków...") },
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        colors = TextFieldDefaults.colors(
            focusedContainerColor   = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor   = MaterialTheme.colorScheme.primary,
            focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
        ),
        shape = RoundedCornerShape(16.dp),
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
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor   = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = drink.imageRes),
                contentDescription = drink.name,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = drink.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)) {
                    Text(
                        text = "${drink.percent}% · ${drink.description}",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2
                    )
                }
            }
        }
    }
}

@Composable
private fun DrinkDetailsDialog(drink: Drink, onDismiss: () -> Unit, onShowRecipe: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor   = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp)
            ) {
                Image(
                    painter = painterResource(id = drink.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .height(240.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = drink.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Zawartość alkoholu: ${drink.percent}%",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = drink.description,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Anuluj")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = onShowRecipe,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text("Pokaż przepis")
                    }
                }
            }
        }
    }
}

@Composable
private fun RecipeDialog(drink: Drink, onDismiss: () -> Unit) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.surface,
        textContentColor = MaterialTheme.colorScheme.onSurface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
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
    var time by rememberSaveable { mutableIntStateOf(0) }
    var isRunning by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (true) {
                delay(1000)
                time++
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = String.format("%02d:%02d", time / 60, time % 60),
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledTonalButton(
                onClick = { isRunning = !isRunning },
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = if (isRunning) TertiaryColor else MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = null
                )
                Spacer(Modifier.width(4.dp))
                Text(if (isRunning) "Pauza" else "Start")
            }

            OutlinedButton(
                onClick = { time = 0 },
                border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp)
            ) {
                Icon(Icons.Default.Refresh, null)
                Spacer(Modifier.width(4.dp))
                Text("Reset")
            }
        }
    }
}



@Parcelize
data class Drink(
    val name: String,
    val percent: Int,
    val description: String,
    val imageRes: Int,
    val ingredients: List<String>
) : Parcelable

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