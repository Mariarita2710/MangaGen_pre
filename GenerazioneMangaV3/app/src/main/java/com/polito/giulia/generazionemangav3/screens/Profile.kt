package com.polito.giulia.generazionemangav3.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.polito.giulia.generazionemangav3.AppViewModel

/*

@Composable
fun ProfileScreen(viewModel: AppViewModel) {
    Profile(viewModel)
}
@Composable
fun Profile(viewModel: AppViewModel, modifier: Modifier = Modifier
    .padding(top = 74.dp)
    .padding(bottom = 74.dp)){

    Column(modifier = modifier.padding(vertical = 4.dp)){
    }

}*/


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.TextStyle
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.polito.giulia.generazionemangav3.ui.theme.fontFamily
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.polito.giulia.generazionemangav3.ui.theme.Screen
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.polito.giulia.generazionemangav3.FindUrl
import com.polito.giulia.generazionemangav3.R
import com.polito.giulia.generazionemangav3.database
import com.polito.giulia.generazionemangav3.ui.theme.Violet20
import com.polito.giulia.generazionemangav3.ui.theme.Violet40


@Composable
fun ProfileScreen(navController: NavController, viewModel: AppViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "profile_screen") {
        composable("profile_screen") {
            Profile(navController = navController,viewModel)
        }
        composable("edit_screen") {
            EditProfile(viewModel,navController = navController)
        }
        composable(route = Screen.MangaPage.route) {
            MangaPageScreen(viewModel)
        }
    }
}

@Composable
fun Profile(navController: NavController,viewModel: AppViewModel,modifier: Modifier = Modifier
    .padding(top = 74.dp)
    .padding(bottom = 100.dp)
    .verticalScroll(rememberScrollState())){
    val scrollState = rememberScrollState()

    var favouriteGenresList by remember { mutableStateOf<List<String>>(emptyList()) }
    var favouritesGenres = database.child("users").child("1").child("genres")
    favouritesGenres.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) { //fa una foto al db in quel momento e la mette in dataSnapshot
            // Itera sui figli del nodo
            var list= mutableListOf<String>()

            for (childSnapshot in dataSnapshot.children) { //prende i figli di prodotti, quindi 0, 1...
                // Aggiungi il prodotto alla lista
                list.add(childSnapshot.value.toString())
            }
            favouriteGenresList=list
        }
        override fun onCancelled(databaseError: DatabaseError) {
            // Gestisci gli errori qui
            println("Errore nel leggere i dati dal database: ${databaseError.message}")
        }
    })

    var favouritesAuthorsList by remember { mutableStateOf<List<String>>(emptyList()) }
    var favouritesAuthors= database.child("users").child("1").child("authors")
    favouritesAuthors.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) { //fa una foto al db in quel momento e la mette in dataSnapshot
            // Itera sui figli del nodo
            var list= mutableListOf<String>()

            for (childSnapshot in dataSnapshot.children) { //prende i figli di prodotti, quindi 0, 1...
                // Aggiungi il prodotto alla lista
                list.add(childSnapshot.value.toString())
            }
            favouritesAuthorsList=list
        }
        override fun onCancelled(databaseError: DatabaseError) {
            // Gestisci gli errori qui
            println("Errore nel leggere i dati dal database: ${databaseError.message}")
        }
    })

    var favouritesEditorsList by remember { mutableStateOf<List<String>>(emptyList()) }
    var favouritesEditors= database.child("users").child("1").child("publishers")
    favouritesEditors.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) { //fa una foto al db in quel momento e la mette in dataSnapshot
            // Itera sui figli del nodo
            var list= mutableListOf<String>()

            for (childSnapshot in dataSnapshot.children) { //prende i figli di prodotti, quindi 0, 1...
                // Aggiungi il prodotto alla lista
                list.add(childSnapshot.value.toString())
            }
            favouritesEditorsList=list
        }
        override fun onCancelled(databaseError: DatabaseError) {
            // Gestisci gli errori qui
            println("Errore nel leggere i dati dal database: ${databaseError.message}")
        }
    })

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.primary)
        .padding(
            top = 70.dp,
            bottom = 80.dp
        )
        .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    Violet40, Violet20
                )
            )
        )
        .verticalScroll(rememberScrollState())) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProfileHeader(navController)
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
            ) {

                    Text(
                        text = " Favourite genres: " + favouriteGenresList,//dopo il più in teoria ci andrebbe tipo una funzione
                        //che fa cambiare il testo a seconda delle cose che vengono scelte quando si fa edit prof
                        //quindi da aggiungere in post
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = fontFamily,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = " Favourite authors: " + favouritesAuthorsList,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = fontFamily,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = " Favourite publishers: " + favouritesEditorsList,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = fontFamily,
                        modifier = Modifier.padding(8.dp)
                    )

            }
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
            Text(
                text = "My Favorites",
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Left,
                fontFamily= fontFamily,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(16.dp)

            ) {

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

                favouriteList?.forEach { p ->
                    val fileName=p +" Cover.jpg"
                    val url= FindUrl(fileName = fileName)
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.Start
                    ){
                        Card(onClick = { viewModel.selectedManga=p;
                            navController.navigate(Screen.MangaPage.route)},
                            modifier=Modifier.size(100.dp, 140.dp)) {
                            AsyncImage(
                                model = url,
                                contentDescription = "Manga cover",
                                contentScale = ContentScale.Crop
                            )
                        }
                        Text(
                            text = p,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.width(100.dp)
                        )

                    }
                }
            }
            Text(
                text = "My Shelf",
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Left,
                fontFamily= fontFamily,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                var shelfList by remember { mutableStateOf<List<String>>(emptyList()) }
                var shelf= database.child("users").child("1").child("shelf")
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

                shelfList?.forEach { p ->
                    val fileName=p+" Cover.jpg"
                    val url= FindUrl(fileName = fileName)
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.Start
                    ){
                        Card(onClick = { viewModel.selectedManga=p;
                            navController.navigate(Screen.MangaPage.route)},
                            modifier=Modifier.size(100.dp, 140.dp)) {
                            AsyncImage(
                                model = url,
                                contentDescription = "Manga cover",
                                contentScale = ContentScale.Crop
                            )
                        }
                        Text(
                            text = p,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.width(100.dp)
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun ProfileHeader(navController: NavController) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(120.dp)
                    .border(1.dp, MaterialTheme.colorScheme.tertiary, CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_picture),
                    contentDescription = "Profile icon",
                    contentScale = ContentScale.Crop,
                )
            }

            Column {
                Text(
                    text = "Sarah Rossi",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 24.sp,
                    fontFamily = fontFamily
                )
                Text(
                    text = "redSarah15",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 14.sp,
                    fontFamily = fontFamily
                )
            }
        }
        Button(
            onClick = { navController.navigate("edit_screen") },
            modifier = Modifier.align(Alignment.End),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            ),
        ) {
            Text("Edit favourites")
        }
    }
}

@Composable
fun EditProfile(viewModel: AppViewModel, navController: NavController) {
    var selected1 by remember { mutableStateOf(false) }
    var selected2 by remember { mutableStateOf(false) }
    var selected3 by remember { mutableStateOf(false) }
    val genres = database.child("users").child("1").child("genres")

    // val color = if (selected1 || selected2 || selected3) Color.Gray else MaterialTheme.colorScheme.tertiary
    var genre1="Drama"
    var genre2="Shonen"
    var genre3="Action"
    var author1= "Isayama"
    var author2="One"
    var author3="Urasawa"
    var editor1= "Jpop"
    var editor2="Panini"
    var editor3="Dynit"

    val buttonStates = remember {
        mapOf(
            "genre1" to mutableStateOf(false),
            "genre2" to mutableStateOf(false),
            "genre3" to mutableStateOf(false),
            "author1" to mutableStateOf(false),
            "author2" to mutableStateOf(false),
            "author3" to mutableStateOf(false),
            "editor1" to mutableStateOf(false),
            "editor2" to mutableStateOf(false),
            "editor3" to mutableStateOf(false),
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "profile pic",
                    modifier = Modifier.size(70.dp)
                )
                Column {
                    Text(
                        text = "Mario Rossi",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 24.sp
                    )
                    Text(
                        text = "redMario94",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            Text(
                text = "Favourite genres: ",
                color = MaterialTheme.colorScheme.onPrimary,
                fontFamily = fontFamily
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                /*Button(
                    onClick = { buttonStates["genre1"]?.value = !(buttonStates["genre1"]?.value ?: false)
                        addToGenres(genre1,"1") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (buttonStates["genre1"]?.value == true) Color.Gray else MaterialTheme.colorScheme.tertiary
                    )){
                    Text(genre1)
                }
                Button(
                    onClick = { buttonStates["genre2"]?.value = !(buttonStates["genre2"]?.value ?: false)
                        addToGenres(genre2,"1")},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (buttonStates["genre2"]?.value == true) Color.Gray else MaterialTheme.colorScheme.tertiary
                    ),
                ){
                    Text(genre2)
                }
                Button(
                    onClick = { buttonStates["genre3"]?.value = !(buttonStates["genre3"]?.value ?: false)
                        addToGenres(genre3,"1")},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (buttonStates["genre3"]?.value == true) Color.Gray else MaterialTheme.colorScheme.tertiary
                    ),
                ){
                    Text(genre3)
                }*/
                var isEnabled by remember { mutableStateOf(true) }
                FilterChip(
                    onClick = {
                            selected1 = !selected1
                            if (selected1) {
                                buttonStates["genre1"]?.value =
                                    !(buttonStates["genre1"]?.value ?: false)
                                addToGenres(genre1, "1")

                            } else {
                                buttonStates["genre1"]?.value =
                                    !(buttonStates["genre1"]?.value ?: true)
                                removeFromGenres(genre1,"1")
                            }
                              },
                    label = {
                        Text(genre1)
                    },

                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = if (buttonStates["genre1"]?.value == true) Color.Gray else MaterialTheme.colorScheme.tertiary
                    ),
                    selected = selected1,
                    leadingIcon = if (selected1) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    },
                )
                FilterChip(
                    onClick = { selected2 = !selected2
                        if (selected2) {
                            buttonStates["genre2"]?.value =
                                !(buttonStates["genre2"]?.value ?: false)
                            addToGenres(genre2, "1")
                        } else {
                            buttonStates["genre2"]?.value =
                                !(buttonStates["genre2"]?.value ?: true)
                            removeFromGenres(genre2,"1")

                        }
                    },
                    label = {
                        Text(genre2)
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = if (buttonStates["genre2"]?.value == true) Color.Gray else MaterialTheme.colorScheme.tertiary
                    ),
                    selected = selected2,
                    leadingIcon = if (selected2) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    },
                )
                FilterChip(
                    onClick = { selected3 = !selected3
                        if (selected3) {
                            buttonStates["genre3"]?.value =
                                !(buttonStates["genre3"]?.value ?: false)
                            addToGenres(genre3, "1")

                        } else {
                            buttonStates["genre3"]?.value =
                                !(buttonStates["genre3"]?.value ?: true)
                            removeFromGenres(genre3,"1")

                        }
                    },
                    label = {
                        Text(genre3)
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = if (buttonStates["genre3"]?.value == true) Color.Gray else MaterialTheme.colorScheme.tertiary
                    ),
                    selected = selected3,
                    leadingIcon = if (selected3) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    },
                )
            }
            Text(
                text = "Favourite authors: ",
                color = MaterialTheme.colorScheme.onPrimary,
                fontFamily = fontFamily
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { buttonStates["author1"]?.value = !(buttonStates["author1"]?.value ?: false)
                        addToAuthors(author1,"1") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (buttonStates["author1"]?.value == true) Color.Gray else MaterialTheme.colorScheme.tertiary
                    ),
                ){
                    Text(author1)
                }
                Button(
                    onClick = { buttonStates["author2"]?.value = !(buttonStates["author2"]?.value ?: false)
                        addToAuthors(author2,"1")},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (buttonStates["author2"]?.value == true) Color.Gray else MaterialTheme.colorScheme.tertiary
                    ),
                ) {
                    Text(author2)
                }
                Button(
                    onClick = { buttonStates["author3"]?.value = !(buttonStates["author3"]?.value ?: false)
                        addToAuthors(author3,"1")},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (buttonStates["author3"]?.value == true) Color.Gray else MaterialTheme.colorScheme.tertiary
                    ),
                )  {
                    Text(author3)
                }
            }
            Text(
                text = "Favourite editors: ",
                color = MaterialTheme.colorScheme.onPrimary,
                fontFamily = fontFamily
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { buttonStates["editor1"]?.value = !(buttonStates["editor1"]?.value ?: false)
                        addToEditors(editor1,"1")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (buttonStates["editor1"]?.value == true) Color.Gray else MaterialTheme.colorScheme.tertiary
                    ),
                ) {
                    Text(editor1)
                }
                Button(
                    onClick = { buttonStates["editor2"]?.value = !(buttonStates["editor2"]?.value ?: false)
                        addToEditors(editor2,"1")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (buttonStates["editor2"]?.value == true) Color.Gray else MaterialTheme.colorScheme.tertiary
                    ),
                ) {
                    Text(editor2)
                }
                Button(
                    onClick = { buttonStates["editor3"]?.value = !(buttonStates["editor3"]?.value ?: false)
                        addToEditors(editor3,"1")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (buttonStates["editor3"]?.value == true) Color.Gray else MaterialTheme.colorScheme.tertiary
                    ),
                )  {
                    Text(editor3)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 280.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val context= LocalContext.current
                Button(
                    onClick = { navController.navigate("profile_screen")
                    Toast.makeText(context, "choices saved successfully", Toast.LENGTH_SHORT).show()},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    ),
                ) {
                    Text("save")
                }
                Button(
                    onClick = { navController.navigate("profile_screen") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray
                    ),
                ) {
                    Text("cancel")
                }
            }
        }
    }
}

