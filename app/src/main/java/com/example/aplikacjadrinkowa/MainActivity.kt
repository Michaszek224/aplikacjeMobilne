package com.example.aplikacjadrinkowa

// Importy ... (dodaj te, które będą potrzebne)
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.* // Menu, Favorite, PlayArrow, Pause, Refresh, Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikacjadrinkowa.uii.DrinkListType
import com.example.aplikacjadrinkowa.uii.DrinkViewModelFactory
import com.example.aplikacjadrinkowa.uii.DrinkViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Uzyskanie ViewModelu
            val app = LocalContext.current.applicationContext as MyDrinkApp
            val viewModel: DrinkViewModel = viewModel(
                factory = DrinkViewModelFactory(app.repository)
            )
            DrinkAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DrinkAppScaffold(viewModel)
                }
            }
        }
    }
}


private val PrimaryColor = Color(0xFFFF9F0D)      // główny pomarańcz
private val OnPrimaryColor = Color.White          // biały tekst na przyciskach
private val SecondaryColor = Color(0xFFC77600)    // ciemniejszy pomarańcz
private val TertiaryColor = Color(0xFFFFE0B2)      // złoty akcent
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
        primary   = PrimaryColor,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrinkAppScaffold(viewModel: DrinkViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    var selectedDrinkForDetails by rememberSaveable { mutableStateOf<Drink?>(null) }
    var showRecipeDialog by rememberSaveable { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                NavigationDrawerItem(
                    icon = { Icon(Icons.Filled.List, contentDescription = "Wszystkie drinki") },
                    label = { Text("Wszystkie drinki") },
                    selected = uiState.currentListType == DrinkListType.ALL,
                    onClick = {
                        viewModel.showAllDrinks()
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = "Ulubione drinki") },
                    label = { Text("Ulubione drinki") },
                    selected = uiState.currentListType == DrinkListType.FAVORITES,
                    onClick = {
                        viewModel.showFavoriteDrinks()
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            when (uiState.currentListType) {
                                DrinkListType.ALL -> "Wszystkie Drinki"
                                DrinkListType.FAVORITES -> "Ulubione Drinki"
                            }
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        ) { paddingValues ->
            DrinkAppContent(
                modifier = Modifier.padding(paddingValues),
                viewModel = viewModel,
                onDrinkClick = { drink -> selectedDrinkForDetails = drink },
            )

            // Dialogi poza głównym contentem, aby były na wierzchu
            selectedDrinkForDetails?.let { drink ->
                DrinkDetailsDialog(
                    drink = drink, // Przekazujemy aktualny stan drinka z UI state
                    onDismiss = { selectedDrinkForDetails = null },
                    onShowRecipe = { showRecipeDialog = true }
                )
            }

            if (showRecipeDialog && selectedDrinkForDetails != null) {
                RecipeDialog(
                    drink = selectedDrinkForDetails!!,
                    onDismiss = { showRecipeDialog = false }
                )
            }
        }
    }
}


@Composable
private fun DrinkAppContent(
    modifier: Modifier = Modifier,
    viewModel: DrinkViewModel,
    onDrinkClick: (Drink) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        SearchBar(
            query = uiState.searchQuery,
            onQueryChange = { viewModel.setSearchQuery(it) }
        )
        if (uiState.displayedDrinks.isEmpty() && uiState.searchQuery.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
                Text("Brak drinków pasujących do wyszukiwania.")
            }
        } else if (uiState.displayedDrinks.isEmpty() && uiState.currentListType == DrinkListType.FAVORITES) {
            Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
                Text("Nie masz jeszcze żadnych ulubionych drinków.")
            }
        }
        else {
            DrinkList(
                drinks = uiState.displayedDrinks,
                onDrinkClick = onDrinkClick,
                onToggleFavorite = { drink -> viewModel.toggleFavorite(drink) }
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
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
private fun DrinkList(
    drinks: List<Drink>,
    onDrinkClick: (Drink) -> Unit,
    onToggleFavorite: (Drink) -> Unit
) {
    var isListVisible by remember { mutableStateOf(false) }

    LaunchedEffect(drinks) {
        isListVisible = true
    }

    AnimatedVisibility(
        visible = isListVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 0.dp)
        ) {
            items(drinks, key = { it.id }) { drink ->
                DrinkListItem(
                    drink = drink,
                    onClick = { onDrinkClick(drink) },
                    onToggleFavorite = { onToggleFavorite(drink) }
                )
            }
        }
    }
}

@Composable
private fun DrinkListItem(
    drink: Drink,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (pressed) 0.98f else 1f, label = "CardScaleAnimation")

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        pressed = true
                        tryAwaitRelease()
                        pressed = false
                    },
                )
            },
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
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
            IconButton(onClick = onToggleFavorite) {
                AnimatedContent(
                    targetState = drink.isFavorite,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(durationMillis = 200)) togetherWith
                                fadeOut(animationSpec = tween(durationMillis = 200))
                    },
                    label = "FavoriteIconAnimation"
                ) { targetState ->
                    Icon(
                        imageVector = if (targetState) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (targetState) "Remove from Favorites" else "Add to Favorites",
                        tint = PrimaryColor
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
            IconButton(onClick = { isRunning = !isRunning }) {
                AnimatedContent(
                    targetState = isRunning,
                    label = "PlayPauseIconAnimation"
                ) { targetIsRunning ->
                    Icon(
                        imageVector = if (targetIsRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (targetIsRunning) "Pause" else "Play",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            IconButton(onClick = {
                time = 0
                isRunning = false
            }) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Reset",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
