package com.polito.giulia.generazionemangav3

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.values
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

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.primary)
    )
    {
        Spacer(modifier = Modifier.height(5.dp))
        ScrollableColumn(viewModel)
        Spacer(modifier = Modifier.height(5.dp))
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

    Column(
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .padding(10.dp, 74.dp, 10.dp, 10.dp)
            .verticalScroll(rememberScrollState())
        ,
    ) {
        Spacer(modifier=Modifier.weight(0.25f))
        Column(modifier = Modifier){

            Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "For you", color = MaterialTheme.colorScheme.onPrimary,
                    fontFamily = fontFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold)
            }
            Spacer(modifier=Modifier.weight(0.25f))

            //Contiene i bottoni con i manga
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {

            }

        }
        Spacer(modifier = Modifier.border(2.dp, MaterialTheme.colorScheme.onPrimary))
    }

    //Seconda riga Consigliati
    Column(modifier = Modifier){

        Row(modifier=Modifier.fillMaxWidth()){
            Text(text = "Recommendations", color = MaterialTheme.colorScheme.onPrimary,
                fontFamily = fontFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold)
        }
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())){
        }
    }
    Spacer(modifier = Modifier.border(2.dp, MaterialTheme.colorScheme.onPrimary))
    //Terza riga
    Column(modifier = Modifier){

        Row(modifier=Modifier.fillMaxWidth()){
            Text(text = "Read again", color = MaterialTheme.colorScheme.onPrimary,
                fontFamily = fontFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold)
        }
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())){

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