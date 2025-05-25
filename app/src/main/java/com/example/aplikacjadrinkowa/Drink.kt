package com.example.aplikacjadrinkowa

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Drink(
    // Dodajemy ID dla łatwiejszej identyfikacji, chociaż nazwa jest tu kluczem w bazie ulubionych
    val id: Int, // Możesz użyć UUID.randomUUID().toString() dla unikalnych stringów
    val name: String,
    val percent: Int,
    val description: String,
    val imageRes: Int,
    val ingredients: List<String>,
    var isFavorite: Boolean = false // Ten stan będzie teraz odzwierciedlał dane z bazy
) : Parcelable

// Zaktualizuj listę drinks, dodając ID
// Możesz użyć `System.identityHashCode(this)` lub po prostu kolejne liczby
val drinks = listOf(
    Drink(
        1, // ID
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
        2,
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
    // ... dodaj ID do pozostałych drinków
    Drink(
        3,
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
        4,
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
        5,
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
        6,
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
        7,
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
        8,
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
        9,
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
        10,
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