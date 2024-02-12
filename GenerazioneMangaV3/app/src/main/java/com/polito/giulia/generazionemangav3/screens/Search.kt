package com.polito.giulia.generazionemangav3.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.polito.giulia.generazionemangav3.AppViewModel
import com.polito.giulia.generazionemangav3.FindUrl
import com.polito.giulia.generazionemangav3.database
import com.polito.giulia.generazionemangav3.ui.theme.Green
import com.polito.giulia.generazionemangav3.ui.theme.Screen
import com.polito.giulia.generazionemangav3.ui.theme.fontFamily
import java.time.format.TextStyle

@Composable
fun SearchScreen(navController: NavController,viewModel: AppViewModel) {
   // Search(viewModel)
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "search_screen") {
        composable("search_screen") {
            Search(navController = navController,viewModel)
        }
        composable("mangalist_screen") {
            DisplayListOnClick(navController = navController)
        }
        composable(route = Screen.Calendar.route) {
            MangaPageScreen(viewModel)
        }
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(navController: NavController, viewModel: AppViewModel) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                var text by remember { mutableStateOf("") }
                var active by remember { mutableStateOf(false) }

                SearchBar(
                    query = text,
                    onQueryChange = {
                        text = it
                    },
                    onSearch = {
                        active = false
                    },
                    active = active,
                    onActiveChange = {
                        active = it
                    },
                    placeholder = {
                        Text(text = "search...")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                    },
                    trailingIcon = {
                        if (active) {
                            Icon(
                                modifier = Modifier.clickable {
                                    if (text.isNotEmpty()) {
                                        text = ""
                                    } else {
                                        active = false
                                    }
                                },
                                imageVector = Icons.Default.Close,
                                contentDescription = "close"
                            )
                        }
                    }
                ) {
                    DisplayIconList(viewModel, text)
                }
            }
            var mangaListDB by remember { mutableStateOf<List<String>>(emptyList()) }
            var listaDB = database.child("manga")


            val children= database.child("manga")
            children.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (childSnapshot in dataSnapshot.children) {
                        viewModel.addManga(childSnapshot)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("Errore nel leggere i dati dal database: ${databaseError.message}")
                }
            })

            val man by viewModel.mangas.observeAsState()

            /*listaDB.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) { //fa una foto al db in quel momento e la mette in dataSnapshot
                    // Itera sui figli del nodo
                    var list = mutableListOf<String>()

                    for (childSnapshot in dataSnapshot.children) { //prende i figli di prodotti, quindi 0, 1...
                        // Aggiungi il prodotto alla lista
                        list.add(childSnapshot.value.toString())
                    }
                    mangaListDB = list
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Gestisci gli errori qui
                    println("Errore nel leggere i dati dal database: ${databaseError.message}")
                }
            })
            */
            Text(
                text = "Trending",
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Left,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(5.dp)

            ) {
                filteredList(section = "trending", viewModel = viewModel)?.forEach { p ->
                    val fileName=p.child("title").value.toString()+" Cover.jpg"
                    val url= FindUrl(fileName = fileName)
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.Start
                    ){
                        Card(onClick = { viewModel.selectedManga=p.child("title").value.toString();
                            navController.navigate(Screen.Calendar.route)},
                            modifier=Modifier.size(100.dp, 140.dp)) {
                            AsyncImage(
                                model = url,
                                contentDescription = "Manga cover",
                                contentScale = ContentScale.Crop
                            )
                        }
                            Text(
                                text = p.child("title").value.toString(),
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onPrimary,
                                textAlign = TextAlign.Center,
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.width(100.dp)
                            )

                        }
                }
                /*mangaListDB.forEach { manga ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        val sl = viewModel.mangas.observeAsState(emptyList()).value
                            .filter { it.child("title").value.toString() == manga }
                        var url = ""
                        sl.forEach { s ->
                            url = FindUrl(fileName = manga + " Cover.jpg")
                        }
                        AsyncImage(
                            model = url,
                            contentDescription = "Manga banner",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .layoutId("banner")
                                .fillMaxSize()
                                .padding(bottom = 200.dp),
                            alpha = 0.5F
                        )*/
                        /*
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    modifier = Modifier.size(80.dp),
                    contentDescription = "manga1"
                )
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    modifier = Modifier.size(80.dp),
                    contentDescription = "manga2"
                )
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    modifier = Modifier.size(80.dp),
                    contentDescription = "manga3"
                )
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    modifier = Modifier.size(80.dp),
                    contentDescription = "manga4"
                )
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    modifier = Modifier.size(80.dp),
                    contentDescription = "manga5"
                )*/

                    }
                    Text(
                        text = "Recommeded by the community",
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Left,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold
                    )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)

            ) {
                filteredList(section = "recommended", viewModel = viewModel)?.forEach { p ->
                    val fileName=p.child("title").value.toString()+" Cover.jpg"
                    val url= FindUrl(fileName = fileName)
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Card(onClick = { viewModel.selectedManga=p.child("title").value.toString();
                            navController.navigate(Screen.Calendar.route)},
                            modifier=Modifier.size(100.dp, 140.dp)) {
                            AsyncImage(
                                model = url,
                                contentDescription = "Manga cover",
                                contentScale = ContentScale.Crop
                            )
                        }
                        Text(
                            text = p.child("title").value.toString(),
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Button(
                            onClick = { navController.navigate("mangalist_screen") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiary
                            ),
                        ) {
                            Text(
                                text = "See all",
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }
    //}
//}

@Composable
fun DisplayIconList(viewModel: AppViewModel, searchText: String) {

    val mangaList = listOf(
        "Manga 1",
        "Manga 2",
        "Manga 3",
        "Manga 4"
    )

    //val mangaListDB= database.child("manga")
    var mangaListDB by remember { mutableStateOf<List<String>>(emptyList()) }
    var listaDB = database.child("manga")
    listaDB.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) { //fa una foto al db in quel momento e la mette in dataSnapshot
            // Itera sui figli del nodo
            var list= mutableListOf<String>()

            for (childSnapshot in dataSnapshot.children) { //prende i figli di prodotti, quindi 0, 1...
                // Aggiungi il prodotto alla lista
                list.add(childSnapshot.value.toString())
            }
            mangaListDB=list
        }
        override fun onCancelled(databaseError: DatabaseError) {
            // Gestisci gli errori qui
            println("Errore nel leggere i dati dal database: ${databaseError.message}")
        }
    })

    var favouriteList by remember { mutableStateOf<List<String>>(emptyList()) }
    var favourites = database.child("users").child("1").child("favourites")
    favourites.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) { //fa una foto al db in quel momento e la mette in dataSnapshot
            // Itera sui figli del nodo
            var list= mutableListOf<String>()

            for (childSnapshot in dataSnapshot.children) { //prende i figli di prodotti, quindi 0, 1...
                // Aggiungi il prodotto alla lista
                list.add(childSnapshot.value.toString())
            }
            favouriteList=list
        }
        override fun onCancelled(databaseError: DatabaseError) {
            // Gestisci gli errori qui
            println("Errore nel leggere i dati dal database: ${databaseError.message}")
        }
    })

    var shelfList by remember { mutableStateOf<List<String>>(emptyList()) }
    var shelf= database.child("users").child("1").child("favourites")
    shelf.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) { //fa una foto al db in quel momento e la mette in dataSnapshot
            // Itera sui figli del nodo
            var list= mutableListOf<String>()

            for (childSnapshot in dataSnapshot.children) { //prende i figli di prodotti, quindi 0, 1...
                // Aggiungi il prodotto alla lista
                list.add(childSnapshot.value.toString())
            }
            shelfList=list
        }
        override fun onCancelled(databaseError: DatabaseError) {
            // Gestisci gli errori qui
            println("Errore nel leggere i dati dal database: ${databaseError.message}")
        }
    })

    var filledHeart by remember { mutableStateOf(false) }
    var filledPlus by remember { mutableStateOf(false) }
    var man = viewModel.selectedManga

    // Filtra la lista dei manga in base al testo di ricerca
    val filteredMangaList = mangaListDB.filter { it.contains(searchText, ignoreCase = true) }

    // Mostra solo i manga che corrispondono al testo di ricerca
    Column {

    filteredMangaList.forEach { manga ->
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val sl = viewModel.mangas.observeAsState(emptyList()).value
                .filter { it.child("title").value.toString() == manga }
            var url = ""
            sl.forEach { s ->
                url = FindUrl(fileName = manga + " Cover.jpg")
            }
            AsyncImage(
                model = url,
                contentDescription = "Manga banner",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .layoutId("banner")
                    .fillMaxSize()
                    .padding(bottom = 200.dp),
                alpha = 0.5F
            )
            /* Icon(
                imageVector = Icons.Filled.AccountBox,
                modifier = Modifier.size(80.dp),
                contentDescription = "manga1"
            )*/
            Text(text = manga)
            // Aggiungi qui i pulsanti o le azioni per i manga
            IconButton(
                onClick = {
                    filledPlus = !filledPlus
                    if (filledPlus) {
                        addToShelf(man, "1")
                    } else
                        removeFromShelf(man, "1")
                },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                if (filledPlus == false) {
                    Icon(
                        imageVector = Icons.Outlined.AddCircle,
                        contentDescription = "Empty Plus",
                        modifier = Modifier.size(30.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Filled Plus",
                        modifier = Modifier.size(30.dp),
                        tint = Green
                    )
                }
            }
            IconButton(
                onClick = {
                    filledHeart = !filledHeart
                    if (filledHeart) {
                        addToShelf(man, "1")
                    } else
                        removeFromShelf(man, "1")
                },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                if (filledHeart == false) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "Empty Heart",
                        modifier = Modifier.size(30.dp),
                        tint = MaterialTheme.colorScheme.tertiaryContainer
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = "Filled Heart",
                        modifier = Modifier.size(30.dp),
                        tint = MaterialTheme.colorScheme.tertiaryContainer
                    )
                }
            }
        }
    }
    }
}

/*
   // var man = viewModel.selectedManga
    val sl= viewModel.mangas.observeAsState(emptyList()).value
        //.filter { it.child("title").value.toString() == man }

*/


@Composable
fun DisplayListOnClick(navController: NavController) {

    var filledHeart by remember { mutableStateOf(false) }
    var filledPlus by  remember { mutableStateOf(false) }


    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.primary)
        .padding(top = 80.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconButton(onClick = {navController.navigate("search_screen") },
                colors = IconButtonDefaults.iconButtonColors(contentColor = androidx.compose.material3.MaterialTheme.colorScheme.tertiary)
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
            ) {
                // Replace this with your list of icons or any other UI elements you want to display
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountBox,
                        modifier = Modifier.size(80.dp),
                        contentDescription = "manga1"
                    )
                    Text(
                        text = "Manga 1",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = fontFamily
                    )
                    IconButton(
                        onClick = {
                            filledPlus = !filledPlus
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ) {
                        if (filledPlus == false) {
                            Icon(
                                imageVector = Icons.Outlined.AddCircle,
                                contentDescription = "Empty Plus",
                                modifier = Modifier.size(30.dp),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = "Filled Plus",
                                modifier = Modifier.size(30.dp),
                                tint = Green
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            filledHeart = !filledHeart
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ) {
                        if (filledHeart == false) {
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = "Empty Heart",
                                modifier = Modifier.size(30.dp),
                                tint = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.Favorite,
                                contentDescription = "Filled Heart",
                                modifier = Modifier.size(30.dp),
                                tint = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        }
                    }
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountBox,
                        modifier = Modifier.size(80.dp),
                        contentDescription = "manga2"
                    )
                    Text(
                        text = "Manga 2 ",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = fontFamily
                    )
                    IconButton(
                        onClick = {
                            filledPlus = !filledPlus
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ) {
                        if (filledPlus == false) {
                            Icon(
                                imageVector = Icons.Outlined.AddCircle,
                                contentDescription = "Empty Plus",
                                modifier = Modifier.size(30.dp),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = "Filled Plus",
                                modifier = Modifier.size(30.dp),
                                tint = Green
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            filledHeart = !filledHeart
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ) {
                        if (filledHeart == false) {
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = "Empty Heart",
                                modifier = Modifier.size(30.dp),
                                tint = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.Favorite,
                                contentDescription = "Filled Heart",
                                modifier = Modifier.size(30.dp),
                                tint = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        }
                    }
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountBox,
                        modifier = Modifier.size(80.dp),
                        contentDescription = "manga3"
                    )
                    Text(
                        text = "Manga 3 ",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = fontFamily
                    )
                    IconButton(
                        onClick = {
                            filledPlus = !filledPlus
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ) {
                        if (filledPlus == false) {
                            Icon(
                                imageVector = Icons.Outlined.AddCircle,
                                contentDescription = "Empty Plus",
                                modifier = Modifier.size(30.dp),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = "Filled Plus",
                                modifier = Modifier.size(30.dp),
                                tint = Green
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            filledHeart = !filledHeart
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ) {
                        if (filledHeart == false) {
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = "Empty Heart",
                                modifier = Modifier.size(30.dp),
                                tint = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.Favorite,
                                contentDescription = "Filled Heart",
                                modifier = Modifier.size(30.dp),
                                tint = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        }
                    }
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountBox,
                        modifier = Modifier.size(80.dp),
                        contentDescription = "manga4"
                    )
                    Text(
                        text = "Manga 4 ",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = fontFamily
                    )
                    IconButton(
                        onClick = {
                            filledPlus = !filledPlus
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ) {
                        if (filledPlus == false) {
                            Icon(
                                imageVector = Icons.Outlined.AddCircle,
                                contentDescription = "Empty Plus",
                                modifier = Modifier.size(30.dp),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = "Filled Plus",
                                modifier = Modifier.size(30.dp),
                                tint = Green
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            filledHeart = !filledHeart
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ) {
                        if (filledHeart == false) {
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = "Empty Heart",
                                modifier = Modifier.size(30.dp),
                                tint = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.Favorite,
                                contentDescription = "Filled Heart",
                                modifier = Modifier.size(30.dp),
                                tint = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun filteredList(section: String, viewModel: AppViewModel): List<DataSnapshot>{
    return viewModel.mangas.observeAsState(emptyList()).value
        .filter { it.child("section").value.toString() == section }
}