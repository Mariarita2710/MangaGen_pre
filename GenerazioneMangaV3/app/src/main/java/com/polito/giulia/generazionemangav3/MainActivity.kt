package com.polito.giulia.generazionemangav3

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.polito.giulia.generazionemangav3.ui.theme.GenerazioneMangaV3Theme
import com.polito.giulia.generazionemangav3.ui.theme.MainScreen

val storage= Firebase.storage.reference
class MainActivity : AppCompatActivity() {
   private val viewModel by viewModels<AppViewModel>()

    private lateinit var database:DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {

        database = Firebase.database("https://generazionemangav3-default-rtdb.firebaseio.com/").reference

        super.onCreate(savedInstanceState)

        //PROVA PER CAPIRE SE SI SCRIVE SU DB:
       // database.child("manga").setValue("robe")

        setContent {

            /* var mangas= MutableLiveData<List<DataSnapshot>>(emptyList())

             println("ciao")

             println("manga:" + database.child("manga"))

             override fun onCancelled(databaseError: DatabaseError) {
                 // Gestisci gli errori qui
                 println("Errore nel leggere i dati dal database: ${databaseError.message}")
             }*/

            GenerazioneMangaV3Theme {
            MainScreen(viewModel)

           }
        }
    }
}
