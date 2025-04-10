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
                        "Klasyczny i orzeźwiający koktajl na bazie tequili, charakteryzujący się idealną równowagą słodyczy likieru pomarańczowego, kwaskowości soku z limonki i mocnego smaku tequili. Podawany tradycyjnie w kieliszku z solnym brzegiem, jest symbolem meksykańskiej fiesty i popularnym wyborem na całym świecie.",
                        R.drawable.rum,
                        listOf(
                            "Zwilż brzeg kieliszka do margarity plasterkiem limonki.",
                            "Wysyp sól na mały talerzyk i obtocz w niej brzeg kieliszka.",
                            "Do shakera wypełnionego lodem wlej 40 ml tequili, 20 ml likieru pomarańczowego i 20 ml soku z limonki.",
                            "Mocno wstrząśnij przez około 15-20 sekund, aż shaker będzie dobrze schłodzony.",
                            "Przelej zawartość shakera do przygotowanego kieliszka z lodem (opcjonalnie bez lodu).",
                            "Dekoruj plasterkiem limonki."
                        )
                    ),
                    Drink(
                        "Mojito",
                        14,
                        "Pochodzący z Kuby, orzeźwiający drink łączący biały rum z intensywnym aromatem świeżej mięty i kwaskowością limonki. Cukier i woda gazowana dopełniają całości, tworząc idealny napój na gorące dni. Jego prostota i wyrazisty smak sprawiają, że jest ulubieńcem barmanów i miłośników koktajli na całym świecie.",
                        R.drawable.rum,
                        listOf(
                            "Do wysokiej szklanki wrzuć pół limonki pokrojonej na ćwiartki oraz kilka listków mięty.",
                            "Dodaj 2 łyżeczki cukru (lub syropu cukrowego).",
                            "Delikatnie ugnieć limonkę z miętą i cukrem muddlerem, aby uwolnić soki i aromat mięty.",
                            "Wypełnij szklankę lodem do pełna.",
                            "Wlej 40 ml białego rumu.",
                            "Dopełnij wodą gazowaną.",
                            "Delikatnie zamieszaj długą łyżką.",
                            "Dekoruj gałązką mięty i plasterkiem limonki."
                        )
                    ),
                    Drink(
                        "Old Fashioned",
                        40,
                        "Uważany za jeden z najstarszych i najbardziej szanowanych klasycznych koktajli. Jego wyrafinowany charakter opiera się na prostocie połączenia bourbonu (lub whisky żytniej) z subtelną słodyczą cukru, aromatem angostury bitters i odświeżającą nutą skórki pomarańczy. To drink dla koneserów, ceniących głębię smaku i tradycję.",
                        R.drawable.rum,
                        listOf(
                            "Do niskiej, grubej szklanki (tzw. old fashioned glass) włóż kostkę cukru.",
                            "Skrop kostkę cukru kilkoma kroplami angostury bitters.",
                            "Dodaj odrobinę wody (około łyżeczki).",
                            "Rozgnieć cukier z bittersem muddlerem, aż cukier się rozpuści.",
                            "Dodaj dużą kostkę lodu (lub kilka mniejszych).",
                            "Wlej 40 ml bourbonu.",
                            "Mieszaj delikatnie barową łyżką przez około 20-30 sekund, schładzając drink.",
                            "Wyciśnij olejek ze skórki pomarańczy nad drinkiem, a następnie wrzuć skórkę do szklanki (opcjonalnie)."
                        )
                    ),
                    Drink(
                        "Negroni",
                        24,
                        "Elegancki włoski aperitif o wyrazistym, gorzko-ziołowym smaku. Połączenie ginu, Campari i czerwonego wermutu w równych proporcjach tworzy złożony profil smakowy, idealny do pobudzenia apetytu przed posiłkiem. Jego intensywny kolor i prostota przygotowania czynią go popularnym wyborem w barach na całym świecie.",
                        R.drawable.rum,
                        listOf(
                            "Do szklanki typu old fashioned wypełnionej lodem wlej 30 ml ginu.",
                            "Dodaj 30 ml Campari.",
                            "Wlej 30 ml czerwonego wermutu.",
                            "Delikatnie zamieszaj barową łyżką przez około 15-20 sekund, aby schłodzić drink.",
                            "Dekoruj plasterkiem pomarańczy."
                        )
                    ),
                    Drink(
                        "Pina Colada",
                        13,
                        "Słodki i kremowy tropikalny koktajl, który przenosi myślami na słoneczne plaże. Bazuje na białym rumie, bogatym mleku kokosowym i orzeźwiającym soku ananasowym. Zblendowany z lodem tworzy gładką, aksamitną konsystencję. Często dekorowany plasterkiem ananasa i wisienką maraschino, jest kwintesencją wakacyjnego relaksu.",
                        R.drawable.rum,
                        listOf(
                            "Do blendera wrzuć kilka kostek lodu.",
                            "Wlej 40 ml białego rumu.",
                            "Dodaj 40 ml mleka kokosowego.",
                            "Wlej 100 ml soku ananasowego.",
                            "Blenduj do uzyskania gładkiej, kremowej konsystencji.",
                            "Przelej do wysokiej szklanki.",
                            "Dekoruj plasterkiem ananasa i/lub wisienką maraschino."
                        )
                    ),
                    Drink(
                        "Manhattan",
                        30,
                        "Wyrafinowany i mocny klasyczny drink, którego sercem jest whisky żytnia, uzupełniona słodyczą czerwonego wermutu i aromatyczną nutą angostury bitters. Podawany w schłodzonym kieliszku koktajlowym, często ozdobiony wisienką koktajlową, jest symbolem elegancji i wyrafinowanego gustu. Idealny na spokojny wieczór.",
                        R.drawable.rum,
                        listOf(
                            "Do szklanki miksującej wypełnionej lodem wlej 50 ml whisky żytniej.",
                            "Dodaj 20 ml czerwonego wermutu.",
                            "Wlej kilka kropel angostury bitters.",
                            "Mieszaj delikatnie barową łyżką przez około 20-30 sekund, aby dobrze schłodzić drink.",
                            "Przelej do schłodzonego kieliszka koktajlowego.",
                            "Dekoruj wisienką koktajlową (opcjonalnie z ogonkiem)."
                        )
                    ),
                    Drink(
                        "Mai Tai",
                        26,
                        "Egzotyczny i bogaty w smaku drink tiki, który przenosi w świat polinezyjskich przygód. Łączy w sobie ciemny rum z orzeźwiającym sokiem z limonki, słodyczą likieru pomarańczowego i charakterystycznym aromatem syropu migdałowego (orgeat). Podawany na kruszonym lodzie i bogato dekorowany owocami i miętą, jest prawdziwą ucztą dla zmysłów.",
                        R.drawable.rum,
                        listOf(
                            "Do shakera wypełnionego lodem wlej 40 ml ciemnego rumu.",
                            "Dodaj 20 ml soku z limonki.",
                            "Wlej 15 ml likieru pomarańczowego (np. Curaçao).",
                            "Dodaj 10 ml syropu migdałowego (Orgeat).",
                            "Mocno wstrząśnij przez około 15-20 sekund.",
                            "Przelej (bez lodu ze shakera) do szklanki typu tiki lub old fashioned wypełnionej kruszonym lodem.",
                            "Dekoruj plasterkiem ananasa, gałązką mięty i/lub wisienką maraschino."
                        )
                    ),
                    Drink(
                        "Cosmopolitan",
                        27,
                        "Stylowy i lekko kwaskowy koktajl na bazie wódki cytrynowej, wzbogacony smakiem likieru pomarańczowego, żurawiny i świeżej limonki. Jego różowy kolor i elegancki kieliszek sprawiają, że jest popularnym wyborem na wieczorne wyjścia. Znany z serialu \"Seks w wielkim mieście\", stał się ikoną nowoczesnej kultury koktajlowej.",
                        R.drawable.rum,
                        listOf(
                            "Zwilż brzeg kieliszka koktajlowego plasterkiem limonki.",
                            "Opcjonalnie obtocz brzeg w cukrze (drobny cukier na talerzyku).",
                            "Do shakera wypełnionego lodem wlej 40 ml wódki cytrynowej (można użyć zwykłej wódki).",
                            "Dodaj 15 ml likieru pomarańczowego (np. Cointreau lub Triple Sec).",
                            "Wlej 30 ml soku żurawinowego (niesłodzonego).",
                            "Dodaj 10 ml świeżo wyciśniętego soku z limonki.",
                            "Mocno wstrząśnij przez około 15-20 sekund, aż shaker będzie dobrze schłodzony.",
                            "Przelej zawartość shakera do przygotowanego kieliszka koktajlowego bez lodu.",
                            "Dekoruj skórką pomarańczy wyciśniętą nad drinkiem."
                        )
                    ),
                    Drink(
                        "Long Island Iced Tea",
                        22,
                        "Mocny i zdradliwie orzeźwiający koktajl, który mimo swojej nazwy nie zawiera herbaty. Jest to potężna mieszanka wódki, białego rumu, ginu i tequili blanco, z dodatkiem likieru pomarańczowego i coli, która nadaje mu barwę podobną do herbaty. Ze względu na wysoką zawartość alkoholu, należy go spożywać z umiarem.",
                        R.drawable.rum,
                        listOf(
                            "Do wysokiej szklanki wypełnionej lodem wlej po 20 ml wódki, białego rumu, ginu i tequili blanco.",
                            "Dodaj 20 ml likieru pomarańczowego (np. Triple Sec).",
                            "Dopełnij colą (około 100-120 ml), pozostawiając trochę miejsca na górze.",
                            "Delikatnie zamieszaj długą łyżką.",
                            "Dekoruj plasterkiem cytryny."
                        )
                    ),
                    Drink(
                        "Zombie",
                        40,
                        "Legendarny i bardzo mocny drink tiki, znany ze swojej złożonej receptury i wysokiej zawartości alkoholu. Łączy w sobie różne rodzaje rumu, likiery, soki owocowe i przyprawy, tworząc tajemniczy i egzotyczny smak. Jego nazwa i moc sprawiły, że stał się kultowym wyborem dla poszukiwaczy mocnych wrażeń.",
                        R.drawable.rum,
                        listOf(
                            "Do shakera wypełnionego lodem wlej 30 ml jasnego rumu.",
                            "Dodaj 30 ml ciemnego rumu.",
                            "Wlej 20 ml świeżo wyciśniętego soku z limonki.",
                            "Dodaj 20 ml soku pomarańczowego.",
                            "Wlej 10 ml grenadiny.",
                            "Dodaj szczyptę cynamonu.",
                            "Mocno wstrząśnij przez około 20-30 sekund.",
                            "Przelej (bez lodu ze shakera) do wysokiej szklanki wypełnionej kruszonym lodem.",
                            "Dekoruj plasterkiem pomarańczy, wisienką maraschino i/lub gałązką mięty."
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
                .padding(16.dp),
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
                Text(text = drink.nazwa, style = MaterialTheme.typography.headlineMedium)
                Text(text = "${drink.procenty}% alkoholu", style = MaterialTheme.typography.bodyMedium)
                Text(text = drink.opis)
                Spacer(modifier = Modifier.height(8.dp))
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
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFDDEEDD)),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Składniki:", style = MaterialTheme.typography.titleLarge)
                drink.skladniki.forEach { skladnik ->
                    Text("- $skladnik")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Minutnik()
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7BC678)) ) { Text("Zamknij") }
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
