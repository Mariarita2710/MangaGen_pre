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
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
    }
}

@Composable
fun Profile(navController: NavController,viewModel: AppViewModel,modifier: Modifier = Modifier
    .padding(top = 74.dp)
    .padding(bottom = 74.dp)){
    val scrollState = rememberScrollState()

    var favouriteGenresList by remember { mutableStateOf<List<String>>(emptyList()) }
    var favouritesGenres = database.child("users").child("1").child("favourites")
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
    var favouritesAuthors= database.child("users").child("1").child("favourites")
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
    var favouritesEditors= database.child("users").child("1").child("favourites")
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
        .padding(top = 70.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProfileHeader(navController)
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
            ) {

                    Text(
                        text = " Favourite genres: " + favouritesGenres,//dopo il più in teoria ci andrebbe tipo una funzione
                        //che fa cambiare il testo a seconda delle cose che vengono scelte quando si fa edit prof
                        //quindi da aggiungere in post
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = fontFamily
                    )
                    Text(
                        text = " Favourite authors: " + " isayama",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = fontFamily
                    )
                    Text(
                        text = " Favourite editors: " + " jpop",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = fontFamily
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)

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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
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
            }
        }
    }
}

@Composable
fun ProfileHeader(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(imageVector = Icons.Default.AccountCircle,
            contentDescription ="profile pic",
            modifier = Modifier.size(70.dp))
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
        Button(
            onClick = {navController.navigate("edit_screen") },
            modifier = Modifier.align(Alignment.CenterVertically),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            ),
        ) {
            Text("edit profile")
        }
    }
}

@Composable
fun EditProfile(viewModel: AppViewModel, navController: NavController) {
    var selectedLanguage = remember { mutableStateOf<Lang?>(null) }
    var genre1="drama"
    var genre2="shonen"
    var genre3="action"


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
                Button(
                    onClick = { addToGenres(genre1,"1") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    ),
                ) {
                    Text(genre1)
                }
                Button(
                    onClick = {addToGenres(genre2,"1" )},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    ),
                ) {
                    Text(genre2)
                }
                Button(
                    onClick = {addToGenres(genre3,"1") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    ),
                ) {
                    Text(genre3)
                }
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
                    onClick = {/*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    ),
                ) {
                    Text("Isayama")
                }
                Button(
                    onClick = {/*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    ),
                ) {
                    Text("One")
                }
                Button(
                    onClick = {/*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    ),
                ) {
                    Text("Urasawa")
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
                    onClick = {/*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    ),
                ) {
                    Text("jpop")
                }
                Button(
                    onClick = {/*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    ),
                ) {
                    Text("panini")
                }
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    ),
                ) {
                    Text(text="dynit")
                }
            }
            Text(
                text = "App languages: ",
                color = MaterialTheme.colorScheme.onPrimary,
                fontFamily = fontFamily
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {/*
                Icon(painter = painterResource(R.drawable.flag_it),
                    contentDescription ="flag ita",
                    modifier = Modifier.size(30.dp))
                Icon(painter = painterResource(R.drawable.flag_jp),
                    contentDescription ="flag jp",
                    modifier = Modifier.size(30.dp))
                Icon(painter = painterResource(R.drawable.flag_kr),
                    contentDescription ="flag kr",
                    modifier = Modifier.size(30.dp))
                Icon(painter = painterResource(R.drawable.flag_uk),
                    contentDescription ="flag uk",
                    modifier = Modifier.size(30.dp))*/
                LanguageSelection(selectedLanguage = selectedLanguage.value) { lang ->
                    selectedLanguage.value = lang
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                        containerColor = MaterialTheme.colorScheme.tertiary
                    ),
                ) {
                    Text("cancel")
                }
            }
        }
    }
}
