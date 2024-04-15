package com.polito.giulia.generazionemangav3

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.values
import com.polito.giulia.generazionemangav3.screens.filteredList
import com.polito.giulia.generazionemangav3.ui.theme.Screen
import com.polito.giulia.generazionemangav3.ui.theme.Violet20
import com.polito.giulia.generazionemangav3.ui.theme.Violet40
import com.polito.giulia.generazionemangav3.ui.theme.fontFamily

//private lateinit var database: DatabaseReference
var database = Firebase.database("https://generazionemangav3-default-rtdb.firebaseio.com/").reference




class AppViewModel: ViewModel() {
    var selectedManga = "Death Note"
    var mangas= MutableLiveData<List<DataSnapshot>>(emptyList())
    fun addManga(man: DataSnapshot){
        val mangaName = man.value.toString()

        // Verifica se esiste già un manga con lo stesso nome
        val mangaExists = mangas.value?.any { it.value.toString() == mangaName } ?: false

        if (!mangaExists) {
            // Aggiungi il manga solo se non esiste già
            val currentList = mangas.value.orEmpty()
            val updatedList = currentList + man
            mangas.value = updatedList
        }
    }
}

@Composable
fun HomePage(viewModel: AppViewModel, navController: NavController){
    val man by viewModel.mangas.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Violet40, Violet20
                    )
                )
            )
            .padding(top = 100.dp)
    )
    {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
/*
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.primary)
    )
    {
        Spacer(modifier = Modifier.height(5.dp))
        ScrollableColumn(viewModel)
        Spacer(modifier = Modifier.height(5.dp))
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
                    val fileName = p.child("title").value.toString() + " Cover.jpg"
                    val url = FindUrl(fileName = fileName)
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Card(
                            onClick = {
                                viewModel.selectedManga = p.child("title").value.toString();
                                navController.navigate(Screen.MangaPage.route)
                            },
                            modifier = Modifier.size(100.dp, 140.dp)
                        ) {
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
                    val fileName = p.child("title").value.toString() + " Cover.jpg"
                    val url = FindUrl(fileName = fileName)
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            onClick = {
                                viewModel.selectedManga = p.child("title").value.toString();
                                navController.navigate(Screen.MangaPage.route)
                            },
                            modifier = Modifier.size(100.dp, 140.dp)
                        ) {
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

        }
    }

}

@Composable
fun ScrollableColumn(viewModel: AppViewModel) {

    val children= database.child("manga") //prende dal db il nodo manga e aggiunge un listener
    children.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) { //fa una foto al db in quel momento e la mette in dataSnapshot
            // Itera sui figli del nodo

            for (childSnapshot in dataSnapshot.children) { //prende i figli di manga
                // Aggiungi il prodotto alla lista
                viewModel.addManga(childSnapshot)
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Gestisci gli errori qui
            println("Errore nel leggere i dati dal database: ${databaseError.message}")
        }
    })

    val man by viewModel.mangas.observeAsState()
    println(man)

    val data =
        listOf<String>(
            "Attack on Titan",
            "One punch man",
            "JoJo",
            "Attack on Titan",
            "One punch man",
            "JoJo","Attack on Titan",
            "One punch man",
            "JoJo","Attack on Titan",
            "One punch man",
            "JoJo","Attack on Titan",
            "One punch man",
            "JoJo",
        )


    LazyColumn(
        modifier = Modifier
            .padding(top = 80.dp)
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        items(count = data.count()) { countValue ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .clickable {  },
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
            ) {
                Text(
                    text = "${data[countValue]}  vol  $countValue",
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp , top = 10.dp , end = 10.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .width(120.dp)
                            .height(150.dp)
                            .padding(start = 10.dp , top = 5.dp , bottom = 10.dp),
                        contentScale = ContentScale.Crop,
                        painter = painterResource(id = R.drawable.newmanga),
                        contentDescription = ""
                    )

                    Text(modifier = Modifier.padding(15.dp),text = "The new ${data[countValue]} volume is out, read it as soon as possible or get spoiled by your friends!" ,color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 18.sp,
                        fontFamily = fontFamily,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal )
                }
            }
        }
    }
}
@Composable
fun FindUrl(fileName: String): String{
    var url by remember { mutableStateOf<String>("") }
    storage.child(fileName).downloadUrl.addOnSuccessListener {
        // Got the download URL for 'users/me/profile.png'
        url=it.toString()
    }.addOnFailureListener {
        // Handle any errors
        Log.e("Foto Error", "Errore nel listener "+fileName)
    }

    return url
}