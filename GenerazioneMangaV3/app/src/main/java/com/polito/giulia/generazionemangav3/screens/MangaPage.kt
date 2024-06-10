package com.polito.giulia.generazionemangav3.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.polito.giulia.generazionemangav3.AppViewModel
import com.polito.giulia.generazionemangav3.FindUrl
import com.polito.giulia.generazionemangav3.R
import com.polito.giulia.generazionemangav3.database
import com.polito.giulia.generazionemangav3.ui.theme.Blue40
import com.polito.giulia.generazionemangav3.ui.theme.Coral40
import com.polito.giulia.generazionemangav3.ui.theme.Green
import com.polito.giulia.generazionemangav3.ui.theme.Indigo40
import com.polito.giulia.generazionemangav3.ui.theme.Indigo90
import com.polito.giulia.generazionemangav3.ui.theme.Screen
import com.polito.giulia.generazionemangav3.ui.theme.Violet20
import com.polito.giulia.generazionemangav3.ui.theme.Violet40
import com.polito.giulia.generazionemangav3.ui.theme.fontFamily

@Composable
fun MangaPageScreen(viewModel: AppViewModel){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "manga") {
        composable("manga") {
            MangaDetail(viewModel = viewModel, navController = navController)
        }
        composable("volume_page_screen/{number}",arguments = listOf(navArgument("number"){
            type = NavType.IntType
        })) {
            val numb = it.arguments?.getInt("number") ?: ""
            VolumePageScreen(viewModel, numb as Int)
        }
        composable(route = Screen.Search.route) {
            SearchScreen(navController,viewModel)
        }
        composable(route = Screen.Review.route) {
            ReviewScreen(viewModel,navController)
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaDetail(viewModel: AppViewModel, navController: NavController) {
    var man = viewModel.selectedManga

    val sl= viewModel.mangas.observeAsState(emptyList()).value
        .filter { it.child("title").value.toString() == man }
    Log.d("Manga: ",sl.toString())

    var plot=""
    var author=""
    var genre=""
    var publisher=""
    var country=""
    var url = ""
    var numVol = 0L
    //var review = ""

    sl.forEach { s ->
        plot = s.child("plot").value.toString()
        author = s.child("author").value.toString()
        genre = s.child("genre").value.toString()
        country = s.child("country").value.toString()
        publisher = s.child("publisher").value.toString()
        numVol = s.child("numVol").value as Long
        url= FindUrl(fileName = man +" Banner.jpg")
        //review = s.child("review").value.toString()
    }

    Log.d("plot: ",plot)
    Log.d("country: ",country)
    Log.d("publisher: ",publisher)
    Log.d("genre: ",genre)
    Log.d("author: ",author)
    Log.d("number of volumes: ",numVol.toString())

    ConstraintLayout(
        constraintSet = ConstraintSet(
            content =
            """
                    {
                        bgCard: {
                          bottom: ['parent', 'bottom']
                        },
                        arrowBack: {
                          top: ['mangaTitle','top'],
                          bottom: ['bgCard', 'top'],
                          left: ['parent', 'top']
                        },
                        mangaTitle: {
                          top: ['bgCard', 'top'],
                          bottom: ['bgCard', 'top'],
                          right: ['parent', 'right']
                        },
                        banner: {
                          top: ['bgCard', 'top'],
                          bottom: ['bgCard', 'top'],
                          right: ['bgCard', 'right']
                        },
                        btnHeart: {
                          bottom: ['bgCard', 'top'],
                          right: ['parent', 'right'],
                        }
                    }
                """.trimIndent()
        ),
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Violet40, Violet20
                    )
                )
            )
            //.background(MaterialTheme.colorScheme.primary)
    ) {
        IconButton(
            modifier = Modifier
               .layoutId("arrowBack") ,
            onClick = {
                navController.navigate(Screen.Search.route)
                Log.d("vwghsfwghf", "gksjnf")
            },
            colors = IconButtonDefaults.iconButtonColors(contentColor = androidx.compose.material3.MaterialTheme.colorScheme.tertiary)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }
        AsyncImage(
            model=url,
            contentDescription = "Manga banner",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .layoutId("banner")
                .fillMaxSize()
                .padding(bottom = 200.dp),
            alpha = 0.5F
        )
/*
        Image(
            painter = painterResource(id = R.drawable.solo_leveling_banner),
            contentDescription = "Manga banner",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .layoutId("banner")
                .fillMaxSize()
                .padding(bottom = 200.dp),
            alpha = 0.5F
        )
        */

        Card(
            shape = RoundedCornerShape(
                topStart = 8.dp,
                topEnd = 8.dp
            ),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            modifier = Modifier
                .layoutId("bgCard")
                .height(530.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Violet40,Violet20
                        )
                    )
                )
            ) {
            ConstraintLayout(
                constraintSet = ConstraintSet(
                    content =
                    """
                    {
                       optionBar: {
                          top: ['parent', 'top'],
                          left: ['parent', 'left']
                        },
                        optionPage: {
                          top: ['optionBar', 'bottom'],
                          left: ['parent', 'left']
                        }
                    }
                """.trimIndent()
                ),
                modifier = Modifier
                    .padding(
                        start = 10.dp,
                        end = 10.dp
                    )
            ) {

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "info_screen") {
                    composable("info_screen") {
                        InfoPage(author, genre, country, publisher, plot, /*review*/ navController, man)
                    }
                    composable("volume_screen") {
                        VolumesPage(navController = navController, numVol)
                    }
                    composable("review_screen") {
                        ReviewScreen(viewModel, navController)
                    }
                    composable("volume_page_screen/{number}",arguments = listOf(navArgument("number"){
                        type = NavType.IntType
                    })) {
                        val numb = it.arguments?.getInt("number") ?: ""
                        VolumePageScreen(viewModel, numb as Int)
                    }
                    }


                val options = mutableListOf<String>("Info", "Volumes")
                var selectedIndex by remember {
                    mutableStateOf(0)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    //OptionBar()
                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .layoutId("optionBar")
                    ) {
                        options.forEachIndexed { index, option ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = options.size,
                                    baseShape = RoundedCornerShape(10.dp)
                                ),
                                colors = if (selectedIndex == index) {
                                    SegmentedButtonDefaults.colors(MaterialTheme.colorScheme.tertiary)
                                } else {
                                    SegmentedButtonDefaults.colors(MaterialTheme.colorScheme.background)
                                },
                                onClick = {
                                    selectedIndex = index

                                },
                                selected = selectedIndex == index
                            ) {
                                Text(
                                    text = option,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 20.sp,
                                    fontFamily = fontFamily,
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        if (selectedIndex == 0) {
                            navController.navigate("info_screen")
                        } else {
                            navController.navigate("volume_screen")
                        }
                    }
                }
            }
        }
        }
        // FAVOURITES
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

        //SHELF
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


        var filledHeart by remember { mutableStateOf(false) }
        var filledPlus by  remember { mutableStateOf(false) }

        //filledHeart = favouriteList.contains(man)
        //filledPlus = shelfList.contains(man)
        if(shelfList.contains(man)){
            filledPlus=true
        }else{
            filledPlus=false
        }
        if(favouriteList.contains(man)){
            filledHeart=true
        }else{
            filledHeart=false
        }

                Column(
                    //verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .layoutId("mangaTitle")
                        .padding(top = 0.dp, end = 10.dp, bottom = 230.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = man,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 50.sp,
                        fontFamily = fontFamily,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal,
                        style = TextStyle(shadow = Shadow(color = Color.Black, blurRadius = 5F))
                    )
                }
                Row(modifier = Modifier
                    .layoutId("btnHeart")
                    .padding(top = 32.dp, start = 320.dp, bottom = 8.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    val context= LocalContext.current
                    IconButton(
                        onClick = {
                            filledPlus = !filledPlus
                            if(filledPlus){
                            addToShelf(man, "1")
                                Toast.makeText(context,"Successfully added to shelf!",Toast.LENGTH_SHORT
                                ).show()
                        }
                        else {
                                removeFromShelf(man, "1")
                                Toast.makeText(context,"Successfully removed from shelf!",Toast.LENGTH_SHORT
                                ).show()
                            }
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
                            if(filledHeart){
                                addToFavourites(man, "1")
                                Toast.makeText(context,"Successfully added to favourites!",Toast.LENGTH_SHORT
                                ).show()
                            }
                            else
                                deleteFavourite(man, "1")
                            Toast.makeText(context,"Successfully removed from favourites!",Toast.LENGTH_SHORT
                            ).show()
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ) {
                        if (filledHeart == false) {
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = "Empty Heart",
                                modifier = Modifier.size(50.dp),
                                tint = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.Favorite,
                                contentDescription = "Filled Heart",
                                modifier = Modifier.size(50.dp),
                                tint = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        }
                    }
                }

        }
    }

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionBar() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "info_screen") {
        composable("info_screen") {
            InfoPage(navController = navController)
        }
        composable("volume_screen") {
            VolumesPage(navController = navController)
        }
    }
    val options = mutableListOf<String>("Info", "Volumes")
    var selectedIndex by remember {
        mutableStateOf(0)
    }

        SingleChoiceSegmentedButtonRow(modifier = Modifier
            .fillMaxWidth()
            .layoutId("optionBar")) {
            options.forEachIndexed { index, option ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = options.size,
                        baseShape = RoundedCornerShape(10.dp)
                    ),
                    colors = if (selectedIndex == index) {
                        SegmentedButtonDefaults.colors(MaterialTheme.colorScheme.tertiary)
                    } else {
                        SegmentedButtonDefaults.colors(MaterialTheme.colorScheme.background)
                    },
                    onClick = {
                        selectedIndex = index
                        if (option == "Info") {
                            navController.navigate("info_screen")
                        } else {
                            navController.navigate("volume_screen")
                        }
                    },
                    selected = selectedIndex == index
                ) {
                    Text(text = option)
                }
            }
        }
    }
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoPage(author: String, genre: String, country: String, publisher: String, plot: String, navController: NavController, manga: String) {
    var showMore by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .layoutId("optionPage")
        .padding(top = 50.dp)
        .verticalScroll(rememberScrollState())
    ){

        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)){
                Text(
                    text = "Author: " + author,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 18.sp,
                    fontFamily = fontFamily,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = "Genre: " + genre,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 18.sp,
                    fontFamily = fontFamily,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = "Country: " + country,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 18.sp,
                    fontFamily = fontFamily,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = "Publisher: " + publisher,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 18.sp,
                    fontFamily = fontFamily,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal
                )
            }
        }
        Text(
            text = "Plot",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 24.sp,
            fontFamily = fontFamily,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(top = 20.dp)
        )
        Card(colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),) {
            // if showMore is true, the Text will expand
            // Else Text will be restricted to 3 Lines of display
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .animateContentSize(animationSpec = tween(100))
                .clickable(
                    onClickLabel = "Read more",
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { showMore = !showMore }){
                if (showMore) {
                    Text(text = plot)
                } else {
                    Text(
                        text = plot,
                        maxLines = 5, overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        //Reviews
        var revList by remember { mutableStateOf<List<String>>(emptyList()) }
        var revs= database.child("manga").child(manga).child("reviews")
        revs.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) { //fa una foto al db in quel momento e la mette in dataSnapshot
                // Itera sui figli del nodo
                var list= mutableListOf<String>()

                for (childSnapshot in dataSnapshot.children) { //prende i figli di prodotti, quindi 0, 1...
                    // Aggiungi il prodotto alla lista
                    list.add(childSnapshot.value.toString())
                }
                revList=list
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Gestisci gli errori qui
                println("Errore nel leggere i dati dal database: ${databaseError.message}")
            }
        })

        Column {
            Text(
                text = "Reviews",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 24.sp,
                fontFamily = fontFamily,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 20.dp)
            )
            val reviewValue = remember { mutableStateOf(TextFieldValue()) }
            var openAlertDialog by remember { mutableStateOf(true) }
            var ctx = LocalContext.current

            /*if (openAlertDialog){
                Popup(
                    onDismissRequest = { openAlertDialog = false },
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xCC1D232C))
                            .padding(horizontal = 20.dp)
                    ){
                        //EFFETTIVO BOX DEL POPUP
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(15.dp))
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    RoundedCornerShape(15.dp)
                                )
                                .border(
                                    2.dp,
                                    MaterialTheme.colorScheme.tertiary,
                                    RoundedCornerShape(15.dp)
                                )
                        ){
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    //.fillMaxSize()
                                    .background(MaterialTheme.colorScheme.primary)
                                    .padding(16.dp)
                            ){
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = buildAnnotatedString {
                                        append("Leave a review about this manga")
                                    },
                                    fontSize = 20.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center,
                                    fontFamily = fontFamily,
                                )
                                Spacer(modifier = Modifier.height(22.dp))
                                Column(modifier = Modifier.padding(horizontal = 18.dp)) {
                                    OutlinedTextField(
                                        value = reviewValue.value,
                                        visualTransformation = PasswordVisualTransformation(),
                                        shape = MaterialTheme.shapes.large,
                                        placeholder =
                                        {
                                            Text(
                                                text = "Review",
                                                fontFamily = fontFamily,
                                                fontWeight = FontWeight.Light,
                                                fontSize = 18.sp,
                                                fontStyle = FontStyle.Italic,
                                                color = Color.White
                                            )
                                        },
                                        onValueChange = {
                                            reviewValue.value = it
                                        },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                        singleLine = true,
                                        colors = TextFieldDefaults.outlinedTextFieldColors(
                                            containerColor = MaterialTheme.colorScheme.onBackground,
                                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                                            focusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                                            focusedTextColor = Color.White,
                                            unfocusedTextColor = Color.White
                                        ),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }

                                Spacer(modifier = Modifier.height(22.dp))
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp)
                                ) {
                                    TextButton(
                                        modifier = Modifier.width(135.dp),
                                        shape = RoundedCornerShape(3.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.errorContainer,
                                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                                        ),
                                        onClick = {
                                            openAlertDialog = false
                                        }
                                    ) {
                                        Text(
                                            text = "Go back",
                                            fontFamily = fontFamily,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    TextButton(
                                        modifier = Modifier.width(135.dp),
                                        shape = RoundedCornerShape(3.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.tertiary,
                                            contentColor = MaterialTheme.colorScheme.onTertiary
                                        ),
                                        onClick = {
                                            Toast.makeText(ctx, "Review successfully posted!", Toast.LENGTH_SHORT).show()
                                            openAlertDialog = false
                                        }
                                    ) {
                                        Text(
                                            text = "Post",
                                            fontFamily = fontFamily,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }*/

            /*Row(horizontalArrangement =Arrangement.Center){
                Button(onClick = {
                                 //openAlertDialog=true
                                 navController.navigate("review_screen")
                },
                    modifier = Modifier.padding(10.dp),
                    colors = ButtonDefaults.buttonColors((MaterialTheme.colorScheme.tertiary))) {
                    Text(
                        text = "Write a review",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 18.sp,
                        fontFamily = fontFamily,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal
                    )
                }
                Button(onClick = { *//*TODO*//* },
                    modifier = Modifier.padding(10.dp),
                    colors = ButtonDefaults.buttonColors((MaterialTheme.colorScheme.tertiary))) {
                    Text(
                        text = "See all",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 18.sp,
                        fontFamily = fontFamily,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
*/
            var mostraReview by remember { mutableStateOf(false) }

            Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .padding(bottom = 80.dp)) {

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    //.fillMaxSize()
                                    .padding(3.dp)
                            ) {
                                if (openAlertDialog) {

                                    Column(modifier = Modifier.padding(2.dp)) {
                                        OutlinedTextField(
                                            value = reviewValue.value,
                                            shape = MaterialTheme.shapes.large,
                                            placeholder =
                                            {
                                                Text(
                                                    text = "About this manga...",
                                                    fontFamily = fontFamily,
                                                    fontWeight = FontWeight.Light,
                                                    fontSize = 18.sp,
                                                    fontStyle = FontStyle.Italic,
                                                    color = Color.White
                                                )
                                            },
                                            onValueChange = {
                                                reviewValue.value = it
                                            },
                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                            singleLine = false,
                                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                                containerColor = MaterialTheme.colorScheme.onBackground,
                                                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                                                focusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                                                focusedTextColor = Color.White,
                                                unfocusedTextColor = Color.White
                                            ),
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(22.dp))
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(2.dp)
                                    ) {
                                        /*TextButton(
                                        modifier = Modifier.width(135.dp),
                                        shape = RoundedCornerShape(14.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Indigo90,
                                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                                        ),
                                        onClick = {
                                            openAlertDialog = false
                                        }
                                    ) {
                                        Text(
                                            text = "Go back",
                                            fontFamily = fontFamily,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }*/
                                        TextButton(
                                            modifier = Modifier.width(135.dp),
                                            shape = RoundedCornerShape(14.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = MaterialTheme.colorScheme.tertiary,
                                                contentColor = MaterialTheme.colorScheme.onTertiary
                                            ),
                                            onClick = {
                                                Toast.makeText(
                                                    ctx,
                                                    "Review successfully posted!",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                mostraReview = true
                                                addToReview(manga, reviewValue.value.text)
                                                Log.d("manga: ", manga)
                                                Log.d("rece: ", reviewValue.value.text)
                                                //openAlertDialog = false
                                            }
                                        ) {
                                            Text(
                                                text = "Post",
                                                fontFamily = fontFamily,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(22.dp))
                                    Log.d("revs:", revList.toString())
                                    Column(Modifier.padding(bottom = 30.dp)) {
                                        revList.forEach() {
                                                Text(
                                                    text = "redSarah15",
                                                    color = MaterialTheme.colorScheme.onPrimary,
                                                    fontSize = 14.sp,
                                                    fontFamily = fontFamily,
                                                    textAlign = TextAlign.Start,
                                                    fontWeight = FontWeight.SemiBold,
                                                    modifier = Modifier.padding(start = 2.dp)
                                                )
                                                Card(
                                                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
                                                    modifier = Modifier.padding(top = 8.dp)
                                                ) {
                                                    // if showMore is true, the Text will expand
                                                    // Else Text will be restricted to 3 Lines of display
                                                    Box(modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(8.dp)
                                                        .animateContentSize(
                                                            animationSpec = tween(
                                                                100
                                                            )
                                                        )
                                                        .clickable(
                                                            onClickLabel = "Read more",
                                                            interactionSource = remember { MutableInteractionSource() },
                                                            indication = null
                                                        ) { showMore = !showMore }) {
                                                        if (showMore) {
                                                            Text(text = it)
                                                        } else {
                                                            Log.d("revs:", it)
                                                            Text(
                                                                text = it,
                                                                maxLines = 5,
                                                                overflow = TextOverflow.Ellipsis
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }




            /*Text(
                text = "Mary ★★★★☆",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 14.sp,
                fontFamily = fontFamily,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Normal
            )

            Text(
                text = "I really enjoyed reading this manhwa. Very nice story and well developed characters!",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 14.sp,
                fontFamily = fontFamily,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Normal
            )*/
                }
            }
        }

    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VolumesPage(navController: NavController, numVol: Long) {
    Box(
        modifier = Modifier
            .layoutId("optionPage")
            .padding(top = 50.dp, bottom = 80.dp)
    ) {
        Text(
            text = "Volumes",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 24.sp,
            fontFamily = fontFamily,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Normal
        )

        LazyColumn(
            modifier = Modifier
                .padding(top = 30.dp)
        ) {
            items(numVol.toInt() + 1) {
                if (it > 0) {
                    Card(
                        modifier = Modifier
                            .width(200.dp)
                            .height(70.dp)
                            .padding(vertical = 10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        ),
                        onClick = {
                            navController.navigate("volume_page_screen/$it")
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                text = "Volume $it",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 20.sp,
                                fontFamily = fontFamily,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier
                                    .padding(10.dp)
                            )
                            var isChecked = remember { mutableStateOf(false) }
                            IconButton(
                                onClick = {
                                    isChecked.value = !isChecked.value
                                    /*TODO*/
                                },
                                colors = if (isChecked.value) {
                                    IconButtonDefaults.iconButtonColors(contentColor = Green, containerColor = Color.White)
                                } else {
                                    IconButtonDefaults.iconButtonColors(contentColor = Color.White)
                                },
                                modifier = Modifier.size(30.dp)
                            ) {
                                if (isChecked.value) {
                                    Icon(
                                        imageVector = Icons.Rounded.CheckCircle,
                                        contentDescription = "checked",
                                        modifier = Modifier.size(40.dp)
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Outlined.CheckCircle,
                                        contentDescription = "unchecked",
                                        modifier = Modifier.size(50.dp)
                                    )
                                }

                            }

                        }
                    }
                }

            }
        }
    }
}

fun deleteFavourite(manga: String, id: String){
    var favourites= database.child("users").child(id).child("favourites")
    favourites.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            for (childSnapshot in dataSnapshot.children) {
                if(childSnapshot.value.toString()==manga){
                    childSnapshot.ref.removeValue()
                }
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            println("Cannot read data from database: ${databaseError.message}")
        }
    })
}

fun addToFavourites(manga: String, id: String){

    var favourites= database.child("users").child(id).child("favourites")
    favourites.orderByKey().limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            var lastKey = 0
            if(dataSnapshot.exists()){

                for (childSnapshot in dataSnapshot.children) {
                    val i = childSnapshot.key?.toInt() ?: 0
                    lastKey= i+1
                    println("LastKey: "+lastKey)
                }
                database.child("users").child(id).child("favourites").child(lastKey.toString()).setValue(manga)
            }
            else{
                val favouriteMap = mapOf(lastKey.toString() to manga)
                database.child("users").child(id).child("favourites").setValue(favouriteMap)
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            println("Cannot read data from database: ${databaseError.message}")
        }
    })
}

fun addToShelf(manga: String, id: String) {
    var shelf= database.child("users").child(id).child("shelf")
    shelf.orderByKey().limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            var lastKey = 0
            if(dataSnapshot.exists()){

                for (childSnapshot in dataSnapshot.children) {
                    val i = childSnapshot.key?.toInt() ?: 0
                    lastKey= i+1
                    println("LastKey: "+lastKey)
                }
                database.child("users").child(id).child("shelf").child(lastKey.toString()).setValue(manga)
            }
            else{
                val favouriteMap = mapOf(lastKey.toString() to manga)
                database.child("users").child(id).child("shelf").setValue(favouriteMap)
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            println("Cannot read data from database: ${databaseError.message}")
        }
    })
}
fun removeFromShelf(manga: String, id: String) {
    var shelf= database.child("users").child(id).child("shelf")
    shelf.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            for (childSnapshot in dataSnapshot.children) {
                if(childSnapshot.value.toString()==manga){
                    childSnapshot.ref.removeValue()
                }
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            println("Cannot read data from database: ${databaseError.message}")
        }
    })
}

fun addToGenres(genre: String, id: String){

    var favourites= database.child("users").child(id).child("genres")
    favourites.orderByKey().limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            var lastKey = 0
            if(dataSnapshot.exists()){

                for (childSnapshot in dataSnapshot.children) {
                    val i = childSnapshot.key?.toInt() ?: 0
                    lastKey= i+1
                    println("LastKey: "+lastKey)
                }
                database.child("users").child(id).child("genres").child(lastKey.toString()).setValue(genre)
            }
            else{
                val favouriteMap = mapOf(lastKey.toString() to genre)
                database.child("users").child(id).child("genres").setValue(favouriteMap)
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            println("Cannot read data from database: ${databaseError.message}")
        }
    })
}

fun removeFromGenres(genre: String, id: String){

    var favourites= database.child("users").child(id).child("genres")
    favourites.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            for (childSnapshot in dataSnapshot.children) {
                if(childSnapshot.value.toString()==genre){
                    childSnapshot.ref.removeValue()
                }
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            println("Cannot read data from database: ${databaseError.message}")
        }
    })
}


fun addToAuthors(authors: String, id: String){

    var favourites= database.child("users").child(id).child("authors")
    favourites.orderByKey().limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            var lastKey = 0
            if(dataSnapshot.exists()){

                for (childSnapshot in dataSnapshot.children) {
                    val i = childSnapshot.key?.toInt() ?: 0
                    lastKey= i+1
                    println("LastKey: "+lastKey)
                }
                database.child("users").child(id).child("authors").child(lastKey.toString()).setValue(authors)
            }
            else{
                val favouriteMap = mapOf(lastKey.toString() to authors)
                database.child("users").child(id).child("authors").setValue(favouriteMap)
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            println("Cannot read data from database: ${databaseError.message}")
        }
    })
}

fun addToEditors(editors: String, id: String){

    var favourites= database.child("users").child(id).child("publishers")
    favourites.orderByKey().limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            var lastKey = 0
            if(dataSnapshot.exists()){

                for (childSnapshot in dataSnapshot.children) {
                    val i = childSnapshot.key?.toInt() ?: 0
                    lastKey= i+1
                    println("LastKey: "+lastKey)
                }
                database.child("users").child(id).child("publishers").child(lastKey.toString()).setValue(editors)
            }
            else{
                val favouriteMap = mapOf(lastKey.toString() to editors)
                database.child("users").child(id).child("publishers").setValue(favouriteMap)
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            println("Cannot read data from database: ${databaseError.message}")
        }
    })
}

fun addToReview(manga: String, review: String){

    var revs= database.child("manga").child(manga).child("reviews")
    revs.orderByKey().limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            var lastKey = 0
            if(dataSnapshot.exists()){

                for (childSnapshot in dataSnapshot.children) {
                    val i = childSnapshot.key?.toInt() ?: 0
                    lastKey= i+1
                    println("LastKey: "+lastKey)
                }
                database.child("manga").child(manga).child("reviews").child(lastKey.toString()).setValue(review)
            }
            else{
                val revMap = mapOf(lastKey.toString() to review)
                database.child("manga").child(manga).child("reviews").setValue(revMap)
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            println("Cannot read data from database: ${databaseError.message}")
        }
    })
}
